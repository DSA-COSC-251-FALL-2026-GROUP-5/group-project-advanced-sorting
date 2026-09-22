import java.util.Random;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

class CollectData {
  public static SortMetrics<Integer> sortMetrics = new SortMetrics<Integer>(Integer::compare);
  public static final double DUPLICATION_RATIO[] = { 0.1, 10 };
  public static final int DATASET_SIZES[] = {
      5,
      1_000,
      1_000_000
  };

  // 100 runs is probably enough to get a decent estimate of the standard
  // deviation
  public static final int NUMBER_OF_RUNS = 100;
  public static final int TIM_SORT_MIN_RUNS = 32;
  // NOTE: we will initialize an array of the maximum size of the dataset for
  // convenience, but this can clearly be dynamically changed to the size of the
  // array so that you don't waste space
  public static Integer tmpArr[] = new Integer[Arrays.stream(DATASET_SIZES).max().getAsInt()];

  public static void runSortAndSaveToFile(String sortTypeString, HashMap<String, Sort<Integer>> sortTypeMap) {
    // for now we'll hardcode the type to be Integer based for testing purposes and
    // for simplicity
    try {
      for (int i = 0; i < DUPLICATION_RATIO.length; i++) {
        for (int j = 0; j < DATASET_SIZES.length; j++) {
          for (int k = 0; k < NUMBER_OF_RUNS; k++) {
            // create an array of integers
            //
            // we define the duplicity ratio to be r = n/k where n is the size of the array
            // and k is the range from 1 to k.
            //
            // This means that to get k, it's k = n/r. We'll just ceil it, just in case we
            // get a range that's less than 1, so that we just get an array of 1

            Integer arr[] = new Random()
                .ints(DATASET_SIZES[j], 1, (int) Math.ceil(DATASET_SIZES[j] / DUPLICATION_RATIO[i]) + 1)
                .boxed()
                .toArray(Integer[]::new);
            // System.out.println(Arrays.toString(arr));
            sortMetrics.runSort(
                arr,
                sortTypeMap.get(sortTypeString));

            Path path = Path.of("results",
                sortTypeString,
                "%f".formatted(DUPLICATION_RATIO[i]),
                "%d".formatted(DATASET_SIZES[j]),
                "%04d.json".formatted(k));

            Files.createDirectories(path.getParent());
            System.out.printf("Saved in %s\n".formatted(path.toString()));
            Files.writeString(
                path,
                sortMetrics.getMetadataAsJson(),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
            sortMetrics.resetMetrics();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  };

  public static void main(String[] args) {

    JavaSort<Integer> javaSort = new JavaSort<Integer>(sortMetrics);
    BubbleSort<Integer> bubbleSort = new BubbleSort<Integer>(sortMetrics);
    SelectionSort<Integer> selectionSort = new SelectionSort<Integer>(sortMetrics);
    InsertionSort<Integer> insertionSort = new InsertionSort<Integer>(sortMetrics);
    MergeSort<Integer> mergeSort = new MergeSort<Integer>(sortMetrics, tmpArr);
    TimSort<Integer> timSort = new TimSort<Integer>(sortMetrics, TIM_SORT_MIN_RUNS, tmpArr);

    HashMap<String, Sort<Integer>> sortTypeMap = new HashMap<String, Sort<Integer>>();
    sortTypeMap.put("bubbleSort", bubbleSort);
    sortTypeMap.put("selectionSort", selectionSort);
    sortTypeMap.put("insertionSort", insertionSort);
    sortTypeMap.put("mergeSort", mergeSort);
    sortTypeMap.put("timSort", timSort);
    sortTypeMap.put("javaSort", javaSort);

    String fastSorts[] = {
        "mergeSort",
        "timSort",
        "javaSort"
    };
    for (String fastSort : fastSorts) {
      runSortAndSaveToFile(
          fastSort,
          sortTypeMap);
    }
  }
}

import java.util.Comparator;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

/*
- [] dataset size
- [] runtime
- [] number of comparisons
- [] number of swaps
- [] any other meaningful metrics
 */

public class SortMetrics<T> {
  private long swaps = 0;
  private long comparisions = 0;
  private long startTime = 0;
  private long endTime = 0;
  private long runTime = 0;
  private long datasetSize = 0;
  T arr[];
  Comparator<T> comparator;

  SortMetrics(Comparator<T> comparator) {
    this.comparator = new Comparator<T>() {
      @Override
      public int compare(T a, T b) {
        // compare is 1 when a > b and -1 when a < b. 0 is probably when a == b
        comparisions++;
        // to prevent null
        return Comparator.nullsLast(comparator).compare(a, b);

      }
    };
  }

  void swap(T[] arr, int i, int j) {
    // internally increment swaps
    swaps++;
    T tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  void resetMetrics() {
    swaps = 0;
    comparisions = 0;
    startTime = 0;
    endTime = 0;
    runTime = 0;
    datasetSize = 0;
  }

  void runSort(T arr[], Sort<T> sortType) {
    startTime = System.nanoTime();
    sortType.sort(arr, this);
    endTime = System.nanoTime();
    runTime = endTime - startTime;
    this.arr = arr;
    this.datasetSize = arr.length;
  }

  String getJsonContent() {
    String arrAsString = arr == null ? "[]" : Arrays.toString(arr);
    return """
        {
          {
          "swaps" : %d,
          "comparisons" : %d,
          "runTime" : %d,
          "datasetSize" : %d
          },
        %s
        }
        """.formatted(
        swaps,
        comparisions,
        runTime,
        datasetSize,
        arrAsString);
  }

  void writeToFile(String path) {
    try {
      // Overwrites the file if it exists, or creates a new one
      Files.writeString(Path.of(path), getJsonContent());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

import java.util.Comparator;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

/*
- [x] dataset size
- [x] runtime
- [x] number of comparisons
- [x] number of swaps
- [x] any other meaningful metrics
 */

public class SortMetrics<T> {
  private long swaps = 0;
  private long comparisions = 0;
  private long copies = 0; // NOTE: this is for insertion
  private long startTime = 0;
  private long endTime = 0;
  private long runTime = 0;
  private long datasetSize = 0;
  T unsortedArr[];
  T sortedArr[];
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

  void rightShift(T arr[], int indexStart, int indexEnd) {
    // a utility function that imagines that we need a hole at index i, and shifts
    // everything from index i to the right

    for (int i = indexEnd; i > indexStart; i--) {
      copies++;
      arr[i] = arr[i - 1];
    }
  }

  int binarySearch(T arr[], T x, int leftPtr, int rightPtr) {
    int midPtr;
    while (leftPtr < rightPtr) {
      // midPtr is the ceil((leftPtr + rightPtr )/ 2)
      midPtr = ((leftPtr + rightPtr) % 2 == 0) ? (leftPtr + rightPtr) / 2 : ((leftPtr + rightPtr) / 2) + 1;
      comparisions++;
      if (this.comparator.compare(arr[midPtr], x) > 0) {
        rightPtr = midPtr - 1;
      } else {
        leftPtr = midPtr;
      }
    }
    return rightPtr;
  }

  void swap(T[] arr, int i, int j) {
    // internally increment swaps
    if (i == j) {
      // no need to waste compute if they're the same
      return;
    }
    swaps++;
    T tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  void resetMetrics() {
    swaps = 0;
    comparisions = 0;
    copies = 0;
    startTime = 0;
    endTime = 0;
    runTime = 0;
    datasetSize = 0;
  }

  void runSort(T arr[], Sort<T> sortType) {
    this.unsortedArr = arr.clone();
    startTime = System.nanoTime();
    sortType.sort(arr, this);
    endTime = System.nanoTime();
    runTime = endTime - startTime;
    this.sortedArr = arr.clone();
    this.datasetSize = arr.length;
  }

  String getJsonContent() {
    String unsortedArrAsString = unsortedArr == null ? "[]" : Arrays.toString(unsortedArr);
    String sortedArrAsString = sortedArr == null ? "[]" : Arrays.toString(sortedArr);
    return """
        {
          "metadata" : {
            "swaps" : %d,
            "comparisons" : %d,
            "copies" : %d,
            "runTime" : %d,
            "datasetSize" : %d
          },
          "unsortedArr" : %s,
          "sortedArr" : %s,
        }
            """.formatted(
        swaps,
        comparisions,
        copies,
        runTime,
        datasetSize,
        unsortedArrAsString,
        sortedArrAsString);
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

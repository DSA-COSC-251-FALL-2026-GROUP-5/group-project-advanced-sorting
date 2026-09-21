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
  private long copies = 0;
  private long startTime = 0;
  private long endTime = 0;
  private long runTime = 0;
  private long datasetSize = 0;
  T unsortedArr[];
  T sortedArr[];
  Comparator<T> comparator;
  int sign = 1;

  SortMetrics(Comparator<T> comparator) {
    this.comparator = new Comparator<T>() {
      @Override
      public int compare(T a, T b) {
        // compare is 1 when a > b and -1 when a < b. 0 is probably when a == b
        comparisions++;
        // to prevent null
        return sign * Comparator.nullsLast(comparator).compare(a, b);
      }
    };
  }

  void toggleSign() {
    // this is used to sort things in ascending or descending order
    sign = -sign;
  }

  void setSign(int sign) {
    if (sign != -1 || sign != 1) {
      throw new IllegalArgumentException("sign must be either 1 for ascending order, or -1 for descending order");
    }
    this.sign = sign;
  }

  void incrementCopies() {
    // increment the copies variable
    copies++;
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

  void merge(T arr[], T tmpArr[], int leftPtr1, int rightPtr1, int leftPtr2, int rightPtr2) {
    // This function will treat leftPtr1 to rightPtr1 as one array that's sorted,
    // and leftPtr2 to rightPtr2 as another subarray that's sorted, and it will
    // merge.
    //
    // We need that leftPtr1 <= rightPtr1 < leftPtr2 <= rightPtr2
    //
    // Moreover, we need that all of the variables are in range of the array
    //
    // We also assume that the subarray 1 and subarray 2 is neighboring subarrays,
    // meaning that they can be merged to
    //
    // I will also assume that the temporary array has length, exactly equals to
    // I won't enforce those conditions as exceptions to try to simplify things
    //
    // NOTE: the reason why we're passing in a tmpArr is because:
    // - it's actually kinda hard to initialize an array of a generic type
    // - i think it's going to save us some time because memory allocation and
    // deallocationn takes some time due to syscalls or something

    for (int i = leftPtr1; i <= rightPtr2; i++) {
      tmpArr[i] = arr[i];
      copies++;
    }

    int ptr1 = leftPtr1;
    int ptr2 = leftPtr2;

    // mainPtr will be the pointer where we write onto in the main array. It
    // actually can be derived from ptr1 and ptr2 because, it's something like (ptr1
    // - leftPtr1) + (ptr2 - leftPtr2), but we'll explicitly store it

    int mainPtr = leftPtr1;
    // ptr1 and ptr2 will keep track of where we're currently at on the merging
    // stage

    while ((ptr1 <= rightPtr1) && (ptr2 <= rightPtr2)) {
      if (comparator.compare(tmpArr[ptr1], tmpArr[ptr2]) <= 0) {
        // meaning if tmpArr[ptr1] <= tmpArr[ptr2] (i think this will maintain
        // stability)
        arr[mainPtr] = tmpArr[ptr1];
        ptr1++;
      } else {
        arr[mainPtr] = tmpArr[ptr2];
        ptr2++;
      }
      copies++;
      mainPtr++;
    }

    // do take the tail and place it in the main array

    for (int i = ptr1; i <= rightPtr1; i++) {
      arr[mainPtr] = tmpArr[ptr1];
      ptr1++;
      mainPtr++;
      copies++;
    }

    for (int i = ptr2; i <= rightPtr2; i++) {
      arr[mainPtr] = tmpArr[ptr2];
      ptr2++;
      mainPtr++;
      copies++;
    }
  }

  void reverse(T arr[], int leftPtr, int rightPtr) {
    // reverse a subarray by swapping
    while (leftPtr < rightPtr) {
      swap(arr, leftPtr, rightPtr);
      leftPtr++;
      rightPtr--;
    }
  }

  void runSort(T arr[], Sort<T> sortType) {
    this.unsortedArr = arr.clone();
    startTime = System.nanoTime();
    sortType.sort(arr);
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

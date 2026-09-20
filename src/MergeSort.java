import java.util.Comparator;

public class MergeSort<T> extends Sort<T> {
  T[] tmpArr;

  MergeSort(SortMetrics<T> sortMetrics, T tmpArr[]) {
    // NOTE: before we merge sort, we must create a temporary array of the specified
    // type and initialize it for the instance at the start because Java can't
    // trivally do something like `new T[10]` (so it diverges from the typical
    // constructor template for the Sort class)
    super(sortMetrics);
    this.tmpArr = tmpArr;
  }

  void mergeSort(T[] arr, T[] tmpArr, int leftPtr, int rightPtr) {
    if ((rightPtr + 1) - leftPtr <= 1) {
      // if sub array is of size 0 or 1, it's trivially sorted
      return;
    }

    // also, at this stage, the array is of length 2 or greater, so, midPtr <
    // rightPtr always from here otherwise, we split it into two
    int midPtr = (leftPtr + rightPtr) / 2;
    // midPtr is now the floor of (leftPtr + rightPtr)/2

    // so we first mergeSort the left half
    mergeSort(arr, tmpArr, leftPtr, midPtr);

    // then we mergeSort the right half
    mergeSort(arr, tmpArr, midPtr + 1, rightPtr);

    // then we merge them
    sortMetrics.merge(arr, tmpArr, leftPtr, midPtr, midPtr + 1, rightPtr);
  }

  @Override
  void sort(T[] arr) {
    // this simply initializes the correct parameter for the mergeSort function
    // which is recursive
    mergeSort(arr, tmpArr, 0, arr.length - 1);
  }
}

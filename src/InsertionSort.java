public class InsertionSort<T> extends Sort<T> {

  InsertionSort(SortMetrics<T> sortMetrics) {
    super(sortMetrics);
  }

  void sortSubarray(T[] arr, int start, int end) {
    // NOTE: start is the index of the start of the subarray (inclusive) and end is
    // the index of the end of the sub array (inclusive)
    int insertionIndex;
    int foundIndex;
    T toInsert;
    for (int i = start + 1; i <= end; i++) {
      if (sortMetrics.comparator.compare(arr[i], arr[i - 1]) >= 0) {
        // this is the fast path to prevent binary search, making this O(n) for the
        // trivial sorted case (best case)
        continue;
      }
      toInsert = arr[i];
      sortMetrics.incrementCopies();
      foundIndex = sortMetrics.binarySearch(arr, arr[i], start, i - 1);
      insertionIndex = sortMetrics.comparator.compare(arr[i], arr[foundIndex]) >= 0 ? foundIndex + 1 : foundIndex;
      sortMetrics.rightShift(arr, insertionIndex, i);
      arr[insertionIndex] = toInsert;
      sortMetrics.incrementCopies();
    }
  }

  @Override
  void sort(T[] arr) {
    sortSubarray(arr, 0, arr.length - 1);
  }
}

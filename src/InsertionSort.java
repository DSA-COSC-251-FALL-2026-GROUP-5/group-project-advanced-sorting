public class InsertionSort<T> extends Sort<T> {

  InsertionSort(SortMetrics<T> sortMetrics) {
    super(sortMetrics);
  }

  @Override
  void sort(T[] arr) {
    int insertionIndex;
    int foundIndex;
    T toInsert;
    for (int i = 1; i < arr.length; i++) {
      if (sortMetrics.comparator.compare(arr[i], arr[i - 1]) >= 0) {
        // this is the fast path to prevent binary search, making this O(n) for the
        // trivial sorted case (best case)
        continue;
      }
      toInsert = arr[i];
      sortMetrics.incrementCopies();
      foundIndex = sortMetrics.binarySearch(arr, arr[i], 0, i - 1);
      insertionIndex = sortMetrics.comparator.compare(arr[i], arr[foundIndex]) >= 0 ? foundIndex + 1 : foundIndex;
      sortMetrics.rightShift(arr, insertionIndex, i);
      arr[insertionIndex] = toInsert;
      sortMetrics.incrementCopies();
    }
  }
}

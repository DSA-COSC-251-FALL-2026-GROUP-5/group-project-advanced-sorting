public class SelectionSort<T> extends Sort<T> {

  SelectionSort(SortMetrics<T> sortMetrics) {
    super(sortMetrics);
  }

  @Override
  void sort(T[] arr) {
    int minIndex = 0;
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = i; j < arr.length; j++) {
        if (sortMetrics.comparator.compare(arr[j], arr[minIndex]) < 0) {
          minIndex = j;
        }
      }
      sortMetrics.swap(arr, i, minIndex);
    }
  }
}

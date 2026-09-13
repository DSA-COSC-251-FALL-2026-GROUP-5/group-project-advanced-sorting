import java.util.Comparator;

public class SelectionSort<T> extends Sort<T> {
  @Override
  void sort(T[] arr, SortMetrics<T> sortMetrics) {
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

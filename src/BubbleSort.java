import java.util.Comparator;

public class BubbleSort<T> extends Sort<T> {
  @Override
  void sort(T[] arr, SortMetrics<T> sortMetrics) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < (arr.length - i - 1); j++) {
        if (sortMetrics.comparator.compare(arr[j + 1], arr[j]) >= 0) {
          // since we want this to be ascending order. Also, we want this to be stable.
          continue;
        }
        sortMetrics.swap(arr, j, j + 1);
      }
    }
  }
}

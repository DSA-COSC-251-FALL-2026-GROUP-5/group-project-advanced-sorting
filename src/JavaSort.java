import java.util.Arrays;

class JavaSort<T> extends Sort<T> {
  // NOTE: to get metrics, we essentially build a wrapper around swap and also the
  // comparator
  SortMetrics<T> sortMetrics;

  JavaSort(SortMetrics<T> sortMetrics) {
    super(sortMetrics);
  }

  @Override
  void sort(T[] arr) {
    // we'll mainly be looking at the run time because, we're not using any of the
    // sortMetrics utils, which internally logs the swaps
    Arrays.sort(arr);
  }
}

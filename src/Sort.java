abstract class Sort<T> {
  // NOTE: to get metrics, we essentially build a wrapper around swap and also the
  // comparator
  SortMetrics<T> sortMetrics;

  Sort(SortMetrics<T> sortMetrics) {
    this.sortMetrics = sortMetrics;
  }

  abstract void sort(T[] arr);
}

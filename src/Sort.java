import java.util.Comparator;

abstract class Sort<T> {
  // NOTE: to get metrics, we essentially build a wrapper around swap and also the
  // comparator

  abstract void sort(T[] arr, SortMetrics<T> sortMetrics);
}

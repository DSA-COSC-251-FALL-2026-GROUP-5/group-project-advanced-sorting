public class TimSort<T> extends Sort<T> {

  public record Run(int base, int length) {
  }

  TimSort(SortMetrics<T> sortMetrics) {
    super(sortMetrics);
  }

  @Override
  void sort(T[] arr) {
  }
}

import java.util.Comparator;
import java.util.Arrays;

public class InsertionSort<T> extends Sort<T> {
  @Override
  void sort(T[] arr, SortMetrics<T> sortMetrics) {
    // TODO: fix this
    int insertionIndex;
    int foundIndex;
    T toInsert;
    for (int i = 1; i < arr.length; i++) {
      toInsert = arr[i];
      foundIndex = sortMetrics.binarySearch(arr, arr[i], 0, i - 1);
      insertionIndex = sortMetrics.comparator.compare(arr[i], arr[foundIndex]) >= 0 ? foundIndex + 1 : foundIndex;
      sortMetrics.rightShift(arr, insertionIndex, i);
      arr[insertionIndex] = toInsert;
    }
  }
}

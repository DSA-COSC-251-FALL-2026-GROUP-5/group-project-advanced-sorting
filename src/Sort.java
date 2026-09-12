import java.util.Comparator;

abstract class Sort<T> {
  abstract void sort(T[] arr, Comparator<T> comparator);
}

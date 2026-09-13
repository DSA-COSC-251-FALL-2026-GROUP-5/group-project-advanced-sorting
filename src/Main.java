import java.util.Arrays;
import java.util.Comparator;

class Main {
  public static void main(String[] args) {
    SortMetrics<Integer> sortMetrics = new SortMetrics<Integer>(Integer::compare);
    Integer arr[] = { 3, 4, 5, 1, 17, 100, 1, 222 };
    BubbleSort<Integer> bubbleSort = new BubbleSort<Integer>();
    Arrays.toString(arr);
    sortMetrics.runSort(arr, bubbleSort);
    System.out.println(sortMetrics.getJsonContent());
  }
}

import java.util.Arrays;
import java.util.Comparator;

class Main {
  public static void main(String[] args) {

    /*
     * SortMetrics<Integer> sortMetrics = new
     * SortMetrics<Integer>(Integer::compare);
     * Integer arr[] = { 3, 4, 5, 1, 17, 100, 1, 222 };
     * sortMetrics.runSort(arr, new BubbleSort<Integer>());
     * sortMetrics.writeToFile("results/binarySearch.json");
     */

    SortMetrics<Integer> sortMetrics = new SortMetrics<Integer>(Integer::compare);
    Integer arr[] = { 3, 4, 5, 17, 100, 1, 222, 2, 2, 2 };
    sortMetrics.runSort(arr, new BubbleSort<Integer>());
    System.out.println(sortMetrics.getJsonContent());
    System.out.println(sortMetrics.binarySearch(arr, 1, 0, arr.length - 1));

  }
}

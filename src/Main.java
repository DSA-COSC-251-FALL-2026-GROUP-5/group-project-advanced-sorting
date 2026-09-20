import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

class Main {
  public static void main(String[] args) {

    /*
     * SortMetrics<Integer> sortMetrics = new
     * SortMetrics<Integer>(Integer::compare);
     * 
     * Integer arr[] = { 3, 4, 5, 17, 100, 1, 222, 2, 2, 2 };
     * 
     * // Integer arr[] = {};
     * sortMetrics.runSort(arr, new InsertionSort<Integer>());
     * System.out.println(sortMetrics.getJsonContent());
     * 
     */

    /*
     * SortMetrics<Integer> sortMetrics = new
     * SortMetrics<Integer>(Integer::compare);
     * Integer arr[] = { 3, 4, 5, 17, 100, 1, 222, 2, 2, 2 };
     * sortMetrics.runSort(arr, new BubbleSort<Integer>());
     * System.out.println(sortMetrics.getJsonContent());
     * System.out.println(sortMetrics.binarySearch(arr, 0, 0, arr.length - 1));
     */
    /*
     * Integer a[] = { 3, 4, 5, 17 };
     * System.out.println(sortMetrics.binarySearch(a, 100, 0, 3));
     */

    SortMetrics<Integer> sortMetrics = new SortMetrics<Integer>(Integer::compare);

    Integer arr[] = { 2 };

    Integer tmpArr[] = new Integer[arr.length];

    sortMetrics.merge(arr, tmpArr, 0, 0, 0, 0);

    System.out.println(sortMetrics.getJsonContent());
    System.out.println(Arrays.toString(arr));
  }
}

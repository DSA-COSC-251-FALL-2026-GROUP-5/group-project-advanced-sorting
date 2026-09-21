import java.util.Arrays;

class Main {
  public static void main(String[] args) {

    /*
     * SortMetrics<Integer> sortMetrics = new
     * SortMetrics<Integer>(Integer::compare);
     * 
     * Integer arr[] = { 3, 4, 5, 17, 100, 1, 222, 2, 2, 2 };
     * 
     * // Integer arr[] = {};
     * sortMetrics.toggleSign();
     * sortMetrics.runSort(arr, new MergeSort<Integer>(sortMetrics, new
     * Integer[arr.length]));
     * System.out.println(sortMetrics.getJsonContent());
     */

    /*
     * SortMetrics<Integer> sortMetrics = new
     * SortMetrics<Integer>(Integer::compare);
     * // Integer arr[] = { 3, 4, 5, 17, 100, 1, 222, 2, 2, 2 };
     * Integer arr[] = { 4, 3 };
     * sortMetrics.runSort(arr, new MergeSort<Integer>(sortMetrics, new
     * Integer[arr.length]));
     * System.out.println(sortMetrics.getJsonContent());
     * // System.out.println(Arrays.toString(arr));
     */

    /*
     * Integer a[] = { 3, 4, 5, 17 };
     * System.out.println(sortMetrics.binarySearch(a, 100, 0, 3));
     */

    /*
     * SortMetrics<Integer> sortMetrics = new
     * SortMetrics<Integer>(Integer::compare);
     * 
     * Integer arr[] = { 2, 4, 6, 1, 3, 5, 7, 9 };
     * 
     * Integer tmpArr[] = new Integer[arr.length];
     * 
     * sortMetrics.merge(arr, tmpArr, 0, 2, 3, arr.length - 1);
     * 
     * System.out.println(sortMetrics.getJsonContent());
     * System.out.println(Arrays.toString(arr));
     * 
     * sortMetrics.reverse(arr, 0, arr.length - 1);
     * 
     * System.out.println(sortMetrics.getJsonContent());
     * System.out.println(Arrays.toString(arr));
     * 
     */

    /*
     * SortMetrics<Integer> sortMetrics = new
     * SortMetrics<Integer>(Integer::compare);
     * Integer arr[] = { 10, 2, 11, 123, 111, 88, 90, 80, 70 };
     * InsertionSort<Integer> insertionSort = new
     * InsertionSort<Integer>(sortMetrics);
     * insertionSort.sort(arr);
     * System.out.println(Arrays.toString(arr));
     */

    SortMetrics<Integer> sortMetrics = new SortMetrics<Integer>(Integer::compare);

    /*
     * Integer arr[] = { 1, 3, 5, 7,
     * 2, 4, 6, 8,
     * 1, 3, 5
     * };
     */

    /*
     * Integer arr[] = {
     * 30, 31, 82, 33, 30, 7, 8, 3, 43, 61, 3, 72, 52, 42, 17, 1, 86, 36, 11, 98,
     * 71, 36, 36, 54, 32, 90, 79, 17, 73,
     * 44, 37, 58, 91, 54, 46, 58, 12, 50, 76, 22, 58, 83, 55, 34, 49, 78, 98, 3,
     * 32, 99, 72, 75, 67, 67, 16, 81, 39,
     * 2, 36, 80, 80, 79, 19, 20, 96, 6, 45, 42, 5, 16, 27, 95, 21, 92, 35, 11, 73,
     * 23, 20, 41, 64, 28, 91, 46, 12, 6,
     * 12, 82, 93, 62, 80, 64, 6, 27, 2, 44, 44, 7, 78, 55
     * };
     */

    Integer arr[] = { 2, 1 };

    // System.out.println(Arrays.toString(arr));
    TimSort<Integer> timSort = new TimSort<Integer>(sortMetrics, 5, new Integer[arr.length]);
    // System.out.println(timSort.findRun(arr, 9));
    sortMetrics.runSort(arr, timSort);
    System.out.println(sortMetrics.getJsonContent());
    // System.out.println(Arrays.toString(arr));
  }
}

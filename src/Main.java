class Main {
  public static void main(String[] args) {

    SortMetrics<Integer> sortMetrics = new SortMetrics<Integer>(Integer::compare);

    Integer arr[] = { 3, 4, 5, 17, 100, 1, 222, 2, 2, 2 };

    // Integer arr[] = {};
    sortMetrics.toggleSign();
    sortMetrics.runSort(arr, new MergeSort<Integer>(sortMetrics, new Integer[arr.length]));
    System.out.println(sortMetrics.getJsonContent());

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
  }
}

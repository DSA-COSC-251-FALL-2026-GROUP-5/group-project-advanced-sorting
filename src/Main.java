class Main {
  public static void main(String[] args) {
    SortMetrics<Integer> sortMetrics = new SortMetrics<Integer>(Integer::compare);
    Integer arr[] = { 3, 4, 5, 17, 100, 1, 222, 2, 2, 2 };
    sortMetrics.runSort(arr, new BubbleSort<Integer>(sortMetrics));
    System.out.println(sortMetrics.getResultsAsJson());
    sortMetrics.writeToFile("saved_output/saved.json");
    sortMetrics.resetMetrics();
  }
}

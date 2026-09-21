public class TimSort<T> extends Sort<T> {

  T tmpArr[];

  // this is a pointer to a runStack when we run the sorting algorithm
  Run runStack[] = null;
  // by convention, stackPtr will point to the next item to pop in the stack.
  int stackPtr = -1;
  int minRun;

  TimSort(SortMetrics<T> sortMetrics, int minRun, T tmpArr[]) {
    // NOTE: make sure the temporary array is of the same size, or even bigger than
    // the array we're using.
    super(sortMetrics);
    this.tmpArr = tmpArr;
    this.minRun = minRun;
  }

  @Override
  void sort(T[] arr) {
    // the first step we do is to initialize an array that's of size that's at least
    // Math.ceil(arr.length/minRun)
    runStack = new Run[(int) Math.ceil(arr.length / ((float) minRun))];

    // so the first thing we do is do the find run routine

    // at the end of the algorithm, change the pointer to null so that the garbage
    // collector can take care of the rest
    runStack = null;
  }
}

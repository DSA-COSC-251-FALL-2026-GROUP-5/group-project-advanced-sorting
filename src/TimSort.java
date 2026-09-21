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

  public Run findRun(T[] arr, int startIndex) {
    int currentIndex = startIndex;
    // currentIndex will need a lee-way of around 1 element, so i can only allow
    // currentIndex to be at most at arr.length - 2. I hope this breaks when it
    // reaches the end
    while ((currentIndex <= (arr.length - 2))
        && (sortMetrics.comparator.compare(arr[currentIndex], arr[currentIndex + 1]) == 0)) {
      currentIndex++;
    }

    if (currentIndex == arr.length - 1) {
      // TODO: fix off-by-one error on this one
      return new Run(startIndex, currentIndex - startIndex + 1);
      // if startIndex haven't moved, then we have an array of sized 1, although, that
      // would be kinda useless, but it might happen if we were to reach the end of
      // the array
    }

    // ok so we now know that there's actually something at currentIndex + 1, so we
    // can safely index it

    boolean isAscending;
    // the reason why the while loop broke was because arr[currentIndex] !=
    // arr[currentIndex + 1]

    isAscending = sortMetrics.comparator.compare(arr[currentIndex + 1], arr[currentIndex]) > 0;
    // if it's greater than zero, then treat it as ascending:
    // NOTE: essentially, the .compare might switch signs depending on whether we've
    // called toggleSign or setSign. Essentially, we can assume that the .compare
    // hasn't been modified and do as follows. I think it will actually be fine even
    // when you toggle the sign, it's just that everything will be in reverse order

    // TODO: now we need to find the runs based on whether it's ascending or
    // descending, perhaps a sign toggle is all we need (also, ascending and
    // descending is relative on whether we've toggled the sign before, we assume
    // that ascending and descending is when we haven't toggled the sign)

    if (!isAscending) {
      sortMetrics.toggleSign();
    }

    // essentially, we toggle it so that the logic becomes the same, whether it's
    // decreasing or increasing, but we look at it as if isAscending is true

    while ((currentIndex <= (arr.length - 2))
        && (sortMetrics.comparator.compare(arr[currentIndex + 1], arr[currentIndex]) >= 0)) {
      currentIndex++;
    }

    // if it breaks, then currentIndex will point to either the last index of the
    // array, or the last index of the run, so, its length will be currentIndex -
    // startIndex + 1

    // toggle it back to prevent side effects (it's a bit scuff, but i'm lazy)
    if (!isAscending) {
      sortMetrics.toggleSign();
    }

    return new Run(startIndex, currentIndex - startIndex + 1);
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

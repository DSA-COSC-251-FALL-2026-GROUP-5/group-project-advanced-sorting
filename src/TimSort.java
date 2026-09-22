import java.util.Arrays;

public class TimSort<T> extends Sort<T> {

  T tmpArr[];

  // this is a pointer to a runStack when we run the sorting algorithm
  Run runStack[] = null;
  // by convention, runStackPtr will point to the next item to pop in the stack.
  int runStackPtr = -1;
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
      return new Run(startIndex, currentIndex - startIndex + 1, true);
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

    return new Run(startIndex, currentIndex - startIndex + 1, isAscending);
  }

  void mergeTopTwoRun(T arr[]) {
    // merges s[n] with s[n - 2] and then updates the runStack
    sortMetrics.merge(arr,
        tmpArr,
        runStack[runStackPtr - 1].startIndex(),
        runStack[runStackPtr - 1].startIndex() + runStack[runStackPtr - 1].length() - 1,
        runStack[runStackPtr].startIndex(),
        runStack[runStackPtr].startIndex() + runStack[runStackPtr].length() - 1);

    Run mergedRun = new Run(runStack[runStackPtr - 1].startIndex(),
        runStack[runStackPtr - 1].length() + runStack[runStackPtr].length(), true);

    // set unused to null to make it easier for debugging
    runStack[runStackPtr] = null;
    runStack[runStackPtr - 1] = mergedRun;
    runStackPtr = runStackPtr - 1;
  }

  void mergeBottomTwoRun(T arr[]) {
    // merges s[n - 1] with s[n - 2] and then updates the runStack
    sortMetrics.merge(arr,
        tmpArr,
        runStack[runStackPtr - 2].startIndex(),
        runStack[runStackPtr - 2].startIndex() + runStack[runStackPtr - 2].length() - 1,
        runStack[runStackPtr - 1].startIndex(),
        runStack[runStackPtr - 1].startIndex() + runStack[runStackPtr - 1].length() - 1);

    Run mergedRun = new Run(runStack[runStackPtr - 2].startIndex(),
        runStack[runStackPtr - 2].length() + runStack[runStackPtr - 1].length(), true);

    // so we're going to need to do something weird here. Since we've combined s[n -
    // 1] and s[n - 2] into one, we'll first store the merged thing inside of s[n -
    // 2]
    runStack[runStackPtr - 2] = mergedRun;

    // then we'll copy the run from s[n] to s[n - 1] to make it continuous
    runStack[runStackPtr - 1] = runStack[runStackPtr];

    // then we'll make s[n] null to make it easier to debug
    runStack[runStackPtr] = null;
    runStackPtr--;

  }

  void mergeCollapse(T arr[]) {
    // merge collapse assumes that the only reason why the invariance is broken is
    // because
    // you added something to the top of the stack that broke the invariance, and
    // will try to repair the invariance top-down

    // this isn't a "very pure function" but I'm lazy because this function is very
    // specific. We just assume that the runStack has already been initialized,
    // meaning that mergeCollapse is a function that should only be called in the
    // `sort` method
    while (runStackPtr >= 2) {
      // if the last element of the stack is at index 2, then we have enough to
      // continue here

      // the first invariance states that s[n] + s[n - 1] < s[n - 2]
      boolean firstInvarianceMet = (runStack[runStackPtr].length()
          + runStack[runStackPtr - 1].length()) < runStack[runStackPtr - 2].length();

      // second invariance states that s[n] < s[n - 1]
      boolean secondInvarianceMet = runStack[runStackPtr].length() < runStack[runStackPtr - 1].length();

      // we view it as if we're trying to sort in ascending
      if (firstInvarianceMet && secondInvarianceMet) {
        // if the first invariance is met, then we need to consider the second
        // invariance
        break;
      }

      if (firstInvarianceMet && !secondInvarianceMet) {
        // if the second invariance isn't met, that is, we have a case where:
        // ####
        // ##
        // ############
        //
        // then, we merge the top two, that is, we merge s[n] with s[n - 1]
        mergeTopTwoRun(arr);
      } else if (!firstInvarianceMet) {
        // if the first invariance isn't even met (we don't even yet care about the
        // second invariance), we merge the minimum of either s[n] or s[n - 2]

        if (runStack[runStackPtr].length() > runStack[runStackPtr - 2].length()) {
          // so, if we have something like:
          // ###############
          // ######
          // ############
          //
          // then we merge the bottom two
          mergeBottomTwoRun(arr);
        } else {
          mergeTopTwoRun(arr);
        }
      }
    }

    // when we have 2 elements in the stack, we should still repair the
    // second invariance
    if (runStackPtr == 1 && (runStack[runStackPtr].length() >= runStack[runStackPtr - 1].length())) {
      // so if we have something like:
      // #############
      // #####
      //
      // then repair it by merging it

      // System.out.printf("merged run: %s with %s\n", runStack[runStackPtr],
      // runStack[runStackPtr - 1]);
      mergeTopTwoRun(arr);
    }
  }

  void mergeForceCollapse(T arr[]) {
    // force merge collapse just repeatedly merge s[n] with s[n - 1] until the run
    // is of size 1
    while (runStackPtr > 0) {
      mergeTopTwoRun(arr);
    }
  }

  @Override
  void sort(T[] arr) {
    // the first step we do is to initialize an array that's of size that's at least
    // Math.ceil(arr.length/minRun)

    if (arr.length <= 1) {
      // just deal with the trivial cases to not think about edge cases
      return;
    }

    runStack = new Run[(int) Math.ceil(arr.length / ((float) minRun))];

    // so the first thing we do is do the find run routine

    // although we can find the run and then do a mergeCollapse on the fly, I think
    // it would be better conceptually to decouple it, even though it will take up
    // more memory and more time because it will make it a bit easier to debug

    Run discoveredRun[] = new Run[(int) Math.ceil(arr.length / ((float) minRun))];
    int discoveredRunPtr = -1;

    int runPtr = 0;

    // create a new insertionSort instance because, we will use it to make the runs
    // at least minRun (except for the boundaries)
    InsertionSort<T> insertionSort = new InsertionSort<T>(sortMetrics);

    while (runPtr <= arr.length - 1) {
      Run run = findRun(arr, runPtr);
      int endIndex;
      if (run.length() < minRun && (run.startIndex() + run.length()) < arr.length) {
        // if run.startIndex() + run.length() >= arr.length, then we know that `run` is
        // actually the subarray on the edge, so we don't need to extend it by sorting
        // or anything, we can just add that to the to the `runStack`

        // we still need to be careful of boundary conditions
        endIndex = Math.min(run.startIndex() + minRun - 1, arr.length - 1);

        // if the run is descending, then sort it in descending order
        if (!run.isAscending()) {
          sortMetrics.toggleSign();
        }

        insertionSort.sortSubarray(arr, run.startIndex(), endIndex);

        if (!run.isAscending()) {
          sortMetrics.toggleSign();
        }

        run = new Run(run.startIndex(), endIndex - run.startIndex() + 1, run.isAscending());
      } else {
        endIndex = runPtr + run.length() - 1;
      }

      // reverse it if it's in descending order
      if (!run.isAscending()) {
        /*
         * System.out.println("reversing... our subarray is: ");
         * for (int i = runPtr; i <= endIndex; i++) {
         * System.out.printf("%d, ", arr[i]);
         * }
         * System.out.println();
         */
        sortMetrics.reverse(arr, runPtr, endIndex);
        run = new Run(run.startIndex(), run.length(), true);
      }

      runPtr = endIndex + 1;

      // we store it in the discoveredRun array, which we will loop through afterwards
      discoveredRunPtr++;
      discoveredRun[discoveredRunPtr] = run;
    }

    /*
     * for (int i = 0; i <= discoveredRunPtr; i++) {
     * System.out.println(discoveredRun[i]);
     * }
     */

    // System.out.println(Arrays.toString(arr));

    // now we're in the mergeCollapse stage, where we begin pushing things into the
    // runStack, while preserving our invariance

    // System.out.println(Arrays.toString(discoveredRun));

    for (int i = 0; i <= discoveredRunPtr; i++) {
      runStackPtr++;
      runStack[runStackPtr] = discoveredRun[i];
      mergeCollapse(arr);
    }

    /*
     * for (int i = 0; i <= runStackPtr; i++) {
     * System.out.println(runStack[i]);
     * }
     */

    mergeForceCollapse(arr);

    // at the end of the algorithm, change the pointer to null so that the garbage
    // collector can take care of the rest
    runStack = null;
    runStackPtr = -1;
  }
}

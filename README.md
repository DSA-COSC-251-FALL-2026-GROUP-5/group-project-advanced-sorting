# TODO

## Coding side

- [] use a generic sorting class, and then fill in the blank for each sorting algorithm as a subclass
- [] generate or write a test suite
- [] write a `.isSorted` function in `Sort.java`.
- [] write a `.swap` function in `Sort.java`.
- [] save sorted results to a `.txt` file (I suppose it can be json, as long as it's a plain text file)
- [] log performance results such as:
  - [] dataset size
  - [] runtime
  - [] number of comparisons
  - [] number of swaps
  - [] any other meaningful metrics
- [] logging should be implemented in `Sort.java`
- [] write a small CLI to use different sorting algorithms I guess
- [] compare it to different sorting algorithms

## Research report and presentation slides

- [] A clear explanation of the key characteristics of the sorting algorithm.
- [] A step-by-step demonstration of how the algorithm works on a small dataset.
- [] An analysis of the algorithm’s time complexity, including best-case, average-case, and worst-case
- [] A discussion of situations where the algorithm performs well and why.
- [] A discussion of situations where the algorithm should not be used and why.
- [] Real-world examples where the algorithm is applied and reasons for choosing it over other algorithms
- [] A description of the Java implementation.
- [] Testing results for the small, medium, and large datasets.
- [] Runtime, comparisons, swaps, movements, or other relevant performance metrics.
- [] A comparison with Bubble Sort, Selection Sort, and Insertion Sort.

## Research report

Introduction
- [] Algorithm explanation with examples
- [] Time complexity analysis
- [] Java implementation
- [] Testing methodology
- [] Performance results for 5, 1,000, and 1,000,000 elements
- [] Comparison with Bubble Sort, Selection Sort, and Insertion Sort
- [] Appropriate and inappropriate use cases
- [] Real-world applications
- [] Conclusion and findings

Project requirement is linked [here](./assignment.pdf).

# Notes

- [timsort](https://mail.python.org/pipermail/python-dev/2002-July/026837.html)
- [worst case complexity of timsort](https://arxiv.org/abs/1805.08612)

## Merge sort implementation

A typical merge sort implementation goes as follows:

- we first have an unsorted array
- at every step, we have two pointers pointing to where the subarray starts and where the subarray ends
- we recursively call merge sort on the left half and the right half until the subarray is of length 1 or less
- we then call merge on the subarray by passing the pointers to the left sub array and the pointers to the right subarray as arguments



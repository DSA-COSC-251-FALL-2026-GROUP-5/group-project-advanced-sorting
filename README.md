# TODO

## Coding side

- [x] use a generic sorting class, and then fill in the blank for each sorting algorithm as a subclass
~~- [] generate or write a test suite~~
~~- [x] write a `.isSorted` function in `Sort.java`.~~
~~- [x] write a `.swap` function in `Sort.java`.~~
- [x] save sorted results to a `.txt` file (I suppose it can be json, as long as it's a plain text file)
- [x] log performance results such as:
  - [x] dataset size
  - [x] runtime
  - [x] number of comparisons
  - [x] number of swaps
  - [x] any other meaningful metrics
~~- [x] logging should be implemented in `Sort.java`~~
~~- [] write a small CLI to use different sorting algorithms I guess~~
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

## Tim sort implementation

So Tim sort is quite a complicated algorithm, moreover, it isn't as clearly defined as algorithms like merge sort, bubble sort, etc. Tim sort is a hybrid sorting algorithm that is based on insertion sort and merge sort which is optimized for practical data. There are different levels of optimization when it comes to Tim sort, what we will implement will utilize the most basic of optimizations that characterizes the Tim sort algorithm.

So we will implement something as follows:

- finding runs at the start, making runs into the size of `minRun`, and trying to detect decreasing runs and then reversing them to prevent insertion sort's worse case
- maintaining some sort of stack of sub-arrays which maintains an invariant like (A + B > C and B + C > D, etc...)
- merging runs in order to maintain that invariant
- once we no long have any runs left, we will merge them similar to how you apply the merge operator for a `reduce` function
- once you have only one array left, that's your sorted array.

### Justification for Fibonacci-like invariant

If we have a stack of subarrays. We denote `s` to be an array of the length of the subarray.

The Fibonacci-like invariant imposes the constraint such that:

`s[n] + s[n+1] < s[n + 2]`

> [!NOTE]
> when we're pushing elements onto the stack, let's just say for simplicity, we push it straight to `s[0]`


Now, why would such an invariant be helpful?

Well, consider the case when we have a stack such that `s[n] + s[n+1] = s[n+2]`, and so on. Then, when we merge `s[n]` and `s[n + 1]`, we would get an array that's the same size as `s[n+2]`, meaning that merging them would be the most optimal, and so on (since merging is most optimal for 2 arrays with equal length).

Now, rather than an equality, we're specifying a greater than, which in practice, kinda means that `s[n] + s[n+1]` is a slight bit bigger than `s[n+2]`. The cases when `s[n]` is really small has already been taken care of in the `minRun` constraint. 

Moreover, let's say that we have a stack that has the following invariant, and we decide to push in an absurdly large run. Then, we essentially start merging the runs on our stack until we've merged enough such that either we can't get any bigger, or we've merged enough so that the run that's on the stack, is about the same size as the one we're about to push in. I think such a behavior is pretty good.

### Finding runs algorithm

So finding runs is kinda weird, because we need to decide when we should treat the run to be in descending order. I think, here's how we're going to do it:

- if we're starting at `n`, peak at `n + 1`, the order of that will determine the order of the whole run. If it's in descending order, and we haven't reached `minRun`, then we have to sort it in descending order.

### Pseudo code (in English)

- So, first of all, we initialize a stack, and can be conservative and initialize the stack to just be an array of size `Math.ceil(arr.length/minRun)`

> [!NOTE]
> the reason why we want to make our own stack is because, it's quite simple, moreover, we want to count the operations, essentially, one stack push is going to consist of 1 copy operation, which we want to take note of, and a pop is essentially free.

- the stack will consist of a sort of struct (it's called a record in Java)

- we will start with the run finding routine, so let's initilize a pointer `ptr`, and we move the pointer until we reach the end of the array.

- first of all, we peak and see whether it's increasing or decreasing. If it's increasing then we'll call like `findRun` or something starting at the `ptr` (find run should actually be in `SortMetrics` because we also need to log the comparisons) also, `findRun` should use the comparator inside of the `SortMetrics` instance, which you can switch the sign to find an ascending run or a descending run

- once we've found the run, we then check whether the run is less than min run. If it is less than `minRun`, then we just run binary insertion sort on that run to `ptr + minRun` or something, then we try to push that run into the stack

- when we're pushing anything into the stack, we gotta check for the invariant and repair the invariant, which goes as follows:

  - if the stack is of length that's equals to 0, then we just push onto it
  - if it's of length 1, then we merge if it's greater than the one we have in the stack
  - if the stack is of length 2 or greater, then what we do is push as follows:
    - we check whether the invariant is met, that is, `s[n] + s[n - 1] < s[n - 2]` (we're now going to make the stack in reverse order, because that's how we'll implement the stack, where n is the next element to pop out.
    - if the invariant is met, then we can just push onto the stack if the `s[n] < s[n - 1]`, if not, merge `s[n]` and `s[n - 1]` and push that onto the stack (also, when I say `s[n]`, we don't actually have to push it onto the stack, then pop it back out or anything, there can be equivalent representation)
    - now, in the case that the invariant isn't even met, and we assume that the stack already follows the invariant, then what we must do is to merge some stuff in the stack to make it the invariant hold
    - so we do as follows: we take the `s[n - 1]` and merge it with either `s[n]` or `s[n - 2]`, whichever one is lesser than. We then try to push that merged stuff back into the stack, and then check whether the invariant holds, if not, repeat the steps until the invariant holds (so this is very similar to the heapify algorithm)
    - so once we finish this push while preserving invariant, we then try to insert the next run and continue doing so until we no long have any runs left (so, it seems like this "heapify-equivalent" invariant preserving process can be its own function)
    - so now, we will begin the reducing phase, which is to merge the `s[n]` and `s[n - 1]`, then use that to merge the `s[n - 2]` element, etc, until we finally have no more left in the stack, at which we return with the sorted array.

So we actually aren't done yet, because I still have to figure out what to do in this "invariant-preserving push". So we can actually use Java's own way of structuring the Tim sort algorithm, and this "invariant-preserving push" thing is called `mergeCollapse`, meanwhile, the reduction stage is called `mergeForceCollapse`

I think `mergeForceCollapse` is quite simple which is what I've already described as the "reduction stage" 

We'll talk how to implement `mergeCollapse` now

### `mergeCollapse` implementation

So essentially, our `mergeCollapse` implementation just requires that our stack preserve the invariant. So here's what we'll do:

- we'll have a function called `invariantMet` and that basically checks whether our stack has the invariant, that is, whether our stack follows `s[n] + s[n - 1] < s[n - 2]` etc.
- while `invariantMet` gets called and returns false, then here's what we'll do. We know that when we started off, the invariant wasn't met because what we've inserted is way bigger than the bottom. So we merge the minimum of either `s[n]` with `s[n -1]` or `s[n-1]` with `s[n - 2]`. Once we've merged, then it might be the case that the invariant isn't met now, because some item in the middle, so we now have to resolve that.
- this means that we can generalize where we search from the back to the front, and the first thing that breaks the invariant will go through some merging logic.

Ok... so Java uses the following invariant:


```
s[n] + s[n - 1] < s[n - 2]
```

AND

```
s[n] < s[n - 1]
```

which means that it's strictly decreasing towards the end (or increasing if you view it backwards).

Let's analyze it for some case:

```
####
########################################
##########
```

So, first condition is met, but second condition isn't met, so we need to merge the top 2:

```
############################################
##########
```

Now, we can actually argue that if the second condition is imposed from the beginning, then this should never even happen in the first place, so even though this is a really bad case, espeically if we starting getting a bunch of `####`, the second condition actually makes this impossible.

So we could only get:

```
##########
########################################
```

Then it wouldn't be satisifed, so we would merge:

```
##############
########################################
```

And if we continued getting the same sample, we would continue increasing the top, which is what we wanted. I think this behavior is favorable. If we assume that the bottom already has the invariant, then we can resolve the invariant when you get a push is one swoop, because, the reason why the invariant broke was because you inserted something that's too small, so, if it is already the case that the invariant is already met, then if you were to say, merge the small subarray you pushed with `s[n - 1]`, then it would still satisfy the first condition, but perhaps the second condition would be violated, so I guess you'd need the `while invariantMet` thing.

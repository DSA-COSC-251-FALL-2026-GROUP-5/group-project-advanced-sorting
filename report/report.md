---
title: "Timsort"
author: "Group 5"
toc: true
toc-depth: 2
number-sections: true
---


- [] Algorithm explanation with examples
- [] Time complexity analysis
- [] Java implementation
- [] Testing methodology
- [] Performance results for 5, 1,000, and 1,000,000 elements
- [] Comparison with Bubble Sort, Selection Sort, and Insertion Sort
- [] Appropriate and inappropriate use cases
- [] Real-world applications
- [] Conclusion and findings


# Introduction

Timsort is a hybrid, stable sorting algorithm, derived from merge sort and insertion sort, designed to perform well on many kinds of real-world data. It was implemented by Tim Peters in 2002 for use in the Python programming language. The algorithm finds subsequences of the data that are already ordered (runs) and uses them to sort the remainder more efficiently.

# Methodology

## Empirical Run-Time Testing Methodology

The most obvious data set we should use is integers, however, that begs the question, what should be the range of the randomly generated integer be? Suppose that we generate random integers in the range between $[1, k]$ and we generate a list of size $n$. If $k >> n$, then we would rarely get duplicates, meanwhile, if $k << n$, then we will get many duplicates.

Our parameter will then be the ratio $r = \frac{n}{k}$ where a high value of $r$ would mean more duplicates (I think the duplicate can be thought of as the expected number of duplicates, although, I'm not too certain on the probabilistic analysis on that). We can select something like $r = 10$ for data with high duplication, and $r = 0.1$ for low duplication.



# Time complexity analysis

# Performance results

# Conclusion

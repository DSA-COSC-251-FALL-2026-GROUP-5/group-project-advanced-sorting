# Video presentation

- [Presentation slides is linked here](https://www.canva.com/design/DAHV0yK9FWU/PDktLT631XDTwHdCIhK8zQ/edit)
- [Video presentation is linked here](https://youtu.be/SsERZtwMEks).

# Report

The report is linked [here in PDF format](./report/report.pdf).

The [GitHub on where it's stored is linked is here](https://github.com/DSA-COSC-251-FALL-2026-GROUP-5/group-project-advanced-sorting).

The absolute link to the pdf is [linked here](https://github.com/DSA-COSC-251-FALL-2026-GROUP-5/group-project-advanced-sorting/blob/main/report/report.pdf).


# Java source code

Java source code is stored inside of the `src` directory, linked [here](./src/).

# Usage

Make sure you're in a bash or zsh terminal. If you're not in a bash terminal, simply look in the file and run the commands in the script, assuming you have `java` and `javac` in your path. There are 3 bash scripts in the root directory 

In order to build the project, run:

```
./build
```

This will compile the Java code into Java Byte code inside of the `out` directory. To run `Main` class, run the command:

```
./run
```

To collect the data, we can run the collect data script, which will run our `CollectData` class's main function.

```
./collect_data
```

## Data Analysis

A simple data analysis was done using python in order to measure the mean and standard deviation of the runs. This was done in a `marimo` notebook.

To view the notebook, first change your directory to `scripts`, then run:

```
uv sync
```

to install `marimo` (assuming you have `uv`, otherwise, you can install `marimo` using `pip`).

If you're using `uv`, run:

```
uv run marimo edit .
```

otherwise, if you have marimo installed globally, run:

```
marimo edit .
```

In the page, you can click on the `data_analysis.py` notebook to view it.

## Using the sorting interface

So, in `Main.java` you can access all of the classes in the `*Sort.java` files. The code to run Timsort in `Main.java` for integers is as follows:

```java
class Main {
  public static void main(String[] args) {
    SortMetrics<Integer> sortMetrics = new SortMetrics<Integer>(Integer::compare);
    Integer arr[] = { 3, 4, 5, 17, 100, 1, 222, 2, 2, 2 };
    sortMetrics.runSort(arr, new TimSort<Integer>(sortMetrics, 32, new Integer[arr.length]));
    System.out.println(sortMetrics.getResultsAsJson());
    sortMetrics.writeToFile("saved_output/saved.json");
    sortMetrics.resetMetrics();
  }
}
```

- the `SortMetrics` class implements all of the utility functions such as `swap`, `rightShift`, `merge`, `compare` such that it can log all of the metrics for us to export and analyze later.
- every sorting algorithm has its own class, which all inherits from an abstract base class of the type `Sort` which has a `Sort.sort` method, which `SortMetrics.run` wraps around, in order to get the runtime data.
- `SortMetrics.getResultsAsJson` will format the metrics data as json along side with the sorted and unsorted array. If we only want the metadata, we can use the `SortMetrics.getMetadataAsJson` method.
- the `SortMetrics.resetMetrics` method simply resets the internal counters for metrics such as run time, swaps, comparisons, etc.
- to save to file, we can use the `SortMetrics.writeToFile` method, which will save the content of `SortMetrics.getResultsAsJson` to a file of a specified path

In place of `TimSort`, we can similarly initialize `MergeSort`, `BubbleSort`, etc.

```java
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
```

# AI usage

- AI was used to format the `results.json` into markdown tables format
- AI was used to provide some Java syntax 
- AI was used to discuss high level design decisions for code structure (such as how to structure the classes, and how to record the metrics)
- AI was used to discuss the implementation of the Tim Sort algorithm
- AI was not used in the data analysis process
- AI was not used to write the report
- AI was not used to write any of the code

import marimo

__generated_with = "0.24.2"
app = marimo.App(width="medium")


@app.cell
def _():
    import marimo as mo
    import json
    import statistics

    return json, statistics


@app.cell
def _(json):
    def _():
        with open("../results/bubbleSort/0.100000/5/0000.json") as file:
            s = json.load(file)
            print(s)

    return


@app.cell
def _():
    sorts = [
        "bubbleSort",
        "insertionSort",
        "javaSort",
        "mergeSort",
        "selectionSort",
        "timSort"
    ]

    duplication_ratios = [
        "0.100000",
        "10.000000"
    ]

    dataset_sizes = [
        "5",
        "1000",
        "1000000"
    ]

    prefixes = []
    for sort in sorts:
        for duplication_ratio in duplication_ratios:
            for dataset_size in dataset_sizes:
                prefixes.append(f"{sort}/{duplication_ratio}/{dataset_size}")
    prefixes
    return


@app.cell
def _(json, statistics):
    valid_prefixes = [
      "bubbleSort/0.100000/5",
      "bubbleSort/0.100000/1000",
      "bubbleSort/10.000000/5",
      "bubbleSort/10.000000/1000",
      "insertionSort/0.100000/5",
      "insertionSort/0.100000/1000",
      "insertionSort/10.000000/5",
      "insertionSort/10.000000/1000",
      "javaSort/0.100000/5",
      "javaSort/0.100000/1000",
      "javaSort/0.100000/1000000",
      "javaSort/10.000000/5",
      "javaSort/10.000000/1000",
      "javaSort/10.000000/1000000",
      "mergeSort/0.100000/5",
      "mergeSort/0.100000/1000",
      "mergeSort/0.100000/1000000",
      "mergeSort/10.000000/5",
      "mergeSort/10.000000/1000",
      "mergeSort/10.000000/1000000",
      "selectionSort/0.100000/5",
      "selectionSort/0.100000/1000",
      "selectionSort/10.000000/5",
      "selectionSort/10.000000/1000",
      "timSort/0.100000/5",
      "timSort/0.100000/1000",
      "timSort/0.100000/1000000",
      "timSort/10.000000/5",
      "timSort/10.000000/1000",
      "timSort/10.000000/1000000"
    ]

    valid_prefix = valid_prefixes[0]
    # first look at runtime

    metrics_to_extraction_function = {
        "runTime": lambda metadata: metadata["runTime"]*1e-9, # we convert the nano seconds to seconds
        "comparisons": lambda metadata: metadata["comparisons"],
        "swaps": lambda metadata: metadata["swaps"],
        "copies (excluding swaps)": lambda metadata: metadata["copies"],
        "copies (including swaps)": lambda metadata: 3*metadata["swaps"] + metadata["copies"],
    }

    metrics_mean_and_stdev = dict()
    for metrics, extracting_function in metrics_to_extraction_function.items():
        prefix_to_result = dict()
        prefix_to_mean_and_stdev = dict()
        for valid_prefix in valid_prefixes:
            prefix_to_result[valid_prefix] = list()
            for i in range(100):
                path = f"../results/{valid_prefix}/{i:04}.json"
                with open(path) as file:
                    prefix_to_result[valid_prefix].append(extracting_function(json.load(file)))
            prefix_to_mean_and_stdev[valid_prefix] = (statistics.mean(prefix_to_result[valid_prefix]),
                                                      statistics.stdev(prefix_to_result[valid_prefix]))
        metrics_mean_and_stdev[metrics] = prefix_to_mean_and_stdev
    
    with open("results.json", "w", encoding="utf-8") as results_file:
       json.dump(metrics_mean_and_stdev, results_file, indent=4)
    return


@app.cell
def _():
    return


if __name__ == "__main__":
    app.run()

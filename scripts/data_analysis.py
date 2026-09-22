import marimo

__generated_with = "0.19.6"
app = marimo.App(width="medium")


@app.cell
def _():
    import marimo as mo
    import json
    return (json,)


@app.cell
def _(json):
    with open("../results/bubbleSort/0.100000/5/0000.json") as file:
        s = json.load(file)
        print(s)
    return


@app.cell
def _():
    return


if __name__ == "__main__":
    app.run()

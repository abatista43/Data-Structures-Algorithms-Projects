# Project 5: Percolation

This is the directions document for Project 5 Percolation in CompSci 201 at Duke University, Fall 2023.


**See [the details document](docs/details.md) for information** on using Git, starting the project, and more details about the project including information about the classes and concepts that are outlined briefly below. You'll absolutely need to read the information in the [details document](docs/details.md) to understand how the classes in this project work independently and together. The _details_ document also contains project-specific details. This current document provides a high-level overview of the assignment.

**You are STRONGLY encouraged to work with a partner on P5!** (and on P6 and P7). See [the details document](docs/details.md) for information on using Git with a partner and how the workflow can proceed. If you'd like to be paired (somewhat randomly, but you can write about yourself or a partner) then fill [out this form to request a pairing](https://forms.office.com/r/nv58WSSsUh).

## Outline 
- [Project Introduction](#project-introduction)
- [Part 1: `PercolationDFS`](#part-1-percolationdfsfast)
- [Part 2: `PercolationBFS`](#part-2-percolationbfs)
- [Part 3: `PercolationUF`](#part-3-percolationuf)
- [Benchmarking and Analysis](#benchmarking-and-analysis)
- [Submitting, Reflect, and Grading](#submitting-reflect-and-grading)


## Project Introduction

In this assignment, you will write a program to estimate the value of the [percolation threshold](http://en.wikipedia.org/wiki/Percolation_thresholds) via [Monte Carlo](http://en.wikipedia.org/wiki/Monte_Carlo_method) simulation. In doing so, you will better understand depth-first-search, breadth-first-search, union-find structures, and the use of computer simulations for statistical inquiry. _**Your goal will be to explore trade-offs in several approaches to estimate the percolation threshold in an NxN system.**_ 

### Acknowledgements 
This assignment originated at Princeton; thanks to Kevin Wayne from whom staff at Duke first learned of it, and to Jeff Forbes when he was at Duke for ensuring we kept it as part of the assignment-stack at Duke.


### Vocabulary
In a system of rectangular/square NxN grid cells, each cell is sometimes referred to as a _site_ in the assignment and in supporting material, including this background.
- All sites start as **blocked**
- When a site is chosen at random it is **open**
- Open sites connected to the top row are **full**.
- A system in which there's a full cell in the bottom row (and thus a path of full cells from top to bottom) **percolates**.

Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Given a composite system composed of randomly distributed insulating and metallic materials, what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Scientists have defined an abstract process known as percolation to model such situations.

We model a percolation system using an N-by-N grid of sites. **Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites.** In diagrams we color full sites blue to model water flowing from the top through the system. We say the **system percolates if there is at least one full site in the bottom row.** In other words, a system percolates if there is a path of open sites from the top row to the bottom row. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.

For more on percolation see the [Princeton Case Study](http://introcs.cs.princeton.edu/java/24percolation/).

![An example of a percolating vs non-percolating system](./p6-figures/P6-percolates.png)

## Percolation Threshold

**(Rather than include this in the [the details document](docs/details.md) we include this information here. This project is a simulation).**

The percolation threshold problem is: if sites are independently set to be open with probability p (and therefore blocked with probability (1 − p), what is the probability that the system percolates? In other words, in a N-by-N grid, would the system percolate if N^2 randomly chosen cells are opened?  When p equals 0, the system does not percolate; when p equals 1, the system percolates. The plots below show the site vacancy probability p versus the percolation probability for 20-by-20 random grid (left) and 100-by-100 random grid (right).

![Threshold of percolating vs non-percolating system](./p6-figures/P6-threshold.png)

When N is sufficiently large, there is a threshold value `p*` such that when `p < p*` a random N-by-N grid almost never percolates, and when `p > p*`, a random N-by-N grid almost always percolates. No mathematical solution for determining the percolation threshold `p*` has yet been derived. Your task is to write a suite of computer programs to visualize the percolation process and estimate `p*` using Monte Carlo techniques. As you can see above, the percolation threshold in an NxN grid is about 0.593. The size of the grid doesn't matter as your simulations will show. 

### Helpful Videos 
The video linked here helps explain the techniques you'll read about. This video may be helpful after reading the assignment, or to get grounded before reading. This video may help situate the classes you write: `PercolationDFS`, `PercolationBFS`, and `PercolationUF`. You should also view [the recording from class on November 8](https://duke.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=64090dc9-2a30-455c-8bcf-b05f00e03d00) if you weren't there to see the `InteractivePercolationVisualizer` being used.

1. [From DFS to Union-Find: two approaches compared/contrasted](https://youtu.be/a_YMnW4hzmI)

### MazeDemo
The program `MazeDemo.java` within the project you get (`src/MazeDemo.java`) is a model for DFS, BFS, and Union-Find.  You should study the program to reinforce your understanding of these concepts. Two videos of solving a maze using DFS and BFS, respectively, can be found linked below, looking at them and the source code for `MazeDemo.java` may help in understanding how the two searches work. 

- [dfs maze](https://www.youtube.com/watch?v=95igA_fWQtc)
- [bfs maze](https://www.youtube.com/watch?v=dffMgIjfczI)

### Overview: What to Do

Here's a high-level view of the assignment. This is enough information to know what to do, but not necessarily how to do it. For details, you can refer to [the details document](docs/details.md).

You'll create three new implementations of the `IPercolate` interface that you'll test, time, and answer questions about in analyzing tradeoffs. 
1. Create class `PercolationDFS` that extends `PercolationDefault`. Implement one constructor, it simply calls `super` appropriately. You will implement one method, `@Override` the method `search` to use a Stack rather than recursion.
2. Create class `PercolationBFS` that extends `PercolationDefault`. Implement one constructor, it simply calls `super` appropriately. You will implement one method, `@Override` the method `search` to use BFS instead of DFS. 
3. Create class `PercolationUF` that implements the `IPercolate` interface. Implement a constructor and every method from the interface. Instance variables and methods are described in the [the details document](docs/details.md)

For all three classes, test the implementation using the JUnit test class `TestPercolate` by changing which of the three implementations is used. You **must also test using the `InteractivePercolationVisualizer` simulating the percolation using your own click-tests.**  Do not depend only on the JUnit tests.

For the analysis you will run `PercolationStats` and answer the analysis questions. 


## Part 1 `PercolationDFS`
Your new class `PercolationDFSFast` will extend `PercolationDefault` and thus inherit state and methods from that class. That class has protected state, so can be extended.

1. You'll need to create a constructor with an int/size parameter whose only code is a call to `super(..)` to initialize the state in the parent class. 
2. You'll need to `@Override` `search` to use an explicit Stack for DFS rather than recursion.

See [the details document](docs/details.md) for details.

## Part 2: `PercolationBFS`
This class extends the `PercolationDFSDefault` class just as `PercolationBFS` does. 

1. You'll need to create a `PercolationBFS` constructor with an int/size parameter that calls super to initialize the state in the parent class.
2.  You'll need to `@Override` `search` to use a `Queue` just as you used a `Stack` for `PercolationDFS`.


## Part 3: `PercolationUF`
This class `implements` the `IPercolate` interface and will use an `IUnionFind` object to keep track of open and full cells. See the video at the beginning of the assignment for general ideas. You can also look at the `MazeDemo` class in the Percolate assignment to see how union-find can be used. 

See [the details document](docs/details.md) for details.


## Benchmarking and Analysis

Copy/Paste the runs described below and answer the questions that are included below. You will answer these questions in a separate PDF document you submit to gradescope.

You're given `PercolationStats.java` which performs benchmarks for an `IPercolate` object using grid sizes of 100, 200, 400, 800, 1600, and 3200. If you don't change the value of the public `RANDOM_SEED` variable you should _**see the same mean values**_ of the Percolation threshold shown below. Your timing values may vary, but for all implementations using 10 trials you should see the same mean and standard deviation values. A sample run is provided below from running `PercolationDFSFast` on ola's laptop computer. **You will eventually reach a StackOverflowError because you’re running an infinite benchmark on a computer with finite memory. This is normal!**

<details>
<summary>Expand for example simulation data</summary>

```
simulation data for 10 trials
grid	mean	stddev	total time
100	0.593	0.019	0.026
200	0.596	0.006	0.033
400	0.592	0.006	0.174
800	0.592	0.003	1.475

Exception in thread "main" java.lang.StackOverflowError
    at PercolationDFS.dfs(PercolationDFS.java:109)
    at PercolationDFS.dfs(PercolationDFS.java:109)
    at PercolationDFS.dfs(PercolationDFS.java:109)
    at PercolationDFS.dfs(PercolationDFS.java:110)
    at PercolationDFS.dfs(PercolationDFS.java:109)
    [... rest truncated]
```
</details>

Copy/paste the results for running `PercolationStats.java` for 10 trials on each `IPercolate` object (`PercolationDFS, PercolationBFS, PercolationUF`) in your analysis document. 

So first copy/paste data for the grid sizes shown above for all three `IPercolate` classes you implement for this project. Then answer these questions using data from `PercolationUF` with `QuickUWPC`.
1. How does doubling the grid size affect running time (keeping # trials fixed)? Specifically, by roughly what factor does the runtime increase each time we double the grid size?
2. How does doubling the number of trials, e.g., to 20 (and more) affect running time? Specifically, by roughly what factor does the runtime increase each time we double the number of trials?
3. Based on your observations of the timings with 10 trials, estimate the largest grid size you can run completely within at most 24 hours with 10 trials. Explain your reasoning.
4. Read this brief opinion article: [Cho and Cain, _Human-centered redistricting automation in the age of AI+, Science, September 2020](https://courses.cs.duke.edu/compsci201/fall21/netid/science-abd1879.pdf) about using statistical and computational techiques similar to those used in this assignment for socio-technical problems such as congressional districting. Write a paragraph about your thoughts after reading the article regarding the relationship between computational techniques and societal applications.

**After completing the analysis questions you should submit your answers as a PDF to the P6-analysis project on Gradescope.**

## Submitting, Reflect, and Grading

Push your code to Git. Do this often. Once you have run and tested your completed program locally:

1. Submit your code on gradescope to the autograder.
2. Submit a PDF to Gradescope in the separate Analysis assignment. Be sure to mark pages for the questions as explained in the [gradescope documentation here](https://help.gradescope.com/article/ccbpppziu9-student-submit-work#submitting_a_pdf).


For this project, the grading will be:

| Points | Grading Criteria |
| --- | --- |
| 6 | PercolationDFS |
| 6 | PercolationBFS |
| 6 | PercolationUF |
| 8 | Analysis  |

# Autograder API Checker
We use an API checker to check that you implement the appropriate instance variables and methods for each method. Attached below is a sample output response from a head UTA’s test submission. 

![Sample API Checker from Head UTA's Computer](./p6-figures/P6-API.png)

In order to resolve this issue, make sure that if you’ve added any instance variables or helper methods not specified in the writeup that you change them to private. 

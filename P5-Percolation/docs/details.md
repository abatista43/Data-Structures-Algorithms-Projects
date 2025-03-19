# Details for P5 Percolation

## Starter Code and Using Git
**_You should have installed all software (Java, Git, VS Code) before completing this project._** You can find the [directions for installation here](https://coursework.cs.duke.edu/201fall23/resources-201/-/blob/main/installingSoftware.md) (including workarounds for submitting without Git if needed).

We'll be using Git and the installation of GitLab at [coursework.cs.duke.edu](https://coursework.cs.duke.edu). All code for classwork will be kept here. Git is software used for version control, and GitLab is an online repository to store code in the cloud using Git.

**[This document details the workflow](https://coursework.cs.duke.edu/201fall23/resources-201/-/blob/main/projectWorkflow.md) for downloading the starter code for the project, updating your code on coursework using Git, and ultimately submitting to Gradescope for autograding.** We recommend that you read and follow the directions carefully this first time working on a project! While coding, we recommend that you periodically (perhaps when completing a method or small section) push your changes as explained in below.

## Details on Git with a Partner

You may find it helpful to begin by reading the Working Together section of the [Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/tree/master/git) from the Duke Colab. For more, see the [Git tutoraial by Gitlab](https://docs.gitlab.com/ee/tutorials/make_your_first_git_commit.html) including the link to an [extensive video tutorial](https://www.youtube.com/watch?v=4lxvVj7wlZw) if you prefer that.

One person should fork the starter code and then add their partner as a collaborator on the project. Choose Settings>Members>Invite Members. Then use the autocomplete feature to invite your partner to the project as a *maintainer*. Both of you can now clone and push to this project. See the [gitlab documentation here](https://docs.gitlab.com/ee/user/project/members/).

Now you should be ready to clone the code to your local machines.

1. Both students should clone the same repository and import it into VS Code just like previous projects.  
2. After both students have cloned and imported, one person should make a change (you could just write a comment in the code, for example). Commit and push this change. 
3. The other partner will then issue a git pull request. Simply use the command-line (in the same project directory where you cloned the starter code for the project) and type:
```bash
git pull
```
4. If the other partner now opens the project in VS Code again, they should see the modified code with the edit created by the first partner. 
5. You can continue this workflow: Whenever one person finishes work on the project, they commit and push. Whenever anyone starts work on the project, they begin by downloading the current version from the shared online repository using a git pull command.

This process works as long as only one person is editing at a time, and **you always pull before editing** and remember to **commit/push when finished**. If you forget to pull before editing your local code, you might end up working from an old version of the code different than what is in the shared online gitlab repository. If that happens, you may experience an error when you attempt to push your code back to the shared online repository. 

There are many ways to resolve these conflicts, essentially you just need to pick which of the different versions of the code you want to go with. See the [working together Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/blob/master/git/working_together.md) and the [branching and merging Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/blob/master/git/branching_merging.md) from the Duke Colab for more information. You can also refer to our [Git troubleshooting document](https://coursework.cs.duke.edu/201-public-documentation/resources-201/-/blob/main/troubleshooting.md#git-faq). 

If you run into a merge conflict, one thing that might be confusing is that the editor that opens where you can resolve them may, by default, be [VIM](https://www.vim.org), which can be very unintuitive if you have not used it before. You can either look up the basics there, or if you prefer you can set a different text editor as the default that git uses for editing commit messages, merge conflicts, etc. For example, to make Visual Studio Code the default editor (this was an optional step we suggested during installation, so you may have already done this):
1. Open the command palette on visual studio code (`shift` + `command` + `p` on Mac, or `shift` + `ctrl` + `p` on Windows).
2. Write `Shell Command: Install 'Code' command in path` - it should autocomplete to this, press enter. It may ask for your permission.
3. In a terminal, enter the command `git config --global core.editor "code --wait"`. Now VS Code should be the default editor for git.
4. You can confirm the change by trying `git config --global -e` in a terminal. This should open a VS Code window showing your `git config` file (you don't need to edit, this is just to confirm it worked).

The `--wait` command means that whenever `git` opens something in VS Code for you to edit, it will wait until you close that window/tab before proceeding.

**Important note: The `search` method will mark an open cell as full and then proceed to mark the open cells connected to that cell as full. As a result, you should terminate the method early (return ;) if the cell is full or blocked before creating the Queue.**

For this class, you'll @Override the `search` method to use
an explicit stack as shown in the [IterativeDFSBlobModel class](https://coursework.cs.duke.edu/201fall23/blobs/-/blob/main/IterativeDFSBlobModel.java) with a stack of `int[]` objects just as in that code.

# Percolation Classes You Will Write/Test/Analyze


## PercolationDFS

You'll override the `search` method inherited from `PercolationDefault` to use a `Stack` rather than recursion in implementing a DFS approach. Use the ideas from the [BlobCounting code](https://coursework.cs.duke.edu/201fall23/blobs) we've seen in class, especially the [IterativeDFSBlobModel](https://coursework.cs.duke.edu/201fall23/blobs/-/blob/main/IterativeDFSBlobModel.java). The `search` method you write **will be called from the inherited `updateOnOpen` from `PercolationDFSDefault.`** 

As an invariant, consider that **every cell on the stack should be `FULL`,** and you'll likely need to mark each `myGrid` cell as `FULL` before
pushing onto the stack.

See below on testing using the `InteractivePercolationVisualizer` and the JUnit testing class `TestPercolation` to test.

**Important note: The `search` method will mark an open cell as `FULL` and then proceed to mark the open cells connected to that cell as full. As a result, you should terminate the method early (return ;) if the cell is `FULL` or blocked before creating the Stack.**

## PercolationBFS

You'll override the `search` method inherited from `PercolationDefault` to use a `Queue` and a breadth-first-search (BFS) approach. Use the ideas from the [BlobCounting code](https://coursework.cs.duke.edu/201fall23/blobs) we've seen in class, especially the [BFSBlobModel](https://coursework.cs.duke.edu/201fall23/blobs/-/blob/main/BFSBlobModel.java). The `search` method you write **will be called from the inherited `updateOnOpen` from `PercolationDFSDefault.`** 

See below on testing using the `InteractivePercolationVisualizer` and the JUnit testing class `TestPercolation` to test.

**Important note: The `search` method will mark an open cell as `FULL` and then proceed to mark the open cells connected to that cell as full. As a result, you should terminate the method early (return ;) if the cell is `FULL` or blocked before creating the Queue.**

As an invariant, consider that **every cell on the Queue should be `FULL`,** and you'll likely need to mark each `myGrid` cell as `FULL` before
adding to the queue.

## Testing implementations

For all three classes you write that extend `PercolationDefault` or implement the `IPercolate` interface.

3. Use  `InteractivePercolationVisualizer`,but you will need to change the line in `main` between lines 22-25 to create a `perc` object of type `PercolationDFS`  and then run this simulator to make sure it works. Similarly for `PercolationBFS` and `PercolationUF`. 
4. Modify and run `TestPercolation`. You will need to change the method `getPercolator` to return a `PercolationDFS` object.


## PercolationUF

Each of the NxN cells is mapped to a number, and these numbers represent a cell's set for the disjoint-set/union-find algorithm. Initially every cell is a single number in its own set. You'll need two additional numbers, `VTOP` and `VBOTTOM`, for a total of N<sup>2</sup>+2 values. For information on these two extra numbers/variables, see [From DFS to Union-Find: two approaches compared/contrasted](https://youtu.be/a_YMnW4hzmI).

### Instance variables for PercolationUF
1. A two-dimensional array of boolean values, `myGrid`, that represents whether a cell is open. Initially `myGrid[r][c]` should be `false` which is the default value when you create the grid. Each time a cell (r,c) is open, `myGrid[r][c]` will be set to true.
2. An integer `myOpenCount` which is the number of open cells, i.e., the number of true values in `myGrid`.
3. An `IUnionFind` object `myFinder` to store/reference the `IUnionFind` object passed to the constructor (which should be a `QuickUWPC` object in this assignment, you will see this later when you test it).
4. Two final values for `VTOP` and `VBOTTOM`, set to `size * size` and `size * size + 1`, for example, in the constructor. All instance variables should be private as shown.

<img src="p6-figures/P6-PercolationUF.png" width="400">

### Constructor for `PercolationUF`
Create a constructor `PercolationUF(IUnionFind finder, int size)` that will construct and initialize the NxN grid stored in the instance variable `myGrid` (where N is given by the `size` parameter). The constructor should initialize the `IUnionFind` object **of size NxN + 2** by calling `finder.initialize` appropriately and then storing this object in the appropriate instance variable which is `myFinder`.  Also initialize the remaining instance variables. This will be a total of five or six lines to initialize each of the five instance variables.

### Methods for `PercolationUF`
You must `@Override` each method from the `IPercolate` interface. As with methods you can see in `PercolationDFS`, these methods you write with (row,col) parameters should throw exceptions when the (row,col) are not in bounds. You may find it convenient to create your own **private or protected** helper method `inBounds` to make these checks as in `PercolationDFS`.

The `IUnionFind` classes use a single integer value to represent each set used in all methods like `find, union, connected`, and so on. You'll need to convert a (row,col) pair to a single integer. The simplest way to do this is to use `row*myGrid.length + col` -- you can see that this maps (0,0) to 0, and in a 10x10 grid will map (0,9) to 9, and (1,9) to 19.  

Be sure to deal with `VTOP` and `VBOTTOM` as well.

1. Method `isOpen` throws an `IndexOutOfBoundsException` (see `PercolationDFS` for example) when needed and otherwise simply returns the appropriate `myGrid` value.
2. Method `isFull` throws an `IndexOutOfBoundsException` (see `PercolationDFS` for example) when needed and otherwise checks if the (row,col) cell is connected to `VTOP` (convert (row,col) to an integer to call `myFinder.connected`).
3. Method `percolates` checks to see if `VTOP` is connected to `VBOTTOM` using `myFinder`.
4. Method `numberOfOpenSites` simply returns the value of the appropriate instance variable.
5. Method `open` throws an Exception when needed, checks to be sure the cell is not already open, and then marks the cell as open and connects with open neighbors as described below.

When a cell is marked as open, you'll write code to check each of the cell's four neighbors. If any of these cells is `OPEN`, the newly marked cell and the open neighbor should be connected by a call to `myFinder.union`. If the newly marked cell is in the top row it should be connected to `VTOP` by a call to `myFinder.union`. If the newly marked cell is in the bottom row it should be connected to `VBOTTOM` by a call to `myFinder.union`.

### Testing `PercolationUF`

See above for testing all implementations using `InteractivePercolationVisualizer` and `TestPercolation'




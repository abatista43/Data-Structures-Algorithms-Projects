## Starter Code and Using Git
**_You should have installed all software (Java, Git, VS Code) before completing this project._** You can find the [directions for installation here](https://coursework.cs.duke.edu/201fall23/resources-201/-/blob/main/installingSoftware.md) (including workarounds for submitting without Git if needed).

We'll be using Git and the installation of GitLab at [coursework.cs.duke.edu](https://coursework.cs.duke.edu). All code for classwork will be kept here. Git is software used for version control, and GitLab is an online repository to store code in the cloud using Git.

**[This document details the workflow](https://coursework.cs.duke.edu/201fall23/resources-201/-/blob/main/projectWorkflow.md) for downloading the starter code for the project, updating your code on coursework using Git, and ultimately submitting to Gradescope for autograding.** We recommend that you read and follow the directions carefully this first time working on a project! While coding, we recommend that you periodically (perhaps when completing a method or small section) push your changes as explained in below.


## Coding in Project P2: Markov

When you fork and clone the project, **make sure you open the correct project folder in VS Code** following the [directions shown here](https://coursework.cs.duke.edu/201fall23/resources-201/-/blob/main/projectWorkflow.md#step-3-open-the-project-in-vs-code).

## What is a WordGram?

The number of strings contained in a `WordGram` is sometimes called the *order* of the WordGram, and we sometimes call the `WordGram` an *order-k* WordGram, or a *k-gram* -- the term used in the Markov program you'll implement.  You can see some examples of order-3 `WordGram` objects below.

| | | |
| --- | --- | --- |
| "cat" | "sleeping" | "nearby" |
| | | |

and 
| | | |
| --- | --- | --- |
| "chocolate" | "doughnuts" | "explode" |
| | | |


### What is a Markov Model?

Markov models are random models with the Markov property. In our case, we want to create a Markov model for generating random text that looks similar to a training text. We will generate one random word at a time, and the Markov property in our context means that the probabilities for that next word will be based on the previous words -- more precisely on 3 previous words in an order-3 Markov Model and the `k`previous words in an order-`k` Markov model.

An order-k Markov model uses order-k `WordGram`s to predict text: we sometimes call these *k-grams* where *k* refers to the order. To begin, we select a random k-gram from the *training text* (the data we use to create our model; we want to generate random text similar to the training text). Then, we look for instances of that k-gram in the training text in order to calculate the probabilities corresponding to words that might follow. We then generate a new word according to these probabilities, after which we repeat the process using the last k-1 words from the previous k-gram and the newly generated word. Continue in that fashion to create the desired number of random words. 

Here is a concrete example. Suppose we are using an order 2 Markov model with the following training text (located in `testfile.txt`):

```
this is a test
it is only a test
do you think it is a test
this test it is ok
it is short but it is ok to be short
```

We begin with a random k-gram, suppose we get `[it, is]`. This appears 5 times in total, and is followed by `only`, `a`, `ok`, `short`, and again by `ok` each of those five times respectively. So the probability (in the training text) that `it is` is followed by `ok` is 2/5 or 40%, and for the other words is 1/5 or 20%. To generate a random word following the 2-gram `[it, is]`, we would therefore choose `ok` with 2/5 probability, or `only`, `a`, or `short` with 1/5 probability each.

Rather than calculating these probabilities explicitly, your code will use them implicitly. In particular, the `getFollows` method will return a `List` of *all* of the words that follow after a given k-gram in the training text (including duplicates), and then you will choose one of these words uniformly at random. Words that more commonly follow will be selected with higher probability by virtue of being duplicated in the `List`.

Suppose we choose `ok` as the next random word. Then the random text generated so far is `it is ok`, and the current `WordGram` of order 2 we are using would be updated to `[is, ok]`. We then again find the following words in the training text, and so on and so forth, until we have generated the desired number of random words.


Of course, for a very small training text these probabilities may not be very meaningful, but random generative models like this can be much more powerful when supplied with large quantities of training data, in this case meaning very large training texts.

## The MarkovDriver class

- Some static variables used in the main method are defined at the top of class, namely:
  - `TEXT_SIZE` is the number of words to be randomly generated.
  - `RANDOM_SEED` is the random seed used to initialize the random number generator. You should always get the same random text given a particular random seed and training text.
  - `MODEL_ORDER` is the order of `WordGram`s that will be used.
  - `PRINT_MODE` can be set to true or false based on whether you want the random text generated to be printed.
- The `filename` defined at the beginning of the main method determines the file that will be used for the training text. By default it is set to `data/alice.txt`, meaning the text of *Alice in Wonderland* is being used. Note that data files are located inside the data folder.
- A `MarkovInterface` object named `generator` is created. By default, it uses `BaseMarkov` as the implementing class, a complete implementation of which is provided in the starter code. Later on, when you have developed `HashMarkov`, you can comment out the line using `BaseMarkov` and uncomment the line using `HashMarkov` to change which implementation you use.
- The `generator` then sets the specified random seed. You should get the same result on multiple runs with the same random seed. Feel free to change the seed for fun while developing and running, but *the random seed should be set to 1234 as in the default when submitting for grading*.
- The `generator` is timed in how long it takes to run two methods: first `setTraining()` and then `getRandomText()`.
- Finally, values are printed: The random text itself if `PRINT_MODE` is set to true and the time it took to train (that is, for `setTraining()` to run) the Markov model and to generate random text using the model (that is, for `getRandomText` to run). 

## Developing the WordGram class: details

The constructors and methods of the `WordGram` class are detailed below. 

### Constructors 

You'll construct a `WordGram` object by passing as constructor arguments: an array, a starting index, and the size (or order) of the `WordGram.` You'll **store the strings in an array instance variable** by copying them from the array passed to the constructor.

There are three instance variables in `WordGram`:
```
private String[] myWords;
private String myToString;
private int myHash;
```

The constructor for WordGram `public WordGram(String[] source, int start, int size)`
should store `size` strings from the array `source`, starting at index `start` (of `source`) into the private `String` array instance variable `myWords` of the `WordGram` class. The array `myWords` should contain exactly `size` strings. 

For example, suppose parameter `words` is the array below, with "this" at index 0.

| | | | | | | |
| --- | --- | --- | --- | --- | --- | --- |
| "this" | "is" | "a" | "test" |"of" |"the" |"code" |
| | | | | | |

The call `new WordGram(words,3,4)` should create this array `myWords` since the starting index is the second parameter, 3, and the size is the third parameter, 4.

| | | | |
| --- | --- | --- | --- |
| "test" | "of" | "the" | "code"|
| | | | |

You can initialize the instance variables `myToString` and `myHash` in the constructor stub to whatever default values you choose; these will change when you implement the methods `.toString()` and `.hashCode()`, respectively.
 

### The length() method 

The `length()` method should return the order of the `WordGram`, that is, the length of `myWords`. 

### The .equals method

The `equals()` method should return `true` when the parameter passed is a `WordGram` object with **the same strings in the same order** as this object. 

The [Java API specification of `.equals()`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#equals(java.lang.Object) takes an `Object` type as input. Thus, the first thing the `WordGram` `equals()` method should do is check if the parameter passed is really a `WordGram` using the `instanceof` operator, and if not return false. Otherwise, the parameter can be *cast* as a `WordGram`. This is done for you in the starter code and you do not need to change it.

Then what you need to do is compare the strings in the array `myWords` of `other` and `this` (the object on which `equals()` is called). Note that `WordGram` objects of different lengths cannot be equal, and your code should check this.

### The hashCode() method

The `hashCode()` method should return an `int` value based on all the strings in instance variable `myWords`. See the [Java API documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#hashCode()) for the design constraints to which a `hashCode()` method should conform. 

Note that the Java String class already has a good [`.hashCode()` method](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#hashCode()) we can leverage. Use the `.hashCode()` of the String returned by `this.toString()` to implement this method.

Since `WordGram` objects are immutable (do not change after creation), you do not need to recompute the hash value each time `.hashCode()` is called. Instead, you can compute it the first time `.hashCode()` is called (which you can check against whatever default value you might set in the constructor), and store the result in the `myHash` instance variable. On subsequent calls, simply return `myHash`.

### The shiftAdd() method

If this `WordGram` has k entries then the `shiftAdd()` method should create and return a _**new**_ `WordGram` object, also with k entries, whose *first* k-1 entries are the same as the *last* k-1 entries of this `WordGram`, and whose last entry is the parameter `last`. Recall that `WordGram` objects are immutable and cannot be modified once created - **this method must create a new WordGram object** and copy values correctly to return in the new `WordGram` created in the `shiftAdd` method.

For example, if `WordGram w` is 
| | | |
| --- | --- | --- |
| "apple" | "pear" | "cherry" |
| | | | 

then the method call `w.shiftAdd("lemon")` should return a new `WordGram` containing {"pear", "cherry", "lemon"} in that order. Note that this new `WordGram` will not equal `w`.

Note: To implement `shiftAdd()` you'll need to create a new `WordGram` object. The code you write in `shiftAdd` will be able to assign values to the private instance variables of that new object directly since the `shiftAdd()` method is in the `WordGram` class.

### The toString() method

The `toString()` method should return a printable `String` representing all the strings stored in the `WordGram` instance variable `myWords`, each separated by a single blank space (that is, `" "`). You may find the String `join` method useful, see [the documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#join(java.lang.CharSequence,java.lang.CharSequence...)).

You do not need to recompute this `String` each time `toString()` is called since the `WordGram` class is immutable-- instead, store the String being returned in instance variable `myToString`. On subsequent calls your code should simply return the value stored in `myToString` (again using the immutability of `WordGram`s to ensure this value will not change). To determine whether a given call to `toString()` is the first, you can compare to the default constructor value of `myToString`.


### The HashMarkov class

### instance variables

You will need the same instance variables as in `BaseMarkov` for storing the words of the training text, the random number generator, and the order of the model. In addition, you will need a `HashMap` instance variable that maps from `WordGram`s (the keys) to `List<String>` (the values). 

### Constructor

You must have at least one constructor that takes as input the order of `WordGram`s used in the model. It should initialize the instance variables, similar to the code you'll find in `BaseMarkov`.

### The setTraining() method

Similar to `BaseMarkov`, your `setTraining()` method should store the words of the training text in an Array of Strings. The easiest way is to use the method call `text.split("\\s+")` as seen in `BaseMarkov` - the regular expression `\\s+` is used to split on all whitespace, including spaces and newline characters.

In addition, you *must clear the `HashMap` instance variable* (for example, if the name of the variable is `myMap`, you can do this by calling `myMap.clear();`). This ensures that the map does not contain stale data if `setTraining()` is called multiple times on different training texts.

Finally, you should loop through the words in the training text *exactly once*. For each `WordGram` of size `k`, where `k` is the order of the `HashMarkov` model,  you'll use the `WordGram` as a key, and add the String that follow it as an entry in the`ArrayList` object that's the value associated with that `key`. You'll create a `WordGram` object from the first `myOrder` words, then loop over all the strings that are after those first strings. You'll find code very similar to this in the `BaseMarkov` method `getFollows` since some of that logic now moves in the the `HashMarkov` method `setTraining`. When your code looks up a `WordGram` model as a key in the `HashMap` instance variable it will create a new `ArrayList` as the value associated with that key the first time the `WordGram` occurs. Then your code wil call `myMap.get(wg).add(str)` where `str` is the string that occurs after the `WordGram`. Note that the values in the `HashMap` have type `List<String>`, but you'll create new `ArrayList` objects (since `List` is an interface).

As you loop over all strings, you'll change the `WordGram` in the body of the loop by calling `shiftAdd` which creates a new `WordGram` with the next-to-be-looped-over string after it. Think about that!


### The getFollows() method

Just like in `BaseMarkov`, the `getFollows` method takes a `WordGram` object `wgram` as a parameter and should return a `List` of all the words (represented as `String`s) that follow the `wgram` in the training text. However, the `HashMarkov` implementation *must* be more efficient, as it should *not* loop over the training text, but should instead simply lookup the `List` in the `HashMap` instance variable intialized during `setTraining()`, or return an empty `List` if the `wgram` is not a key in the map. This means that the `getFollows` method will be O(1) instead of O(N) where N is the size of the training text.

### The getRandomText() method

This method should indirectly use the `HashMap` instance variable set during `setTraining()` since the code in `getRandomText` will call the `getFollows()` method to generate `length` words of random text one at a time according to the Markov model described in the intro section [What is a Markov Model?](#what-is-a-markov-model). 

Note that, in order to adhere to the specification that `HashMarkov` should generate the same random text as `BaseMarkov` given the same random seed, **you will need to use the random number generator in the same way as `BaseMarkov`.

*The easiest way to ensure this works? Simply copy the code from `BaseMarkov`, including the helper method `getNextWord`. Since that code depends on `getFollows`, which is different in `HashMarkov`, it should work as-is.

### The  getOrder() and setSeed() methods

`getOrder()` is just a getter method that should return the order of the Markov model, stored in an instance variable. `setSeed()` should simply call the `setSeed()` method of the random number generator instance variable and pass the corresponding random seed.
*You can use the same implementations you find in `BaseMarkov`.*

import java.util.*;

public class HashMarkov implements MarkovInterface {
    protected String[] myWords;		// Training text split into array of words 
	protected Random myRandom;		// Random number generator
	protected int myOrder;			// Length of WordGrams used
	protected static String END_OF_TEXT = "*** ERROR ***";
    HashMap<WordGram, List<String>> myMap = new HashMap<>();

    public HashMarkov() {
		this(3);
	}

    public HashMarkov(int order){
		myOrder = order;
		myRandom = new Random();
	}

    @Override
    public void setTraining(String text) {
        myWords = text.split("\\s+");
        myMap.clear();
        WordGram currentWG = new WordGram(myWords, 0, myOrder);
        for (int k = myOrder; k < myWords.length; k++) {
            myMap.putIfAbsent(currentWG, new ArrayList<String>());
            myMap.get(currentWG).add(myWords[k]);
            currentWG = currentWG.shiftAdd(myWords[k]);
        }
        // throw new UnsupportedOperationException("Unimplemented method 'setTraining'");
    }

    @Override
    public List<String> getFollows(WordGram wgram) {
        return myMap.getOrDefault(wgram, new ArrayList<String>());
        // throw new UnsupportedOperationException("Unimplemented method 'getFollows'");
    }

    private String getNextWord(WordGram wgram) {
		List<String> follows = getFollows(wgram);
		if (follows.size() == 0) {
			return END_OF_TEXT;
		}
		else {
			int randomIndex = myRandom.nextInt(follows.size());
			return follows.get(randomIndex);
		}
	}

    @Override
    public String getRandomText(int length) {
        ArrayList<String> randomWords = new ArrayList<>(length);
		int index = myRandom.nextInt(myWords.length - myOrder + 1);
		WordGram current = new WordGram(myWords,index,myOrder);
		randomWords.add(current.toString());

		for(int k=0; k < length-myOrder; k += 1) {
			String nextWord = getNextWord(current);
			if (nextWord.equals(END_OF_TEXT)) {
				break;
			}
			randomWords.add(nextWord);
			current = current.shiftAdd(nextWord);
		}
		return String.join(" ", randomWords);
        // throw new UnsupportedOperationException("Unimplemented method 'getRandomText'");
    }

    @Override
    public int getOrder() {
        return myOrder;
        // throw new UnsupportedOperationException("Unimplemented method 'getOrder'");
    }

    @Override
    public void setSeed(long seed) {
        myRandom.setSeed(seed);
        // throw new UnsupportedOperationException("Unimplemented method 'setSeed'");
    }
    
}

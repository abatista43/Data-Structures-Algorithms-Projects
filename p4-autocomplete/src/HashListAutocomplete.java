import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}

		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        String pre = prefix;
        if (prefix.length() > MAX_PREFIX) {
            pre = prefix.substring(0, MAX_PREFIX);
        }
        if (myMap.containsKey(pre)) {
            List<Term> all = myMap.get(prefix);
            if (all != null) return all.subList(0, Math.min(k, all.size()));
        }
        return new ArrayList<Term>();
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<String, List<Term>>();
        myMap.put("", new ArrayList<Term>());
        mySize = 0;
        for (int k = 0; k < terms.length; k++) {
            Term t = new Term(terms[k], weights[k]);
            myMap.get("").add(t);
            mySize += BYTES_PER_CHAR * terms[k].length() + BYTES_PER_DOUBLE;
            for (int i = 1; i < MAX_PREFIX + 1; i++) {
                if (i <= terms[k].length()) {
                    if (! myMap.containsKey(terms[k].substring(0, i))) {
                        myMap.put(terms[k].substring(0, i), new ArrayList<Term>());
                        mySize += BYTES_PER_CHAR * i;
                    }
                    myMap.get(terms[k].substring(0, i)).add(t);
                    mySize += (BYTES_PER_CHAR * terms[k].length()) + BYTES_PER_DOUBLE;
                }
            }
        }
        for (String s : myMap.keySet()) {
            Collections.sort(myMap.get(s), Comparator.comparing(Term::getWeight).reversed());
        }
    }

    @Override
    public int sizeInBytes() {
        return mySize;
    }  
}


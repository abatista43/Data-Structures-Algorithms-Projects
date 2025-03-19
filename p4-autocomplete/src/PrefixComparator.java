import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class    PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }


    @Override
    /**
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words. Comparisons
     * should be made based on the first myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */
    public int compare(Term v, Term w) {
        int r = myPrefixSize;
        String v2 = v.getWord();
        String w2 = w.getWord(); 
        for (int i = 0; i < r; i++) {
            if (i >= v2.length() && i >= w2.length()) {
                return 0;
            }
            if (i >= v2.length()) return -1;
            if (i >= w2.length()) return 1;
            if (v2.charAt(i) > w2.charAt(i)) {
                return 1;
            }
            if (v2.charAt(i) < w2.charAt(i)) {
                return -1;
            }
        }
        return 0;
    }
}

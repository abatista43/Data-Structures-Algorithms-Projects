public class LinkStrand implements IDnaStrand {

    public LinkStrand(){
        this("");
    }

    public LinkStrand(String s){
        initialize(s);
    }

    private class Node {
        String info;
        Node next;
        Node(String s){
            info = s;
        }
    }

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    @Override 
    public void initialize(String s) {
        Node myNode = new Node(s);
        myFirst = myNode;
        myLast = myNode;
        mySize = s.length();
        myAppends = 0;
        myIndex = 0;
        myCurrent = myNode;
        myLocalIndex = 0;
    }

    @Override
	public IDnaStrand getInstance(String source) {

		return new LinkStrand(source);
	}

    @Override
    public long size() {
        return mySize;
    }

    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna);
        myLast = myLast.next;
        mySize += dna.length();		
        myAppends++;
		return this;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node first = myFirst;
        while (first != null) {
            sb.append(first.info);
            first = first.next;
        }
        return sb.toString();
    }

    @Override
    public IDnaStrand reverse() {
        StringBuilder sbFirst = new StringBuilder(myFirst.info);
        sbFirst.reverse();
        LinkStrand rev = new LinkStrand(sbFirst.toString());
        Node list = myFirst.next;
        while (list != null) {
            StringBuilder sb = new StringBuilder(list.info);
            sb.reverse();
            Node temp = rev.myFirst;
            rev.myFirst = new Node(sb.toString());
            rev.myFirst.next = temp;
            list = list.next;
        }
        rev.mySize = mySize;
        return rev;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= mySize) {
            throw new IndexOutOfBoundsException();
        }
        int count = myIndex;
	    int dex = myLocalIndex;
	    Node list = myCurrent;
        if (index <= myIndex) {
            count = 0;
            dex = 0;
            list = myFirst;
        }
	    while (count != index) {
		    count++;
		    dex++;
		    if (dex >= list.info.length()) {
			    dex = 0;
			    list = list.next;
		    }
	    }
        myIndex = index;
        myLocalIndex = dex;
        myCurrent = list;
        return list.info.charAt(dex);
    }
}




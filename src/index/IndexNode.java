package index;

import java.util.LinkedList;
import java.util.List;

public class IndexNode  {

	String word;
	int occurences;
	List<Integer> list; // list of all the lines the word appears on
	
	IndexNode left;
	IndexNode right;
	

	public IndexNode(String word, int linenumber) {
		occurences = 1; // initializes occurences
		list = new LinkedList<>(); // initializes list
		list.add(linenumber); // adds line to list of line numbers
		this.word = word; // sets the word

	}

	public static void main(String[] args) {
//		IndexNode node = new IndexNode("hello", 20);
//		System.out.println(node);
	}

	public String toString(){
		return word + ", " + " occurs: " + occurences + " times(s) on lines: " + list + "\n";
	}
	
	
	
}

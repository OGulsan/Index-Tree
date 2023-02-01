package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class IndexTree {

	private IndexNode root;

	public IndexTree() {}

	public void add(String word, int lineNumber){
		this.root = add(root, word, lineNumber);
		
	}
	private IndexNode add(IndexNode root, String word, int lineNumber){
		IndexNode node = new IndexNode(word, lineNumber); // creates new node to be added

		if(root == null) { // if root is null, make the root the new node
			return node;

		} else if (root.word.compareTo(word)==0) { // word already exists, add the line number to the node
			root.list.add(lineNumber);
			root.occurences++;
		}
		else if (word.compareTo(root.word) < 0) {
			root.left = add(root.left, word, lineNumber);
		}
		else {
			root.right = add(root.right, word, lineNumber);
		}

		return root;
	}

	// returns true if the word is in the index
	public boolean contains(String word){
		return contains(root, word);
	}

	public boolean contains(IndexNode root, String word) { // performs a simple search of the BST
		if(root == null){
			return false;
		}
		if(root.word.compareTo(word) == 0) {
			return true;
		}
		else if (word.compareTo(root.word) < 0) {
			return contains(root.left, word);
		}
		else if(word.compareTo(root.word) > 0) {
			return contains(root.right, word);
		}

		return false;
	}
	

	public void delete(String word){
		// 3 cases when deleting a node from a BST:
		// target node is leaf - adjust it's parents ref to null
		// target node has one child - change childs grandparent ref to point to child instead of target node
		// target node has 2 children - replace target node with inorder predecessor to maintain BST property

		// first need to find node then determine which case I'm dealing with

		//search node and keep ref to it's parent
		this.root = delete(this.root, word);

	}

	private IndexNode delete(IndexNode root, String word){
		if(root == null){ // empty tree
			return root;
		}

		int cmpResult = word.compareTo(root.word); // store result of comparison so I dont have to keep calling .compareTo()

		if(cmpResult < 0) { // if word is less than root, call delete on left subtree
			root.left = delete(root.left, word);
			return root;
		}
		else if(cmpResult > 0) { // if word is greater than root, call delete on right subtree
			root.right = delete(root.right, word);
			return root;
		}
		else { // root contains word to be deleted, check which of the three cases this node is apart of
			if(root.left == null){ // checks if no left child
				return root.right;
			}
			else if(root.right == null){ // checks if no right child
				return root.left;
			}
			else {
				if(root.left.right == null){  
					root.word = root.left.word;
					root.left = root.left.left;
					return root;
				}
				else {
					root.word = findLargestChild(root.left).word; // has 2 children need to replace w/ inorder predecessor
							return root;
				}
			}
		}

	}

	public IndexNode findLargestChild(IndexNode root){ // finds supplied node's inorder predecessor 
		if(root.right.right == null){ // if right most node has no children, have reached largest node (right most)
			IndexNode returnValue = new IndexNode(root.right.word, root.right.list.get(0)); // store this node to be returned
			root.right = root.right.left;
			return returnValue;
		}
		else {
			return findLargestChild(root.right); // not at largest child, move down one level and recursively call again
		}

	}

	public void printIndex(){ // inorder traversal, Tl, Visit Root Node, Tr
		printIndex(this.root);

	}
	public void printIndex(IndexNode root){ // inorder traversal, Tl, Visit Root Node, Tr
		if(root == null)
			return;

		printIndex(root.left); // traverse left subtree

		System.out.println(root); // visit root node

		printIndex(root.right); // traverse right subtree

	}

	public static void main(String[] args) throws FileNotFoundException {
		// add all the words to the tree
		IndexTree index = new IndexTree();
		// attach Scanner to file
		Scanner scannerFile = new Scanner(new File("shakespeare.txt"));

		int lineNumber = 1;
		while(scannerFile.hasNextLine()){ // for each line in text
			Scanner scannerLine = new Scanner(scannerFile.nextLine()); // attach Scanner to line
			while(scannerLine.hasNext()) { // for each word in line
				// replace all non alphanumeric chars and converts word to lowercase to make input more readable
				index.add(scannerLine.next().replaceAll("[^a-zA-Z ]", "").toLowerCase(), lineNumber);
			}
			lineNumber++; // increment line number after each line

		}
		
		// print out the index
		index.printIndex();

		// test removing a word from the tree
		// uncomment below to see the difference when you remove the last two nodes/any nodes of your choice
//		index.delete("zounds");
//		index.delete("zwaggerd");
//		index.printIndex();

	}
}

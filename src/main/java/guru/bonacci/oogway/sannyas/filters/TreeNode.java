package guru.bonacci.oogway.sannyas.filters;

import java.util.HashMap;

/**
 * Borrowed code
 * @author thank you Lyen
 */
public class TreeNode {
	
	private HashMap<Character, TreeNode> node;

	//Indicate that this letter is the end of a profanity word
	private boolean isEnd; 

	public TreeNode() {
		isEnd = false;
		node = new HashMap<>();
	}

	public TreeNode(Character letter) {
		this();
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	/**
	 * @param letter child's letter
	 */
	public void addChild(Character letter) {
		TreeNode childNode = new TreeNode(letter);
		node.put(letter, childNode);
	}

	public TreeNode getChildByLetter(Character letter) {
		// Returns the value to which the specified key is mapped, or null if
		// this map contains no mapping for the key.
		return node.get(letter);
	}

	public boolean containsChild(Character letter) {
		return node.containsKey(letter);
	}
}

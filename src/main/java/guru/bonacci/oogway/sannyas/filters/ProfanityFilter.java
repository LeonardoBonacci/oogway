package guru.bonacci.oogway.sannyas.filters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class ProfanityFilter {

	/**
	 * Borrowed code
	 * @author thank you Lyen
	 */
	private TreeNode root = new TreeNode();

	private boolean isSuspicionFound;
	private boolean merdaFound;

	public static void main(String[] args) {
		ProfanityFilter badWordsFilterHash = new ProfanityFilter();
		badWordsFilterHash.start(args);
	}

	public void start(String[] args) {
		buildDictionaryTree(args[0]); // bad words list file name
		String userInput = "where are you going hey you";
		// show filtered bad words
		System.out.println(filterBadWords(userInput));

		userInput = "where are you going hey you bitch";
		// show filtered bad words
		System.out.println(filterBadWords(userInput));

	}

	private void init(int length) {
		isSuspicionFound = false;
		merdaFound = false;
	}


	/**
	 * Setup a tree for profanity filter
	 * 
	 * @param fileName
	 */
	public void buildDictionaryTree(String fileName) {
		String line;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
			while ((line = in.readLine()) != null) {
				// for each bad word
				addToTree(line, 0, root);
			}

		} catch (FileNotFoundException e) { // FileReader
			e.printStackTrace();
		} catch (IOException e) { // readLine
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param badWordLine
	 * @param characterIndex
	 *            : index of each letter in a bad word
	 * @param node
	 *            that iterates through the tree
	 */
	private void addToTree(String badWordLine, int characterIndex, TreeNode node) {
		if (characterIndex < badWordLine.length()) {
			Character c = badWordLine.charAt(characterIndex);
			if (!node.containsChild(c)) {
				node.addChild(c);
			}
			node = node.getChildByLetter(c);
			// check if this is the last letter
			if (characterIndex == (badWordLine.length() - 1)) {
				// mark this letter as the end of a bad word
				node.setEnd(true);
			} else {
				// add next letter
				addToTree(badWordLine, characterIndex + 1, node);
			}
		}
	}

	/**
	 * @param userInput
	 */
	public boolean filterBadWords(String userInput) {
		init(userInput.length());
		// for each character in a bad word
		for (int i = 0; i < userInput.length(); i++) {
			searchAlongTree(userInput, i, root);
		}
		return merdaFound;
	}

	private void searchAlongTree(String pUserInput, int characterIndex, TreeNode node) {
		if (characterIndex < pUserInput.length()) {
			// get the corresponding letter
			Character letter = pUserInput.charAt(characterIndex);
			if (node.containsChild(letter)) {
				// find a word whose first letter is equal to one of the bad
				// words' first letter
				if (isSuspicionFound == false) {
					isSuspicionFound = true;
				}
				// if this is the final letter of a bad word
				if (node.getChildByLetter(letter).isEnd()) {
					merdaFound = true;
				}
				node = node.getChildByLetter(letter);
				searchAlongTree(pUserInput, characterIndex + 1, node);
			} else {
				// initialize some parameters
				isSuspicionFound = false;
			}
		}
	}
}

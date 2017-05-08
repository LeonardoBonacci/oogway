package guru.bonacci.oogway.sannyas.filters.profanity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class ProfanityFilter implements Predicate<String> {

	/**
	 * Borrowed code
	 * @author thank you Lyen
	 */
	private TreeNode root = new TreeNode();

	private boolean isSuspicionFound;
	private boolean merdaFound;

	public ProfanityFilter() {
		buildDictionaryTree("badwords.txt");
	}

	@Override
	public boolean test(String input) {
		init();
		// for each character in a bad word
		for (int i = 0; i < input.length(); i++) {
			searchAlongTree(input, i, root);
		}
		return !merdaFound;
	}

	private void init() {
		isSuspicionFound = false;
		merdaFound = false;
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

	public void buildDictionaryTree(String fileName) {
		String line;
		BufferedReader in = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			in = new BufferedReader(
					new FileReader(
						classLoader.getResource(fileName).getFile()));
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
}

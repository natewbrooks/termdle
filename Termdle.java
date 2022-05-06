import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Termdle {
	
	public static  ArrayList<String> answers = new ArrayList<String>();
	public static  ArrayList<String> acceptedWords = new ArrayList<String>();

	public static String wordToGuess = "";
	
	public static String guess = "";
	public static  int guessCount = 0;
	
	private UserInterface ui;
	
	public Termdle(ArrayList<String> answersList, ArrayList<String> acceptedWords) {
		Termdle.answers = answersList;
		Termdle.acceptedWords = acceptedWords;
		
		this.ui = new UserInterface(this);
		
		//select a random word from the list
		int indexOfWord = (int) (Math.random() * answers.size());
		wordToGuess = answers.get(indexOfWord);
		System.out.println("Word to Guess: " + wordToGuess);
		//wipe the word from the list so we can't reuse it
		answers.remove(indexOfWord);
	}

	public static void NextLine() {
		guessCount++;
	}
}

class MyKeyListener implements KeyListener {
	public void keyPressed(KeyEvent e) {

		//if the guess is not already 5 chars and the new char isn't special or a number
		if(Termdle.guess.length() < 5 && Character.isLetter(e.getKeyChar())) {
			Termdle.guess += e.getKeyChar();
			System.out.println(Termdle.guess);
		}

		//if backspace is hit and the word has more than one letter, remove last letter
		if(e.getKeyCode() == 8 && (Termdle.guess.length() > 0)) {
			Termdle.guess = Termdle.guess.substring(0, Termdle.guess.length()-1);
			System.out.println(Termdle.guess);
		}
		
		//if word length is 5 and they hit enter, check if word is in accepted list
		if(Termdle.guess.length() == 5 && e.getKeyCode() == 10) {
			if(Termdle.acceptedWords.contains(Termdle.guess)) {
				// update ui
				Termdle.guess = "";
				Termdle.NextLine();
			} else {
				// shake? popup saying not a word?
				System.out.println("That is not an accepted word!");
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		return;
	}
	
	public void keyTyped(KeyEvent e) {
		return;
	}
}

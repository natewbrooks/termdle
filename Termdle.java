import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Termdle {
	
	public static ArrayList<String> answers = new ArrayList<String>();
	public static ArrayList<String> acceptedWords = new ArrayList<String>();

	public static String[][] boardChars = new String[5][5];
	public static Color[][] boardColors = new Color[5][5];

	public static String wordToGuess = "";
	
	public static String guess = "";
	public static int guessCount = 0;

	public static boolean gameOver = false;
	
	public static UserInterface ui;
	
	public Termdle(ArrayList<String> answersList, ArrayList<String> acceptedWords) {
		Termdle.answers = answersList;
		Termdle.acceptedWords = acceptedWords;
		
		Termdle.ui = new UserInterface();
		
		//select a random word from the list
		int indexOfWord = (int) (Math.random() * answers.size());
		wordToGuess = answers.get(indexOfWord);
		System.out.println("Word to Guess: " + wordToGuess);
		//wipe the word from the list so we can't reuse it
		answers.remove(indexOfWord);

		// set board to black
		for(int j = 0; j < boardColors.length; j++) {
			for(int i = 0; i < boardColors[0].length; i++) {
				boardColors[j][i] = Color.BLACK;
			}
		}
	}

	public static void NextLine() {
		guessCount++;
	}

	public static void Check(KeyEvent e) {
		//if the guess is not already 5 chars and the new char isn't special or a number
		if(Termdle.guess.length() < 5 && Character.isLetter(e.getKeyChar())) {
			Termdle.guess += e.getKeyChar();
			// update ui with letter pressed
			System.out.println(Termdle.guess);
		}

		//if backspace is hit and the word has more than one letter, remove last letter
		if(e.getKeyCode() == 8 && (Termdle.guess.length() > 0)) {
			Termdle.guess = Termdle.guess.substring(0, Termdle.guess.length()-1);
			System.out.println(Termdle.guess);
		}
		
		//if word length is 5 and they hit enter, check if word is in accepted list
		if(Termdle.guess.length() == 5 && e.getKeyCode() == 10) {
			if(Termdle.wordToGuess.equals(Termdle.guess)) {
				// YOU WIN! WINNER WINNER!
				Termdle.gameOver = true;
				ui.GetPanel().checkCorrectLetters(Termdle.guess);
				System.out.println("YOU WIN!!");
				// update ui to make you happy
			} else if(Termdle.acceptedWords.contains(Termdle.guess)) {
				// update ui
				ui.GetPanel().checkCorrectLetters(Termdle.guess);
				Termdle.guess = "";
				Termdle.NextLine();
			} else {
				// shake? popup saying not a word?
				System.out.println("That is not an accepted word!");
			}			
		}
	}
}

class MyKeyListener implements KeyListener {
	public void keyPressed(KeyEvent e) {
		if(!Termdle.gameOver) {
			Termdle.Check(e);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		return;
	}
	
	public void keyTyped(KeyEvent e) {
		return;
	}
}

import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Termdle {
	
	public static ArrayList<String> answers = new ArrayList<String>();
	public static ArrayList<String> acceptedWords = new ArrayList<String>();

	public static HashMap<Character, Color> keyboardColors = new HashMap<Character, Color>() {{
		put('q', Color.BLACK);
		put('w', Color.BLACK);
		put('e', Color.BLACK);
		put('r', Color.BLACK);
		put('t', Color.BLACK);
		put('y', Color.BLACK);
		put('u', Color.BLACK);
		put('i', Color.BLACK);
		put('o', Color.BLACK);
		put('p', Color.BLACK);
		put('a', Color.BLACK);
		put('s', Color.BLACK);
		put('d', Color.BLACK);
		put('f', Color.BLACK);
		put('g', Color.BLACK);
		put('h', Color.BLACK);
		put('j', Color.BLACK);
		put('k', Color.BLACK);
		put('l', Color.BLACK);
		put('z', Color.BLACK);
		put('x', Color.BLACK);
		put('c', Color.BLACK);
		put('v', Color.BLACK);
		put('b', Color.BLACK);
		put('n', Color.BLACK);
		put('m', Color.BLACK);
	}};

	public static String[][] boardChars = new String[5][5];
	public static Color[][] boardColors = new Color[5][5];
	public static Panel[][] panels = new Panel[5][5];
	public static Key[] keys = new Key[26];

	public static Color correctLetterPlacementColor = Color.GREEN;
	public static Color correctLetterColor = Color.ORANGE;
	public static Color missedLetterColor = Color.DARK_GRAY;
	public static Color notAWordColor = Color.RED;


	public static String wordToGuess = "";
	
	public static String guess = "";
	public static int guessCount = 0;

	public static boolean gameOver = false;
	public static boolean errorWord = false;
	public static int score = 0;
	
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
				boardChars[j][i] = "";
			}
		}
	}

	public static void NextLine() {
		guessCount++;
	}

	public static void Check(KeyEvent e) {
		if(errorWord && e.getKeyChar() == 8) {
			for(int i = 0; i < 5; i++) {
				Termdle.boardColors[Termdle.guessCount][i] = Color.BLACK;
				Termdle.panels[guessCount][i].drawBox();
			}
			errorWord = false;
		}
		
		//if the guess is not already 5 chars and the new char isn't special or a number
		char key = Character.toLowerCase(e.getKeyChar());
		if(Termdle.guess.length() < 5 && Character.isLetter(key)) {
			Termdle.guess += key;
			// update ui with letter pressed
			ui.GetPanel().Type();
		}

		//if backspace is hit and the word has more than one letter, remove last letter
		if(e.getKeyCode() == 8 && (Termdle.guess.length() > 0)) {
			Termdle.guess = Termdle.guess.substring(0, Termdle.guess.length()-1);
			ui.GetPanel().Type();
		}
		
		//if word length is 5 and they hit enter, check if word is in accepted list
		if(Termdle.guess.length() == 5 && e.getKeyCode() == 10) {
			if(Termdle.wordToGuess.equals(Termdle.guess)) {
				// YOU WIN! WINNER WINNER!
				Termdle.gameOver = true;
				Termdle.score++;
				ui.streakLabel.setText("YOU WON!! - R TO PLAY AGAIN");
				ui.streakLabel.setForeground(correctLetterPlacementColor);
				ui.GetPanel().checkCorrectLetters(Termdle.guess);
				Termdle.UpdateKeyboard();
 				// update ui to make you happy
			} else if(Termdle.acceptedWords.contains(Termdle.guess) && Termdle.guessCount == 4) {
				ui.GetPanel().checkCorrectLetters(Termdle.guess);
				ui.streakLabel.setText(Termdle.wordToGuess.toUpperCase() + " - R TO PLAY AGAIN");
				ui.streakLabel.setForeground(Termdle.notAWordColor);
				Termdle.gameOver = true;
				Termdle.score = 0;
				Termdle.UpdateKeyboard();
			} else if (Termdle.acceptedWords.contains(Termdle.guess) ){
				// update ui
				ui.GetPanel().checkCorrectLetters(Termdle.guess);
				Termdle.guess = "";
				Termdle.NextLine();
				Termdle.UpdateKeyboard();
			} else {
				// shake? popup saying not a word?
				for(int i = 0; i < 5; i++) {
					Termdle.boardColors[Termdle.guessCount][i] = Termdle.notAWordColor;
					Termdle.panels[guessCount][i].drawBox();
				}
				errorWord = true;
				System.out.println("That is not an accepted word!");
			}
		}

	}
	public static void UpdateKeyboard() {
		for (int i = 0; i < 26; i++) {
			Termdle.keys[i].drawBox();
		}
	}

	public static void RestartGame() {
		boardChars = new String[5][5];
		boardColors = new Color[5][5];
		wordToGuess = "";
		guess = "";
		guessCount = 0;
		gameOver = false;

		ui.streakLabel.setForeground(Color.WHITE);
		ui.streakLabel.setText("Score : " + score);
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
				boardChars[j][i] = "";
				panels[j][i].drawBox();
			}
		}
	}
}

class MyKeyListener implements KeyListener {
	public void keyPressed(KeyEvent e) {
		if(!Termdle.gameOver) {
			Termdle.Check(e);
		} else if (Termdle.gameOver && Character.toUpperCase(e.getKeyCode()) == 82) {
			Termdle.RestartGame();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		return;
	}
	
	public void keyTyped(KeyEvent e) {
		return;
	}
}

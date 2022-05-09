import java.util.*;
import java.awt.*;
import java.io.*;   
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Termdle {
	
	public static ArrayList<String> answers = new ArrayList<String>();
	public static ArrayList<String> acceptedWords = new ArrayList<String>();

	public static HashMap<Character, Color> keyboardColors = new HashMap<Character, Color>() {{
		put('q', backgroundColor);
		put('w', backgroundColor);
		put('e', backgroundColor);
		put('r', backgroundColor);
		put('t', backgroundColor);
		put('y', backgroundColor);
		put('u', backgroundColor);
		put('i', backgroundColor);
		put('o', backgroundColor);
		put('p', backgroundColor);
		put('a', backgroundColor);
		put('s', backgroundColor);
		put('d', backgroundColor);
		put('f', backgroundColor);
		put('g', backgroundColor);
		put('h', backgroundColor);
		put('j', backgroundColor);
		put('k', backgroundColor);
		put('l', backgroundColor);
		put('z', backgroundColor);
		put('x', backgroundColor);
		put('c', backgroundColor);
		put('v', backgroundColor);
		put('b', backgroundColor);
		put('n', backgroundColor);
		put('m', backgroundColor);
	}};

	public static int numRows = 6; // scrollbar is added for anything past 8 rows

	public static String[][] boardChars = new String[numRows][5];
	public static Color[][] boardColors = new Color[numRows][5];
	public static Panel[][] panels = new Panel[numRows][5];
	public static Key[] keys = new Key[26];

	public static Color correctLetterPlacementColor = new Color(54, 161, 27);
	public static Color correctLetterColor = new Color(212, 171, 38);
	public static Color missedLetterColor = new Color(74, 73, 71);
	public static Color notAWordColor = new Color(128, 33, 33);
	public static Color backgroundColor = new Color(31, 30, 28);
	public static Color textColor = Color.WHITE;

	public static String fontName = "Dotum";

	public static String wordToGuess = "";
	public static String guess = "";
	public static int guessCount = 0;
	public static String[] pastGuesses = new String[numRows];

	public static boolean gameOver = false;
	public static String winState = "INGAME";
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
		for(int j = 0; j < Termdle.numRows; j++) {
			for(int i = 0; i < boardColors[0].length; i++) {
				boardColors[j][i] = backgroundColor;
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
				Termdle.boardColors[Termdle.guessCount][i] = backgroundColor;
				Termdle.panels[guessCount][i].drawBox();
			}
			ui.streakLabel.setForeground(Termdle.textColor);
			ui.streakLabel.setText("Score : " + Termdle.score);
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
				Termdle.winState = "WIN";
				Termdle.gameOver = true;
				Termdle.score++;
				Termdle.pastGuesses[Termdle.guessCount] = Termdle.guess;
				Termdle.PrintGameResults();
				ui.streakLabel.setText("YOU WON!! - R TO PLAY AGAIN");
				ui.streakLabel.setForeground(correctLetterPlacementColor);
				ui.GetPanel().checkCorrectLetters(Termdle.guess);
				Termdle.UpdateKeyboard();
			} else if(Termdle.acceptedWords.contains(Termdle.guess) && Termdle.guessCount == Termdle.numRows-1) {
				// LOSS
				Termdle.winState = "LOSS";
				ui.GetPanel().checkCorrectLetters(Termdle.guess);
				Termdle.pastGuesses[Termdle.guessCount] = Termdle.guess;
				Termdle.PrintGameResults();
				ui.streakLabel.setText(Termdle.wordToGuess.toUpperCase() + " - R TO PLAY AGAIN");
				ui.streakLabel.setForeground(Termdle.notAWordColor);
				Termdle.gameOver = true;
				Termdle.score = 0;
				Termdle.UpdateKeyboard();
			} else if (Termdle.acceptedWords.contains(Termdle.guess) ){
				// update panels
				ui.GetPanel().checkCorrectLetters(Termdle.guess);
				Termdle.pastGuesses[Termdle.guessCount] = Termdle.guess;
				Termdle.guess = "";
				Termdle.NextLine();
				Termdle.UpdateKeyboard();

				// scroll down a box height
				if(Termdle.numRows > 7 && Termdle.guessCount > 5) {
					ui.scrollPane.getVerticalScrollBar().setValue(ui.scrollPane.getVerticalScrollBar().getValue() + 60);
				}
			} else {
				// shake? popup saying not a word?
				for(int i = 0; i < 5; i++) {
					Termdle.boardColors[Termdle.guessCount][i] = Termdle.notAWordColor;
					Termdle.panels[guessCount][i].drawBox();
				}
				ui.streakLabel.setForeground(Termdle.notAWordColor);
				ui.streakLabel.setText("NOT AN ACCEPTED WORD");
				errorWord = true;
			}
		}

	}
	public static void UpdateKeyboard() {
		for (int i = 0; i < 26; i++) {
			Termdle.keys[i].drawBox();
		}
	}

	public static void RestartGame() {
		boardChars = new String[Termdle.numRows][5];
		boardColors = new Color[Termdle.numRows][5];
		pastGuesses = new String[Termdle.numRows];
		wordToGuess = "";
		guess = "";
		guessCount = 0;
		gameOver = false;
		winState = "INGAME";

		ui.streakLabel.setForeground(textColor);
		ui.streakLabel.setText("Score : " + score);
		//select a random word from the list
		int indexOfWord = (int) (Math.random() * answers.size());
		wordToGuess = answers.get(indexOfWord);
		System.out.println("Word to Guess: " + wordToGuess);
		//wipe the word from the list so we can't reuse it
		answers.remove(indexOfWord);

		// set board to black
		for(int j = 0; j < Termdle.numRows; j++) {
			for(int i = 0; i < boardColors[0].length; i++) {
				boardColors[j][i] = backgroundColor;
				boardChars[j][i] = "";
				panels[j][i].drawBox();
			}
		}

		keyboardColors = new HashMap<Character, Color>() {{
			put('q', backgroundColor);
			put('w', backgroundColor);
			put('e', backgroundColor);
			put('r', backgroundColor);
			put('t', backgroundColor);
			put('y', backgroundColor);
			put('u', backgroundColor);
			put('i', backgroundColor);
			put('o', backgroundColor);
			put('p', backgroundColor);
			put('a', backgroundColor);
			put('s', backgroundColor);
			put('d', backgroundColor);
			put('f', backgroundColor);
			put('g', backgroundColor);
			put('h', backgroundColor);
			put('j', backgroundColor);
			put('k', backgroundColor);
			put('l', backgroundColor);
			put('z', backgroundColor);
			put('x', backgroundColor);
			put('c', backgroundColor);
			put('v', backgroundColor);
			put('b', backgroundColor);
			put('n', backgroundColor);
			put('m', backgroundColor);
		}};
	}

	public static void PrintGameResults() {
		try {
			// write to last game
			FileWriter write = new FileWriter(new File("termdle-last-game.txt"));
			BufferedWriter writer = new BufferedWriter(write);
			writer.write("[MOST RECENT GAME]");
			writer.newLine();
			writer.newLine();
			writer.write("[WORD: " + Termdle.wordToGuess.toUpperCase() + "]");
			writer.newLine();
			writer.newLine();


			for(int i = 0; i < guessCount+1; i++) {
				writer.write(" - " + Termdle.pastGuesses[i].toUpperCase());
				writer.newLine();
			}

			writer.newLine();
			writer.write("+-" + Termdle.winState + " " + (guessCount+1) + "/" + Termdle.numRows + "-+");


			writer.newLine();
			writer.newLine();
			writer.close();

			// append to full record
			write = new FileWriter(new File("termdle-records.txt"),true);
			writer = new BufferedWriter(write);
			writer.write("[WORD: " + Termdle.wordToGuess.toUpperCase() + "]");
			writer.newLine();
			writer.newLine();


			for(int i = 0; i < guessCount+1; i++) {
				writer.write(" - " + Termdle.pastGuesses[i].toUpperCase());
				writer.newLine();
			}

			writer.newLine();
			writer.write("+-" + Termdle.winState + " " + (guessCount+1) + "/" + Termdle.numRows + "-+");

			writer.newLine();
			writer.newLine();
			writer.newLine();
			writer.close();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
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

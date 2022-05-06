import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Termdle implements KeyListener {
	
	private ArrayList<String> answers = new ArrayList<String>();
	private String wordToGuess = "";
	
	private String guess = "";
	private int guessCount = 0;
	
	private UserInterface ui;
	
	public Termdle(ArrayList<String> answersList) {
		this.answers = answersList;
		this.ui = new UserInterface(this);
		
		//select a random word from the list
		int indexOfWord = (int) (Math.random() * answers.size());
		wordToGuess = answers.get(indexOfWord);
		//wipe the word from the list so we can't reuse it
		answers.remove(indexOfWord);
	}
	
	public void keyPressed(KeyEvent e) {
		guess += e.toString();
		System.out.println(e.toString());
	}
	
	public void keyReleased(KeyEvent e) {
		return;
	}
	
	public void keyTyped(KeyEvent e) {
		return;
	}
}


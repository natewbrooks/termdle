import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class UserInterface {

	private Panel p = new Panel();

	public UserInterface() {
		createGUI();
	}
	
	public void createGUI() {
		JFrame frame = new JFrame("Termdle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(p);
		
		frame.pack();
		frame.setVisible(true);
	}

	public Panel GetPanel() {
		return p;
	}
}

class Panel extends JPanel {
	public Panel() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.addKeyListener(new MyKeyListener());
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(500,800);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoxes(g);
	}
	
	public void drawBoxes(Graphics g) {
		for(int j = 0; j < 5; j++) {
			for(int i = 0; i < 5; i++) {
				g.setColor(Termdle.boardColors[j][i]);
				g.fillRect((i+1)*55,(j+1)*55,50,50);

				// g.setColor(Color.WHITE);
				// g.setFont(new Font("TimesRoman", Font.PLAIN, 35));
				// g.drawString("Y", (i+1)*65, (j+1)*90);
			}
		}
	}
	
	public void checkCorrectLetters(String guess) {

		HashMap<Character, Integer> letterCount = new HashMap<Character, Integer>();

		for(char c : Termdle.wordToGuess.toCharArray()) {
			if(letterCount.containsKey(c)) {
				letterCount.put(c, letterCount.get(c)+1);
			} else {
				letterCount.put(c, 1);
			}
		}

		for(int i = 0; i < 5; i++) {
			String letter = guess.substring(i,i+1);
			char c = letter.charAt(0);

			if(letter.equals(Termdle.wordToGuess.substring(i,i+1))) {
				//set box to green
				letterCount.put(c, letterCount.get(c)-1);
				Termdle.boardColors[Termdle.guessCount][i] = Color.GREEN;
			} else if (Termdle.wordToGuess.contains(letter) && letterCount.get(c) > 0) {
				// set box to orange
				letterCount.put(c, letterCount.get(c)-1);
				Termdle.boardColors[Termdle.guessCount][i] = Color.ORANGE;
				
			} else {
				Termdle.boardColors[Termdle.guessCount][i] = Color.BLACK;
			}
		}
		
		this.repaint();
	}


}


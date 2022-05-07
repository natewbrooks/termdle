import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class UserInterface {

	private Panel p = new Panel();
	public JLabel streakLabel = new JLabel("Streak : " + Termdle.score);

	public UserInterface() {
		createGUI();
	}
	
	public void createGUI() {
		JFrame frame = new JFrame("Termdle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(Color.BLACK);

		JPanel board = new JPanel();
		board.setLayout(new GridBagLayout());
		board.setBackground(Color.BLACK);
		board.setPreferredSize(new Dimension(400,300));

		JPanel keyboard = new JPanel();
		keyboard.setLayout(new GridBagLayout());
		keyboard.setBackground(Color.BLACK);
		keyboard.setPreferredSize(new Dimension(500,200));
		
		for(int h = 0; h < Termdle.keyboardColors.keySet().size(); h++) {
			Key k = new Key();
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.NONE;

			if((double) Termdle.keyboardColors.keySet().size()/h > 2.0) {
				c.gridx = h;
				c.gridy = 3;
			} else {
				c.gridx = h-13;
				c.gridy = 4;
			}
			
			Termdle.keys[h] = k;

			k.x.setText(Termdle.keyboardColors.keySet().toArray()[h].toString());
			keyboard.add(k,c);
		}

		for(int j = 0; j < 5; j++) {
			for(int i = 0; i < 5; i ++) {
				Panel p = new Panel();
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.NONE;
				c.gridx = i+1;
				c.gridy = j+1;

				Termdle.panels[j][i] = p;
				board.add(p,c);
			}
		}

		JPanel text = new JPanel();
		text.setBackground(Color.BLACK);

		JPanel x = new JPanel();
		x.setBackground(Color.BLACK);

		JLabel label = new JLabel("Termdle");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Serif", Font.PLAIN, 25));

		streakLabel.setForeground(Color.WHITE);
		streakLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		
		text.add(label);
		x.add(streakLabel);
		
		GridBagConstraints cons = new GridBagConstraints();
 		cons.gridx = 5;
		cons.gridy = 0;

		frame.add(text, cons);
		cons.gridx = 5;
		cons.gridy = 1;
		cons.ipady= 60;


		frame.add(x, cons);
		cons.gridx = 5;
		cons.gridy = 2;


		frame.add(board, cons);

		cons.gridx = 5;
		cons.gridy = 4;
		cons.ipady= 30;
		frame.add(keyboard, cons);
		
		frame.pack();
		frame.setVisible(true);
	}

	public Panel GetPanel() {
		return p;
	}
}

class Panel extends JPanel {
	JLabel x = new JLabel("");

	public Panel() {
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		setLayout(new FlowLayout());
		x.setForeground(Color.WHITE);
		x.setFont(new Font("Serif", Font.PLAIN, 30));
		add(x,SwingConstants.CENTER);

		this.addKeyListener(new MyKeyListener());
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(60,60);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBox();
	}

	public void drawBox() {
		for(int j = 0; j < 5; j++) {
			for(int i = 0; i < 5; i++) {
				if(Termdle.panels[j][i] == this) {
					setBackground(Termdle.boardColors[j][i]);
					x.setText(Termdle.boardChars[j][i]);
				}
			}
		}
		repaint();
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
				Termdle.boardColors[Termdle.guessCount][i] = Termdle.correctLetterPlacementColor;
				Termdle.keyboardColors.put(c, Termdle.correctLetterPlacementColor);
			} else if (Termdle.wordToGuess.contains(letter) && letterCount.get(c) > 0) {
				// set box to orange
				letterCount.put(c, letterCount.get(c)-1);
				Termdle.boardColors[Termdle.guessCount][i] = Termdle.correctLetterColor;
				if(Termdle.keyboardColors.get(c) != Termdle.correctLetterPlacementColor) {
					Termdle.keyboardColors.put(c, Termdle.correctLetterColor);
				}
			} else {
				Termdle.boardColors[Termdle.guessCount][i] = Termdle.missedLetterColor;
				Termdle.keyboardColors.put(c, Termdle.missedLetterColor);
				Termdle.keyboardColors.put(c, Termdle.missedLetterColor);
			}
		}
		
		this.repaint();
	}

	public void Type() {
		for(int i = 0; i < 5; i++) {
			try {
				Termdle.boardChars[Termdle.guessCount][i] = Termdle.guess.substring(i, i+1);
			} catch (Exception e) {
				Termdle.boardChars[Termdle.guessCount][i] = "";
			}
		}

		this.repaint();
	}


}

class Key extends JPanel {
	public JLabel x = new JLabel("");

	public Key() {
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		setLayout(new FlowLayout());
		x.setForeground(Color.WHITE);
		x.setFont(new Font("Serif", Font.PLAIN, 15));
		add(x,SwingConstants.CENTER);

		this.addKeyListener(new MyKeyListener());
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(30,30);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBox();
	}

	public void drawBox() {
		setBackground(Termdle.keyboardColors.get(x.getText().toCharArray()[0]));
		repaint();
	}
}
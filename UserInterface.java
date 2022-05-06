import javax.swing.*;
import java.awt.*;

public class UserInterface {
	
	private Termdle game;
	
	public UserInterface(Termdle termdle) {
		this.game = termdle;
		createGUI();
	}
	
	public void createGUI() {
		JFrame frame = new JFrame("Termdle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new Panel());
		
		frame.pack();
		frame.setVisible(true);
	}
}

class Panel extends JPanel {
	public Panel() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(500,800);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoxes(g);
	}
	
	public void drawBoxes(Graphics g) {
		for(int j = 1; j <= 5; j++) {
			for(int i = 1; i <= 5; i++) {
				g.fillRect(i*55,j*55,50,50);
				g.setColor(Color.BLACK);
			}
		}
	}
	
	/*
	public void checkCorrectLetters() {
		for(int i = 1; i <= 5; i+) {
			String letter = response.substring(i,i+1);
			g.fillRect(i*55, guessCount*55, 50, 50);
			
			if(letter.equals(answer.substring(i,i+1)) {
				//set box to green
				g.setColor(Color.GREEN);
				
			} else if (answer.contains(letter)) {
				// set box to yellow
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.BLACK);
			}
		} 
	}
	*/
	 
	
	
}


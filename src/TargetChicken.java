import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class TargetChicken extends JPanel {
	
	// String of the target Chicken
	private String targetChicken;
	
	// Constants used to draw and timer constant
	public static final int ICON_WIDTH = 30;
	public static final int ICON_HEIGHT = 30;
	private static final int INTERVAL = 100;
	
	/**
	 * Constructor for a random target chicken
	 */
	public TargetChicken() {
		
		// randomly generates a target chicken out of five
		double randomInteger = (Math.random() * 5.00);
		
		// determines what the chicken is based on the random integer
		if (randomInteger < 1.00) {
			targetChicken = "chick";
		} else if (randomInteger < 2.00) {
			targetChicken = "mickey";
		} else if (randomInteger < 3.00) {
			targetChicken = "nugget";
		} else if (randomInteger < 4.00) {
			targetChicken = "rooster";
		} else if (randomInteger < 5.00) {
			targetChicken = "wing";
		}
		
		// sets a border
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// timer
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start();
		setFocusable(true);	
	}
	
	/**
	 * Sets a new target chicken
	 * @param toBeSet the chicken to be set
	 */
	public void setTarget(String toBeSet) {
		targetChicken = toBeSet;
	}
	
	/**
	 * Gets the target chicken
	 * @return the target chicken
	 */
	public String getTarget() {
		return targetChicken;
	}
	
	/**
	 * Resets the target chicken through setting it via the chicken array presented
	 * @param currentArray the chicken array that is being used
	 */
	public void reset(ChickenArray currentArray) {
		targetChicken = currentArray.getTargetString();
	}
	
	/**
	 * repaints the target chicken to ensure it is updated with the court
	 */
	void tick() {
		repaint();
	}
	
	/**
	 * Draws the target chicken
	 * @param g
	 */
	public void draw(Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("files/" + getTarget() + ".png")),
					0, 0, 30, 30, null);
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(ICON_WIDTH, ICON_HEIGHT);
	}

}

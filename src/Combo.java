import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * Uses collections to store chicken combos
 * A chicken combo occurs every time the player gets three of a same type of chicken stored
 * @author benhuang
 */

@SuppressWarnings("serial")
public class Combo  extends JPanel {
	
	// A map of combos, which uses collections
	private Map<String, Integer> found;
	
	// constants for the chicken combo, dimensions and time interval
	public static final int COLUMN_WIDTH = 20;
	public static final int COLUMN_HEIGHT = 200;
	private static final int INTERVAL = 1000;
	
	/**
	 * Constructs a new combo bank where the value of each type is 0
	 */
	public Combo() {
		
		// Creates a new treemap for the chicken combos, 0 for all types at first
		found = new TreeMap<String, Integer>();
		found.put("chick", 0);
		found.put("mickey", 0);
		found.put("nugget", 0);
		found.put("rooster", 0);
		found.put("wing", 0);
		
		// Creates a border
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// timer for the game
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start();
		setFocusable(true);
	}

	/**
	 * Gets the map of combos
	 * @return
	 */
	public Map<String, Integer> getFound() {
		return found;
	}
	
	/**
	 * Sets the combos
	 * @param combos the combos to be set
	 */
	public void setCombo(Map<String, Integer> combos) {
		found = combos;
	}
	
	/**
	 * Adds a chicken to the combo bank after each round
	 * @param chickenType
	 */
	public void addFound(String chickenType) {
		int addOne = found.get(chickenType) + 1;
		found.put(chickenType, addOne);
	}
	
	/**
	 * Returns whether a combo is present, a helper function
	 * @param search the chicken type to look up for combos
	 * @return boolean of whether a combo exists
	 */
	public boolean areThree(String search) {
		if (found.get(search).equals(3)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Removes the chickens after a combo is found, removes any mapping of chicken type
	 * to the integer 3, which is required for the combo
	 */
	public void remove() {
		for (Map.Entry<String, Integer> entry : found.entrySet()) {
			if (areThree(entry.getKey())) {
				found.put(entry.getKey(), 0);
			}
		}
	}
	
	/**
	 * Resets the combo bank to 0
	 */
	public void reset() {
		found = new TreeMap<String, Integer>();
		found.put("chick", 0);
		found.put("mickey", 0);
		found.put("nugget", 0);
		found.put("rooster", 0);
		found.put("wing", 0);
	}
	
	/** 
	 * Repaints the combo bank every interval
	 */
	void tick() {
		repaint();
	}
	
	/**
	 * Draws the combos feature in a vertical column
	 */
	public void draw(Graphics g) {
		int position = 0;
		
		// vertically draws the chickens in the combo bank
		try {
			for (Map.Entry<String, Integer> entry : found.entrySet()) {
				if (entry.getValue().equals(0)) {
				} else if (entry.getValue().equals(1)) {
					g.drawImage(ImageIO.read(new File("files/" + entry.getKey() + ".png")),
							0, position, 20, 20, null);
					position += 20;
				} else if (entry.getValue().equals(2)) {
					g.drawImage(ImageIO.read(new File("files/" + entry.getKey() + ".png")),
							0, position, 20, 20, null);
					position += 20;
					g.drawImage(ImageIO.read(new File("files/" + entry.getKey() + ".png")),
							0, position, 20, 20, null);
					position += 20;
				}
			}
		} catch (Exception e) {
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
		return new Dimension(COLUMN_WIDTH, COLUMN_HEIGHT);
	}
}

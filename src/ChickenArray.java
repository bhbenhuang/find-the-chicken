import java.util.*;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ChickenArray {

	// represents the game using a 2D array of chicken
	private Chicken[][] chickenCourt;
	
	// represent the dimension of the game: size x size grid
	private int dimension;
	
	// which chicken is the objective of this round? Where is it located?
	private TargetChicken target;
	private String targetChickenString;
	private int targetPx;
	private int targetPy;
	
	// which four chickens are not the objective of this round? Represented with constant type
	private ArrayList<String> loserChickens;
	
	// what round is it?
	private int round;
	
	/**
	 * Constructor for constructing a chicken array with random target
	 * @param round the round number determines the size of the array
	 */
	public ChickenArray(int round) {
		
		// initializes the round, target, targetChickenString
		this.round = round;
		target = new TargetChicken();
		targetChickenString = target.getTarget();
		
		// Caps the dimension to be 25 max, the size of the screen
		if (round < 25) {
			dimension = round + 1;
		} else {
			dimension = 25;
		}
		
		// new chicken court
		chickenCourt = new Chicken[dimension][dimension];
		
		// initializes the ArrayList of loserChickens
		loserChickens = new ArrayList<String>();
		loserChickens.add("chick");
		loserChickens.add("mickey");
		loserChickens.add("nugget");
		loserChickens.add("rooster");
		loserChickens.add("wing");
		loserChickens.remove(getTargetString());
		
		// initializes entire court
		populateCourt(chickenCourt);
		
	}
	
	/**
	 * Helper method that sets up 2D array of chicken
	 * Takes in the court to be set up
	 * First places the position of the one target chicken
	 * Then places the other four chickens at random in the remaining positions
	 */
	public void populateCourt(Chicken[][] court) {

		// places the loser chickens randomly throughout court
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				String randomChicken = loserChickens.get((int) (Math.random() * 4));
				court[i][j] = new Chicken(randomChicken, false);
			}
		}
		
		// places the target chicken in a random location
		targetPx = (int) (Math.random() * dimension);
		targetPy = (int) (Math.random() * dimension);
		court[targetPx][targetPy] = new Chicken(getTargetString(), true);
		
	}
	
	/**
	 * Gets the chicken array
	 * @return the chicken array
	 */
	public Chicken[][] toArray() {
		return chickenCourt;
	}
	
	/**
	 * Gets the round number
	 * @return the round number
	 */
	public int getRound() {
		return round;
	}
	
	/**
	 * Gets the dimension
	 * @return the dimension which is round + 1
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * Gets the target chicken of this array
	 * @return the target chicken
	 */
	public TargetChicken getTargetChicken() {
		return target;
	}
	
	/**
	 * Gets the target chicken of this array
	 * @return the string of the target chicken
	 */
	public String getTargetString() {
		return targetChickenString;
	}
	
	/**
	 * Gets the loser array list of chicken
	 * @return the loser chicken array list
	 */
	public ArrayList<String> getLosers() {
		return loserChickens;
	}
	
	/**
	 * Gets the x position of the target
	 * @return the x integer of the target
	 */
	public int getTargetPx() {
		return targetPx;
	}
	
	/**
	 * Gets the y position of the target
	 * @return the y integer of the target
	 */
	public int getTargetPy() {
		return targetPy;
	}
	
	
	/**
	 * returns whether the x and y locations are correct for the game
	 * @param px the x location
	 * @param py the y location
	 * @return whether the x and y locations match that of the target chicken
	 */
	public boolean rightIntersection(int px, int py) {
		return (px >= (350 - ((dimension - 2) * 15) + getTargetPx() * 30) && 
				(px <= (350 - ((dimension - 2) * 15) + getTargetPx() * 30 + 30)) &&
				(py >= 350 - ((dimension - 2) * 15) + getTargetPy() * 30) && 
				(py <= (350 - ((dimension - 2) * 15) + getTargetPy() * 30 + 30)));
	}
	
	/** 
	 * Converts a chicken type to a file graphic
	 * @param g the graphics
	 * @param chickenType the type of chicken as a string
	 * @param x the x position
	 * @param y the y position
	 */
	public void stringToFile(Graphics g, String chickenType, int x, int y) {
		try {
			g.drawImage(ImageIO.read(new File("..//files/" + chickenType + ".png")),
					x, y, 30, 30, null);
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}
	
	/**
	 * Draws the Chicken Array
	 * @param g the Graphics object
	 */
	public void draw(Graphics g) {
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				
				// Finds the position of each chicken
				String currentChicken = chickenCourt[i][j].getType();
				int xPosition = 350 - ((dimension - 2) * 15) + i * 30;
				int yPosition = 350 - ((dimension - 2) * 15) + j * 30;
				
				// Draws the file depending on the type
				if (currentChicken.equals("chick")) {
					stringToFile(g, "chick", xPosition, yPosition);
				} else if (currentChicken.equals("mickey")) {
					stringToFile(g, "mickey", xPosition, yPosition);
				} else if (currentChicken.equals("nugget")) {
					stringToFile(g, "nugget", xPosition, yPosition);
				} else if (currentChicken.equals("rooster")) {
					stringToFile(g, "rooster", xPosition, yPosition);
				} else if (currentChicken.equals("wing")) {
					stringToFile(g, "wing", xPosition, yPosition);
				}
			}
		}
	}
}

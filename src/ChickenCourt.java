import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Map;

import javax.swing.*;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class ChickenCourt extends JPanel {
	
	// is it not game over? is the game in the middle of a round?
	public boolean playing = false;
	public boolean inRound = false;
	
	// ChickenArray representation, which includes the objective chicken
	private ChickenArray chickenGrid;
	
	// time, status, round status, high-score status
	private Clock time;
	private JLabel timeStatus;
	private JLabel status;
	private int round;
	private JLabel roundStatus;
	private JLabel highScoreStatus;
	
	// Shows us the target chicken, the chicken combos, high scores
	private TargetChicken targetChicken = new TargetChicken();
	private Combo chickenCombos = new Combo();
	private HighScores highScores;
	
	// x and y positions of mouse, whether it has been clicked or not
	private boolean mouseClick = false;
	private int mousePx;
	private int mousePy;
	
	// dimensions of the panel, interval of one second
	public static final int COURT_WIDTH = 825;
	public static final int COURT_HEIGHT = 825;
	public static final int INTERVAL = 300;
	
	/**
	 * Constructs the chicken court
	 * @param status the status JLabel
	 * @param clockStatus the JLabel of the clock
	 * @param roundStatus the round number JLabel
	 * @param highScoreStatus the JLabel of the high score
	 */
	public ChickenCourt(JLabel status, JLabel clockStatus, JLabel roundStatus, 
			JLabel highScoreStatus) {
		
		// creates a border around the court
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// timer object which includes tick method
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start();
		setFocusable(true);
		
		// registers the mouse-click
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mouseClick = true;
				mousePx = e.getX();
				mousePy = e.getY();
			}
		});
		
		// initializes the JLabels
		this.status = status;
		timeStatus = clockStatus;
		this.roundStatus = roundStatus;
		this.highScoreStatus = highScoreStatus;
		highScores = new HighScores();
	}
	
	/**
	 * Resets game to initial state
	 */
	public void reset(int round, double timeRemaining, Map<String, Integer> combos,
			int[] highScores) {
		
		// Resets the fields
		this.round = round;
		this.highScores.setHighScores(highScores);
		chickenCombos.setCombo(combos);
		time = new Clock(timeRemaining);
		chickenGrid = new ChickenArray(round);
		targetChicken.setTarget(chickenGrid.getTargetString());
		status.setText("Running...");
		mousePx = -1;
		mousePy = -1;
		playing = true;
		inRound = true;
		requestFocusInWindow();
	}
	
	/**
	 * This method is called every time the timer is defined in the constructor
	 */
	void tick() {
		if (playing) {
			
			// Updates the round number
			roundStatus.setText("Round " + getRound() + "!");
			
			if (inRound) {
				
				// Has the game run out of time?
				if (time.getTime() < 0.0) { 
					
					// Reads the current High Scores and outputs it
					try {
						highScores.inputHighScores("files/highscores.txt");
						highScores.placeHighScore(round - 1);
						highScores.outputHighScores("files/highscores.txt");
					} catch (Exception e) {
						highScores.placeHighScore(round - 1);
						highScores.outputHighScores("files/highscores.txt");
					} 

					// Changes the status display
					status.setText("Game over... You found " + (round - 1) + 
							" chickens! The High Scores are: " + highScores.toString() + ".");
					playing = false;
					
				// Has the mouse been clicked? Is it in the playing area?
				} else if (mouseClick 
						&& (mousePx < 350 - ((round - 1) * 15) + (getRound() + 1) * 30)
						&& (mousePx > 350 - ((round - 1) * 15))
						&& (mousePy < 350 - ((round - 1) * 15) + (getRound() + 1) * 30)
						&& (mousePy > 350 - ((round - 1) * 15))) {
					
					// pressed wrong chicken
					if (!(chickenGrid.rightIntersection(mousePx, mousePy))) {
						time.decrement(5.0);
						status.setText("Chicken Error, -5 seconds");
						mouseClick = false;
					}
					
					// pressed right chicken
					else if (chickenGrid.rightIntersection(mousePx, mousePy)) {
						time.increment(1.0);
						status.setText("Found the Chicken! +1 seconds");
						inRound = false;
						mouseClick = false;
						round++;
						chickenCombos.addFound(chickenGrid.getTargetString());
						if (chickenCombos.areThree(chickenGrid.getTargetString())) {
							time.increment(5.0);
							status.setText("WINNER WINNER CHICKEN DINNER +5 seconds");
							chickenCombos.remove();
						}
					}
				}
				
				//decrement the clock by 0.3 seconds every 300 milliseconds
				time.decrement(0.3);
				if (time.getTime() > 0) {
					timeStatus.setText("Time Remaining: " + (time.getTime()));
				} else {
					timeStatus.setText("Time Remaining: " + 0);
				}
				repaint();
			}
			
			// Are we in between rounds?
			else if (!inRound) {
				reset(getRound(), time.getTime(), chickenCombos.getFound(), 
						highScores.getHighScores());
				repaint();
			}
		}
	}
	
	/**
	 * Gets the round number
	 * @return the integer of the round number
	 */
	public int getRound() {
		return round;
	}
	
	/**
	 * Gets the target chicken
	 * @return the TargetChicken of the target
	 */
	public TargetChicken getTarget() {
		return targetChicken;
	}
	
	/**
	 * Gets the combo
	 * @return the Combo of the chicken combos
	 */
	public Combo getChickenCombos() {
		return chickenCombos;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Set a white background for main playing area
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, getWidth(), getHeight());
		chickenGrid.draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;


public class Game implements Runnable {
	
	/** 
	 * Runs the game
	 */
	public void run() {
		
		// The name of the game
		final JFrame frame = new JFrame("FIND THE CHICKEN");
		frame.setLocation(600, 600);
		
		// Status Panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.NORTH);
		final JLabel status = new JLabel("Running...");
		
		// Clock display, Round Display, High Score 
		final JLabel clock = new JLabel("Time Remaining: " + 30.0);
		final JLabel round = new JLabel("Round 1!");
		final JLabel highScores = new JLabel("");
		
		// Main playing area
		ChickenCourt court = new ChickenCourt(status, clock, round, highScores);
		frame.add(court, BorderLayout.CENTER);
		
		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.SOUTH);
		
		// Add action listener to reset button
		final JButton reset = new JButton("Restart");
		HighScores highScoresObject = new HighScores();
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					highScoresObject.inputHighScores("files/highscores.txt");
				} catch (IOException | HighScores.FormatException e1) {
					System.out.println("Internal Error:" + e1.getMessage());
				}
				court.reset(1, 30.0, (new Combo()).getFound(), 
						highScoresObject.getHighScores());
				}
			});
		
		// target chicken
		final JPanel target_panel = new JPanel();
		target_panel.add(new JLabel("Find the"));
		target_panel.add(court.getTarget());
		
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// instructions button and panel
		final JPanel instructions_panel = new JPanel();
		frame.add(instructions_panel, BorderLayout.EAST);
		final JButton instructions = new JButton("Instructions");
		JLabel instructionsLabel = new JLabel("");
		instructions_panel.add(instructionsLabel);
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (instructionsLabel.getText().equals("")) {
					instructions.setText("Return");
					instructionsLabel.setText("<html> <center><br> BEN HUANG CIS120 GAME <br>"
							+ "<br>FIND THE CHICKEN<br>"
							+ "<br>"
							+ "<br>"
							+ "Chicken is a simple meat. <br>"
							+ "FIND THE CHICKEN is a simple game. <br>"
							+ "<br>"
							+ "There are five chicken: chicks, nuggets, roosters, wings <br>"
							+ "and my roommate Mickey "
							+ "(who's the most chicken of the five chicken)."
							+ "<br>"
							+ "Click the specified chicken before time runs out!<br>"
							+ "<br>"
							+ "+1 second if you find the chicken <br>"
							+ "-5 seconds if you find the wrong chicken<br>"
							+ "+5 seconds if you find three of the same type of "
							+ "chicken across rounds<br>"
							+ "(WINNER WINNER CHICKEN DINNER)<br>"
							+ "<br>"
							+ "The top bar displays the round, target and time.<br>"
							+ "The bottom bar displays the reset button, "
							+ "instructions button and status. <br>"
							+ "The left bar displays the chicken combo bank.<br>"
							+ "<br>"
							+ "At the end of each game, the status displays "
							+ "the score and the high scores. <br>"
							+ "<br>"
							+ "Now, don't be a chicken and FIND THE CHICKEN.<br>"
							+ "<br>"
							+ "</center><html>");
				} else {
					instructions.setText("Instructions");
					instructionsLabel.setText("");
				}	
			}
		});
		
		// Start game
		court.reset(1, 30.0, (new Combo()).getFound(), 
				highScoresObject.getHighScores());
		
		// adding the labels to the panel
		control_panel.add(instructions);
		control_panel.add(status);
		control_panel.add(reset);
		status_panel.add(round);
		status_panel.add(target_panel);
		status_panel.add(clock);
		frame.add(court.getChickenCombos(), BorderLayout.WEST);
		
	}
	
	/**
	 * Main method
	 * @param args 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}

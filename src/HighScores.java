import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Uses an array of length three for high scores
 * Does not use collections because collections was used for combos
 * @author benhuang
 */
public class HighScores {
	
	// the list of integers on the program
	private int[] highScores = {0, 0, 0};
	
	// The buffered reader and buffered writer for I/O
	private BufferedReader in;
	private BufferedWriter out;
	
	/**
	 * Constructs the high scores object
	 */
	public HighScores() {
	}
	
	/**
	 * Gets the three high scores 
	 * @return the array of length three of high scores
	 */
	public int[] getHighScores() {
		return highScores;
	}
	
	/**
	 * Returns a string of the high scores
	 */
	public String toString() {
		return "" + highScores[0] + ", " + highScores[1] + ", " + highScores[2];
	}
	
	/**
	 * Sets an array of high scores
	 * @param toBeSet the array of high scores to set
	 */
	public void setHighScores(int[] toBeSet) {
		highScores = toBeSet;
	}
	
	/** 
	 * Checks and places a new score based on whether it is a high score
	 * Places it in the correct location from highest to lowest
	 * @param newScore
	 */
	public void placeHighScore(int newScore) {
		
		// Determines placement of the new score
		if (newScore > highScores[0]) {
			highScores[2] = highScores[1];
			highScores[1] = highScores[0];
			highScores[0] = newScore;
		} else if (newScore > highScores[1]) {
			highScores[2] = highScores[1];
			highScores[1] = newScore;
		} else if (newScore > highScores[2]) {
			highScores[2] = newScore;
		}
	}
	
	/**
	 * Outputs the high scores into a given file
	 * @param outputFile the file to be outputted into
	 */
	public void outputHighScores(String outputFile) {
		Writer o;
		
		try {
			o = new FileWriter(outputFile);
			out = new BufferedWriter(o);
		} catch (IOException e1) {
			System.out.println("Internal Error:" + e1.getMessage());
		}
		
		int number1 = highScores[0];
		int number2 = highScores[1];
		int number3 = highScores[2];

		try {
			out.write("" + number1 + "\n");
			out.write("" + number2 + "\n");
			out.write("" + number3);
			out.close();
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}

	}
	
	/**
	 * Creates a static class format exception that can be used (similar to HW08)
	 */
	public static class FormatException extends Exception {
        public FormatException(String msg) {
            super(msg);
        }
    }
	
	/**
	 * Inputs the high scores from a specified file
	 * @param filename The file to read from
	 * @throws IOException 
	 * @throws FormatException
	 */
	public void inputHighScores(String filename) throws IOException, FormatException {
		
		if (filename == null) {
			throw new IllegalArgumentException();
		}	
		
		Reader r = new FileReader(filename);
		in = new BufferedReader(r);
		String s;
		
		try {
			for (int i = 0; i < 3; i++) {
				s = in.readLine();
				highScores[i] = Integer.parseInt(s);
			}
		} catch (IOException e) {
			s = null;
		}
		
		r.close();
	}
	

}
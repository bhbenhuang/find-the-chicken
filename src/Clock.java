public class Clock {
	
	// double representation of the clock
	private double counter;
	
	/**
	 * Clock constructor given double of time interval
	 * @param double initial of time
	 */
	public Clock(double initial) {
		counter = initial;
	}
	
	/**
	 * Gets the time remaining
	 * @return integer of time remaining
	 */
	public int getTime() {
		return (int) counter;
	}
	
	/**
	 * Decrements clock
	 * @param value the amount to decrement
	 */
	public void decrement(double value) {
		counter = counter - value;
	}
	
	/**
	 * Increments clock
	 * @param value the amount to increment
	 */
	public void increment(double value) {
		counter = counter + value;
	}
}

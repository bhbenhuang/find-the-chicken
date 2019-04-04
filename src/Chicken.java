public class Chicken {
	
	// type of chicken, whether it is the target
	private String type; 
	private boolean target;
	
	/**
	 * Constructor for chicken
	 * @param type what type out of the five types?
	 * @param target whether it is the target of the round
	 */
	public Chicken(String type, boolean target) {
		this.type = type;
		this.target = target;
	}

	/**
	 * Gets the string of the type
	 * @return the string of the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets whether it is the target
	 * @return the boolean of whether it is the target
	 */
	public boolean isTarget() {
		return target;
	}
}

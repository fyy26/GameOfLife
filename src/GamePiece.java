/**
 * The GamePiece class is an abstract superclass representing pieces of a general board game. 
 */

/**
 * @author Yuying Fan
 */
public abstract class GamePiece {
	
	/**
	 * A numerical value representing the state of the game piece
	 */
	private int value;

	/**
	 * Constructs a GamePiece with a specified numerical value representing its state
	 * 
	 * @param value a numerical value representing the state of the game piece
	 */
	public GamePiece(int value) {
		this.value = value;
	}

	/**
	 * @return the value of this GamePiece
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param newState the new value to assign to this GamePiece
	 */
	public void changeState(int newState) {
		value = newState;
	}

	/**
	 * @return the String representation of this GamePiece, which is consisted of its value as a String
	 */
	@Override
	public String toString() {
		return "" + value;
	}
	
	/**
	 * @return a clone of this GamePiece
	 */
	@Override
	public abstract GamePiece clone();
	
}

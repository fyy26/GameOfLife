/**
 * The Cell class is a subclass of the GamePiece class. This class represents cells in Conway's Game of Life. 
 * In each generation of the game, a Cell can be either alive or dead, which are the only two allowable states. 
 * The methods in this class manipulate the states of a Cell.
 */

/**
 * @author Yuying Fan
 */
public class Cell extends GamePiece {
	
	public static final int ALIVE = 1;
	public static final int DEAD = 0;

	/**
	 * Constructs a Cell with value Cell.ALIVE (a living Cell) or Cell.DEAD (a dead Cell). 
	 * If any value other than Cell.ALIVE or Cell.DEAD is received, the Cell constructed is dead. 
	 * 
	 * @param value a numerical value representing the state of the Cell; can be either Cell.ALIVE or Cell.DEAD
	 */
	public Cell(int value) {
		super(value);
		if (super.getValue() != ALIVE && super.getValue() != DEAD) {
			super.changeState(DEAD);
		}
	}
	
	/**
	 * @return a String representing this Cell: "alive" if the Cell is alive, and "dead" if the Cell is dead.
	 */
	@Override
	public String toString() {
		if (super.getValue() == Cell.ALIVE) {
			return "alive";
		}
		else {
			return "dead";
		}
	}
	
	/**
	 * @return a clone of this Cell with the same state
	 */
	@Override
	public Cell clone() {
		return new Cell(this.getValue());
	}
	
	/**
	 * Determines whether this Cell is alive or dead
	 * @return true if this Cell is alive; false if this Cell is dead
	 */
	public boolean isAlive() {
		return super.getValue() == ALIVE;
	}
	
	/**
	 * Sets this Cell to be alive
	 */
	public void setAlive() {
		super.changeState(ALIVE);
	}
	
	/**
	 * Sets this Cell to be dead
	 */
	public void setDead() {
		super.changeState(DEAD);
	}
	
	/*
	 * Tests the methods in the GamePiece and Cell classes
	 */
	public static void main(String[] args) {
		Cell cell1 = new Cell(Cell.ALIVE);
		System.out.println("Cell 1 is created with the value Cell.ALIVE; it is " + cell1);
		cellTester(cell1);
		System.out.println();
		
		Cell cell2 = new Cell(Cell.DEAD);
		System.out.println("Cell 2 is created with the value Cell.DEAD; it is " + cell2);
		cellTester(cell2);
		System.out.println();
		
		Cell cell3 = new Cell(4);
		System.out.println("Cell 3 is created with the value 4; it is " + cell3);
		cellTester(cell3);
		System.out.println("\n");
	}
	
	/*
	 * A helper method to test methods in the GamePiece and Cell classes on one Cell
	 * 
	 * @param cell the Cell to test with
	 */
	public static void cellTester(Cell cell) {
		cell.changeState(ALIVE);
		System.out.println("\tChanging its value to Cell.ALIVE: it is now " + cell);
		System.out.println("\tThis cell has value " + cell.getValue());
		System.out.println("\tIs this Cell alive? " + cell.isAlive());
		System.out.println();
		
		cell.changeState(DEAD);
		System.out.println("\tChanging its value to Cell.DEAD: it is now " + cell);
		System.out.println("\tThis cell has value " + cell.getValue());
		System.out.println("\tIs this Cell alive? " + cell.isAlive());
		System.out.println();
		
		cell.setAlive();
		System.out.println("\tSetting this cell alive: it is now " + cell);
		System.out.println("\tThis cell has value " + cell.getValue());
		System.out.println("\tIs this Cell alive? " + cell.isAlive());
		System.out.println();
		
		cell.setDead();
		System.out.println("\tSetting this cell alive: it is now " + cell);
		System.out.println("\tThis cell has value " + cell.getValue());
		System.out.println("\tIs this Cell alive? " + cell.isAlive());
		System.out.println();
		
		System.out.println("\tA clone of this cell is a " + cell.clone() + " cell");
	}

}

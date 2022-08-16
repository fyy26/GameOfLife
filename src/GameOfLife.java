import java.util.Arrays;
import java.util.Random;

/**
 * This class represents Conway's Game of Life. Cells on the game board are specified by pairs of int coordinates x and y, 
 * with x being the horizontal index and y being the vertical index. The upper left corner is (0,0). 
 */

/**
 * @author Yuying Fan
 */
public class GameOfLife {

	private GameBoard board;
	private int gen;
	
	private static final int LONELINESS_MIN = 0;
	private static final int LONELINESS_MAX = 1;
	private static final int BIRTH_VALUE_MIN = 3;
	private static final int BIRTH_VALUE_MAX = 3;
	private static final int OVERCROWDING_MIN = 4;
	private static final int OVERCROWDING_MAX = 8;
	
	/**
	 * Constructs a new GameOfLife given an initial setup. Each null references in the 2D array of Cells received
	 * will be assigned to a dead Cell. The given 2D array of Cells must be a matrix with a size greater than 0.
	 * If a 2D array with size 0 is sent, a new default GameOfLife on a 20 * 20 square game board with all Cells 
	 * dead is constructed. 
	 * 
	 * @param initialSetup a 2D array of Cells that is the initial setup on the new game board
	 */
	public GameOfLife(Cell[][] initialSetup) {
		// If the input array is valid, do it.
		if (initialSetup.length > 0 && initialSetup[0].length > 0) {
			this.board = new GameBoard(initialSetup);
			// Set all null to dead Cells
			for (int i = 0; i < initialSetup[0].length; i++) {
				for (int j = 0; j < initialSetup.length; j++) {
					if (board.getPiece(i, j) == null) {
						board.setPiece(i, j, new Cell(Cell.DEAD));
					}
				}
			}
		}
		// Otherwise, construct the default game.
		else {
			this.board = new GameBoard(20, 20);
			clear();  // places a dead Cell on every spot
		}
		
		this.gen = 0;
	}
	
	/**
	 * Constructs a new GameOfLife on a rectangular board given a width and a height for the game board.
	 * The width and height must be positive integers. If non-positive values are received, 
	 * the game board has dimension 20 * 20. All cells on the new game board are dead by default.
	 * 
	 * @param width the width of the game board
	 * @param height the height of the game board
	 */
	public GameOfLife(int width, int height) {
		// If the width and height are both legal (positive integers), do it.
		if (width > 0 && height > 0) {
			this.board = new GameBoard(width, height);
			clear();
		}
		// If the dimension given is illegal, the game is on a default 20 * 20 board.
		else {
			this.board = new GameBoard(20, 20);
			clear();
		}
		
		// Generation starts at 0 for a new game
		this.gen = 0;
	}
	
	/**
	 * Constructs a new GameOfLife on a square game board given a width for the game board.
	 * The width must be a positive integer. If a non-positive value is received, 
	 * the game board has dimension 20 * 20. All cells on the new game board are dead by default.
	 * 
	 * @param width the width of the square game board
	 */
	public GameOfLife(int width) {
		this(width, width);
	}
	
	/**
	 * Constructs a new GameOfLife on a game board with the default dimension 20 * 20.
	 * All cells on the new game board are dead by default.
	 */
	public GameOfLife() {
		this(20);
	}
	
	/**
	 * @return a String representation of the game at this moment
	 */
	public String toString() {
		return board.toString();
	}
	
	/**
	 * @return the width of the game board
	 */
	public int getWidth() {
		return board.getWidth();
	}
	
	/**
	 * @return the height of the game board
	 */
	public int getHeight() {
		return board.getHeight();
	}
	
	/**
	 * @return the current generation number of this game of life
	 */
	public int getGen() {
		return gen;
	}
	
	/**
	 * Determines whether or not the cell at a particular location specified by a pair of int coordinates is alive.
	 * If the location specified is not on the game board, returns false.
	 * 
	 * @param x the x-coordinate of the Cell of interest
	 * @param y the y-coordinate of the Cell of interest 
	 * @return boolean true if the Cell is alive, and false if not. 
	 */
	public boolean isAlive(int x, int y) {
		if (board.onBoard(x, y)) {
			return ((Cell) board.getPiece(x, y)).isAlive();
		}
		else {
			return false;
		}
	}
	
	/**
	 * Sets the state of the cell to alive or dead at the location specified by a coordinate pair.
	 * The only allowable values for state are Cell.ALIVE and Cell.DEAD. If an invalid value is received, the Cell is left unchanged. 
	 * If the location specified is not on the board, this method performs no action. 
	 * 
	 * @param x the x-coordinate of the Cell to be set
	 * @param y the y-coordinate of the Cell to be set
	 * @param state the state to set the Cell to; the only valid values are Cell.ALIVE and Cell.DEAD
	 */
	public void setCell(int x, int y, int state) {
		if (board.onBoard(x, y)) {
			if (state == Cell.ALIVE) {
				board.setPiece(x, y, new Cell(Cell.ALIVE));
				}
			else if (state == Cell.DEAD) {
				board.setPiece(x, y, new Cell(Cell.DEAD));
			}
		}
	}
	
	/**
	 * Sets the Cell at the location specified by a pair of coordinates alive.
	 * If the location specified is not on the board, this method performs no action. 
	 * 
	 * @param x the x-coordinate of the Cell to be set
	 * @param y the y-coordinate of the Cell to be set 
	 */
	public void setAlive(int x, int y) {
		setCell(x, y, Cell.ALIVE);
	}
	
	/**
	 * Sets the Cell at the location specified by a pair of coordinates dead.
	 * If the location specified is not on the board, this method performs no action. 
	 * 
	 * @param x the x-coordinate of the Cell to be set
	 * @param y the y-coordinate of the Cell to be set
	 */
	public void setDead(int x, int y) {
		setCell(x, y, Cell.DEAD);
	}
	
	/**
	 * Alters the state of the Cell at the location specified by a pair of coordinates from alive to dead or from dead to alive.
	 * If the location specified is not on the board, this method performs no action. 
	 * 
	 * @param x the x-coordinate of the Cell to be changed
	 * @param y the y-coordinate of the Cell to be changed
	 */
	public void changeState(int x, int y) {
		if (board.onBoard(x, y)) {
			if (isAlive(x, y)) {
				setDead(x, y);
			}
			else {
				setAlive(x, y);
			}
		}
	}
	
	/**
	 * Clears the game board by setting all cells dead, and sets the generation number to 0.
	 */
	public void clear() {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				setDead(i, j);
			}
		}
		
		gen = 0;
	}
	
	/**
	 * Finds the number of living neighbors the Cell at the location specified by the coordinate pair has.
	 * A neighbor is an occupant of an adjacent square to the left, right, top, or bottom or in diagonal direction. 
	 * 
	 * Returns -1 if the location is not on the game board. 
	 * 
	 * @param x the x-coordinate of the Cell of interest
	 * @param y the y-coordinate of the Cell of interest
	 * @return the number of living neighbors this Cell has
	 */
	public int countLivingNeighbors(int x, int y) {
		if (!board.onBoard(x, y)) {
			return -1;
		}
		
		int livingNeighbors = 0;
		
		if (isAlive(x - 1, y - 1)) {
			livingNeighbors++;
		}
		if (isAlive(x, y - 1)) {
			livingNeighbors++;
		}
		if (isAlive(x + 1, y - 1)) {
			livingNeighbors++;
		}
		if (isAlive(x - 1, y)) {
			livingNeighbors++;
		}
		if (isAlive(x + 1, y)) {
			livingNeighbors++;
		}
		if (isAlive(x - 1, y + 1)) {
			livingNeighbors++;
		}
		if (isAlive(x, y + 1)) {
			livingNeighbors++;
		}
		if (isAlive(x + 1, y + 1)) {
			livingNeighbors++;
		}
		
		return livingNeighbors;
	}
	
	/**
	 * Determines whether the Cell at the location specified by the coordinate pair will be alive in the next generation.
	 * 
	 * A new Cell is born on an empty square if it is surrounded by exactly three occupied neighbor Cells. 
	 * A Cell dies of overcrowding if it is surrounded by four or more neighbors, and it dies of loneliness if it is 
	 * surrounded by zero or one neighbor. 
	 * 
	 * @param x the x-coordinate of the Cell of interest
	 * @param y the y-coordinate of the Cell of interest
	 * @return whether the Cell at the location given will be alive in the next generation
	 */
	public boolean willBeAlive(int x, int y) {
		int livingNeighbors = countLivingNeighbors(x, y);
		
		// If overcrowded or too lonely, die:
		if (OVERCROWDING_MIN <= livingNeighbors && livingNeighbors <= OVERCROWDING_MAX || 
			LONELINESS_MIN <= livingNeighbors && countLivingNeighbors(x, y) <= LONELINESS_MAX) {
			return false;
		}
		
		//If the number of neighbors is one of the birth values, live:
		if (BIRTH_VALUE_MIN <= livingNeighbors && livingNeighbors <= BIRTH_VALUE_MAX) {
			return true;
		}
		
		return isAlive(x, y);  // The value would be a "no action value"
	}
	
	/**
	 * Updates the states of all Cells to the next generation and increment the generation number by 1.
	 */
	public void nextGen() {
//		Make a new GameBoard and copy! Otherwise, the Cells are not updated simultaneously and former updates will affect 
//		the calculation for other Cells in the same generation, causing inaccurate results. 
		
		GameBoard nextGen = new GameBoard(getWidth(), getHeight());
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (willBeAlive(i, j)) {
					nextGen.setPiece(i, j, new Cell(Cell.ALIVE));
				}
				else {
					nextGen.setPiece(i, j, new Cell(Cell.DEAD));
				}
			}
		}
		
		board = nextGen;
		
		gen++;
	}
	
	/**
	 * @return the number of living Cells currently on the game board.
	 */
	public int countLivingCells() {
		int livingCells = 0;
		
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (isAlive(i, j)) {
					livingCells++;
				}
			}
		}
		
		return livingCells;
	}
	
	/**
	 * Sets the game board to a random setup, and resets the generation number to 0.
	 */
	public void randomSetup() {
		Random generator = new Random();
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (generator.nextBoolean()) {
					setAlive(i, j);
				}
				else {
					setDead(i, j);
				}
			}
		}
		
		gen = 0;
	}
	
	/*
	 * Tests methods from the GameOfLife class
	 */
	public static void main(String[] args) {
		Cell[][] setup1 = new Cell[8][4];
		GameOfLife game1 = new GameOfLife(setup1);
		System.out.println("A new Game of Life with initial setup \n\"" + Arrays.deepToString(setup1) + "\" \n is: \n" + game1);
		GameTester(game1);
		System.out.println("\n");
		
		Cell[][] setup2 = new Cell[6][6];
		for (int i = 0; i < setup2.length; i++) {
			for (int j = 0; j < setup2[0].length; j++) {
				if (i % 2 == 0) {
					setup2[i][j] = new Cell(Cell.DEAD);
				}
				else {
					setup2[i][j] = new Cell(Cell.ALIVE);
				}
			}
		}
		GameOfLife game2 = new GameOfLife(setup2);
		System.out.println("A new Game of Life with initial setup \n\"" + Arrays.deepToString(setup2) + "\" \n looks like: \n" + game2);
		GameTester(game2);
		System.out.println("\n");
		
		GameOfLife game3 = new GameOfLife(5, 10);
		System.out.println("A new Game of Life with board dimension 5 * 10 looks like: \n" + game3);
		GameTester(game3);
		System.out.println("\n");
		
		GameOfLife game4 = new GameOfLife(7, 3);
		System.out.println("A new Game of Life with board dimension 7 * 3 looks like: \n" + game4);
		GameTester(game4);
		System.out.println("\n");
		
		GameOfLife game5 = new GameOfLife(3);
		System.out.println("A new Game of Life on a square board with side length 3 looks like: \n" + game5);
		GameTester(game5);
		System.out.println("\n");
		
		GameOfLife game6 = new GameOfLife(1);
		System.out.println("A new Game of Life on a square board with side length 1 looks like: \n" + game6);
		GameTester(game6);
		System.out.println("\n");
		
		GameOfLife game7 = new GameOfLife();
		System.out.println("A default Game of Life looks like: \n" + game7);
		GameTester(game7);
		System.out.println("\n");
		
		Cell[][] empty1 = new Cell[0][0];
		System.out.println("Receives a 0 * 0 2D array " + Arrays.deepToString(empty1) + ", ");
		GameOfLife game8 = new GameOfLife(empty1);
		System.out.println("constructs a default Game of Life on a 20 * 20 game board: \n" + game8);
		System.out.println("\n");
		
		Cell[][] empty2 = new Cell[10][0];
		System.out.println("Receives a 10 * 0 2D array " + Arrays.deepToString(empty2) + ", ");
		GameOfLife game9 = new GameOfLife(empty2);
		System.out.println("constructs a default Game of Life on a 20 * 20 game board: \n" + game9);
		System.out.println("\n");
		
		Cell[][] empty3 = new Cell[0][10];
		System.out.println("Receives a 0 * 10 2D array " + Arrays.deepToString(empty3) + ", ");
		GameOfLife game10 = new GameOfLife(empty3);
		System.out.println("constructs a default Game of Life on a 20 * 20 game board: \n" + game10);
		System.out.println("\n");
		
		GameOfLife game11 = new GameOfLife(-1, 0);
		System.out.println("Receives invalid dimension -1 * 0, \nconstructs a defualt Game of Life: \n" + game11);
		System.out.println("\n");
		
		GameOfLife game12 = new GameOfLife(-1);
		System.out.println("Receives invalid side length -1, \nconstructs a defualt Game of Life: \n" + game12);
		System.out.println("\n");
	
	}
	
	/*
	 * A helper method to test methods from the GameOfLife class with one GameOfLife object
	 * 
	 * @param game the GameOfLife to test with
	 */
	public static void GameTester(GameOfLife game) {
		System.out.println("The width of the game board is " + game.getWidth());
		System.out.println("The height of the game board is " + game.getHeight());
		System.out.println();
		
		System.out.println("The game is now at generation " + game.getGen());
		
		System.out.print("The Cell at (0,0) is ");
		if (game.isAlive(0, 0)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.print("The Cell at (0,1) is ");
		if (game.isAlive(0, 1)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.print("The Cell at (200,-200) is ");
		if (game.isAlive(200, -200)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.println("The Cell at (0,0) is set to be alive with setCell().");
		game.setCell(0, 0, Cell.ALIVE);
		System.out.print("The Cell at (0,0) is now ");
		if (game.isAlive(0, 0)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.println("The Cell at (0,1) is set to be dead with setCell().");
		game.setCell(0, 0, Cell.DEAD);
		System.out.print("The Cell at (0,1) is now ");
		if (game.isAlive(0, 1)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.println("The Cell at (0,0) is set to be dead with setDead().");
		game.setDead(0, 0);
		System.out.print("The Cell at (0,0) is now ");
		if (game.isAlive(0, 0)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.println("The Cell at (0,1) is set to be alive with setAlive().");
		game.setAlive(0, 1);
		System.out.print("The Cell at (0,1) is now ");
		if (game.isAlive(0, 1)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.println("The Cell at (0,0) should change state.");
		game.changeState(0, 0);
		System.out.print("The Cell at (0,0) is now ");
		if (game.isAlive(0, 0)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.println("The Cell at (0,1) should change state.");
		game.changeState(0, 1);
		System.out.print("The Cell at (0,1) is now ");
		if (game.isAlive(0, 1)) {
			System.out.println("alive");
		}
		else {
			System.out.println("dead");
		}
		
		System.out.println();
		
		System.out.println("The board now looks like: ");
		System.out.println(game);
		System.out.println("There is/are " + game.countLivingCells() + " living cell(s) on the board right now.");
		
		System.out.println();
		
		game.setAlive(0, 1);
		game.setAlive(1, 1);
		game.setAlive(1, 0);
		game.setAlive(1, 2);
		game.setAlive(2, 2);
		game.setAlive(2, 1);
		System.out.println("The game board is now set to be\n" + game);
		
		System.out.println("The Cell at (0,0) has " + game.countLivingNeighbors(0, 0) + " living neighbors");
		System.out.print("The Cell at (0,0) will be ");
		if (game.willBeAlive(0,0)) {
			System.out.println("alive in the next generation");
		}
		else {
			System.out.println("dead in the next generation");
		}
		
		System.out.println("The Cell at (0,1) has " + game.countLivingNeighbors(0, 1) + " living neighbors");
		System.out.print("The Cell at (0,1) will be ");
		if (game.willBeAlive(0,1)) {
			System.out.println("alive in the next generation");
		}
		else {
			System.out.println("dead in the next generation");
		}
		
		System.out.println();
		game.nextGen();
		System.out.println("The game proceeds to the next generation.\nThe game is now at generation " + game.getGen() + ".");
		System.out.println("The game board now looks like:\n" + game);
		
		System.out.println();
		game.nextGen();
		System.out.println("The game proceeds to the next generation.\nThe game is now at generation " + game.getGen() + ".");
		System.out.println("The game board now looks like:\n" + game);
		
		System.out.println();
		game.nextGen();
		System.out.println("The game proceeds to the next generation.\nThe game is now at generation " + game.getGen() + ".");
		System.out.println("The game board now looks like:\n" + game);
		
		System.out.println();
		game.clear();
		System.out.println("The game board is now cleared.\n" + game);
		System.out.println("The game is now at generation " + game.getGen());
		
		System.out.println();
		game.randomSetup();
		System.out.println("The game board is given a random setup.\n" + game);
		System.out.println("The game is now at generation " + game.getGen());
	}

}

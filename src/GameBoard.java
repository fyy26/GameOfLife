import java.util.Arrays;

/**
 * The GameBoard class represents a 2D game board. Its implementation is coupled with the GamePiece class to be used for 
 * creating various board games. Locations on a GameBoard are specified by pairs of int coordinates x and y, with x being the 
 * horizontal index and y being the vertical index. The upper left corner is location (0,0). The dimension of each GameBoard is 
 * immutable once the GameBoard is instantiated; if a new dimension is needed, a new GameBoard needs to be constructed. 
 */

/**
 * @author Yuying Fan
 */
public class GameBoard {
	
	private GamePiece[][] board;
	private int width, height;

	/**
	 * Constructs a rectangular GameBoard given a specified width and a specified height.
	 * The width and height must be positive integers. If non-positive values are received, 
	 * the board constructed has dimension 8 * 8. All elements in the board constructed are null.
	 * 
	 * @param width the width of the new game board
	 * @param height the height of the new game board
	 */
	public GameBoard(int width, int height) {
		if (width > 0 && height > 0) {
			this.board = new GamePiece[height][width];  
			// In the newly constructed board, all GamePiece objects in the 2D array are initialized to null 
			this.width = width;
			this.height = height;
		}
		else {
			this.board = new GamePiece[8][8];
			this.width = 8;
			this.height = 8;
		}
	}
	
	/**
	 * Constructs a square GameBoard given a specified width. The width must be a positive integer. 
	 * If a non-positive value is received, the board constructed has dimension 8 * 8.
	 * All elements in the board constructed are null.
	 * 
	 * @param width the width of the new square game board
	 */
	public GameBoard(int width) {
		this(width, width);
	}
	
	/**
	 * Constructs a rectangular GameBoard given a 2D array of GamePieces that is a matrix (i.e. width and height are the same)
	 * with size greater than 0. If a 2D array with size 0 is received, the board constructed has dimension 8 * 8 with all 
	 * elements being null.
	 * 
	 * @param board the initial setup of the new game board as a 2D array of GamePieces
	 */
	public GameBoard(GamePiece[][] board) {
		if (board.length > 0 && board[0].length > 0) {   // Checks if the 2D array received has a non-zero size
			this.width = board[0].length; 
			this.height = board.length;
			
		// Make a clone and assign the clone into the private field, so that the user does not gain indirect access to it. 
			GamePiece[][] boardClone = new GamePiece[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (board[i][j] != null) {
						boardClone[i][j] = board[i][j].clone();
					}
				}
			}
			this.board = boardClone;  
		}
		else {
			this.board = new GamePiece[8][8];
			this.width = 8;
			this.height = 8;
		}
	}
	
	/**
	 * @return the String representation of this GameBoard generated based on the value of each GamePiece on the board.
	 */
	public String toString() {
//		return Arrays.deepToString(board);  // this puts all rows on the same line 
		String out = "";
		for (int i = 0; i < board.length; i++) {
			if (i != board.length - 1) {
				out += Arrays.toString(board[i]) + "\n";  // places each row on a new line
			}
			else {
				out += Arrays.toString(board[i]);   // no new line after the last row 
			}
		}
		return out;
	}
	
	/**
	 * @return the width of this GameBoard
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return the height of this GameBoard
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Clears this GameBoard by setting all GamePieces on the board to null.
	 */
	public void clear() {
		board = new GamePiece[height][width];
	}
	
	/**
	 * @param x the x-coordinate of the location specified
	 * @param y the y-coordinate of the location specified
	 * @return whether the location specified is on this game board
	 */
	public boolean onBoard(int x, int y) {
		return 0 <= x && x < width && 0 <= y && y < height;
	}
	
	/**
	 * @param x the x-coordinate of the location specified
	 * @param y the y-coordinate of the location specified
	 * @return whether there is a GamePiece at the location specified
	 */
	public boolean hasPiece(int x, int y) {
		return onBoard(x, y) && getPiece(x, y) != null;
	}
	
	/**
	 * @param x the x-coordinate of the location specified
	 * @param y the y-coordinate of the location specified
	 * @return the GamePiece at the location specified, null if the location is empty or not on board
	 */
	public GamePiece getPiece(int x, int y) {
		if (onBoard(x, y)) {
			return board[y][x];
		}
		else {
			return null;
		}
	}
	
	/**
	 * Sets a specified GamePiece at a specific location on this GameBoard
	 * 
	 * @param x the x-coordinate of the location specified
	 * @param y the y-coordinate of the location specified
	 * @param piece the GamePiece to place on the location specified 
	 * @return the GamePiece replaced; null if the location is empty or not on board
	 */
	public GamePiece setPiece(int x, int y, GamePiece piece) {
		if (onBoard(x, y)) {
			GamePiece prev = board[y][x];
			board[y][x] = piece;
			return prev;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Removes the GamePiece at the location specified and set that location to null
	 * 
	 * @param x the x-coordinate of the location specified
	 * @param y the y-coordinate of the location specified
	 * @return the GamePiece removed; null if the location is empty or not on board
	 */
	public GamePiece removePiece(int x, int y) {
		return setPiece(x, y, null);
	}
	
	/**
	 * Tests the methods in the GameBoard class
	 */
	public static void main(String[] args) {
		GameBoard board1 = new GameBoard(5, 10);
		System.out.println("A rectangular GameBoard with dimension 5 * 10 is: \n" + board1);
		gameBoardTestser(board1);
		System.out.println();
		
		GameBoard board2 = new GameBoard(7, 3);
		System.out.println("A rectangular GameBoard with dimension 7 * 3 is: \n" + board2);
		gameBoardTestser(board2);
		System.out.println();
		
		GameBoard board3 = new GameBoard(3);
		System.out.println("A square GameBoard with side length 3 is: \n" + board3);
		gameBoardTestser(board3);
		System.out.println();
		
		GameBoard board4 = new GameBoard(1);
		System.out.println("A square GameBoard with side length 1 is: \n" + board4);
		gameBoardTestser(board4);
		System.out.println();
		
		Cell[][] setup1 = new Cell[8][4];
		GameBoard board5 = new GameBoard(setup1);
		System.out.println("A GameBoard with initial setup \n\"" + Arrays.deepToString(setup1) + "\" \nis: \n" + board5);
		gameBoardTestser(board5);
		System.out.println();
		
		Cell[][] setup2 = new Cell[6][6];
		for (int i = 0; i < setup2.length; i++) {
			for (int j = 0; j < setup2[0].length; j++) {
				setup2[i][j] = new Cell(Cell.DEAD);
			}
		}
		GameBoard board6 = new GameBoard(setup2);
		System.out.println("A GameBoard with initial setup \n\"" + Arrays.deepToString(setup2) + "\" \nis: \n"+ board6);
		gameBoardTestser(board6);
		System.out.println();
		
		GameBoard board7 = new GameBoard(-1, 0);
		System.out.println("Receives invalid dimension -1 * 0, \nconstructs an 8 * 8 default GameBoard: \n" + board7);
		gameBoardTestser(board7);
		System.out.println();
		
		GameBoard board8 = new GameBoard(-1);
		System.out.println("Receives invalid side length -1 for square board, \nconstructs an 8 * 8 default GameBoard: \n" + board8);
		gameBoardTestser(board8);
		System.out.println();
		
		Cell[][] empty1 = new Cell[0][0];
		System.out.println("Receives a 0 * 0 2D array " + Arrays.deepToString(empty1) + ", ");
		GameBoard board9 = new GameBoard(empty1);
		System.out.println("constructs an 8*8 default GameBoard: \n" + board9);
		gameBoardTestser(board9);
		System.out.println();
		
		Cell[][] empty2 = new Cell[10][0];
		System.out.println("Receives a 10 * 0 2D array " + Arrays.deepToString(empty2) + ", ");
		GameBoard board10 = new GameBoard(empty2);
		System.out.println("constructs an 8*8 default GameBoard: \n" + board10);
		gameBoardTestser(board10);
		System.out.println();
		
		Cell[][] empty3 = new Cell[0][10];
		System.out.println("Receives a 0 * 10 2D array " + Arrays.deepToString(empty3) + ", ");
		GameBoard board11 = new GameBoard(empty3);
		System.out.println("constructs an 8*8 default GameBoard: \n" + board11);
		gameBoardTestser(board11);
		System.out.println();
		
	}
	
	/**
	 * A helper method to test methods in the GameBoard class on one GameBoard object
	 * 
	 * @param board the GameBoard to test with
	 */
	public static void gameBoardTestser(GameBoard board) {
		System.out.println("Its width is " + board.getWidth());
		System.out.println("Its height is " + board.getHeight());
		
		System.out.println("Is (0,0) on this board? " + board.onBoard(0, 0));
		System.out.println("Is (5,4) on this board? " + board.onBoard(5, 4));
		System.out.println("Is (20,20) on this board? " + board.onBoard(20, 20));
		
		System.out.println("Is there a cell at (0,0)? " + board.hasPiece(0, 0));
		if (!board.hasPiece(0, 0)) {
			board.setPiece(0, 0, new Cell(Cell.ALIVE));
		}
		System.out.println("If there is not a piece at (0,0), an alive cell is placed there. ");
		System.out.println("The cell at (0,0) is " + board.getPiece(0, 0));
		
		System.out.println("Is there a cell at (5,4)? " + board.hasPiece(5, 4));
		if (board.onBoard(5, 4) && !board.hasPiece(5, 4)) {
			System.out.println("(5,4) is on the board and there is not a piece at (5,4), so a dead cell is placed there. ");
			board.setPiece(5, 4, new Cell(Cell.DEAD));
			System.out.println("The cell at (5,4) is " + board.getPiece(5, 4));
		}
		
		if (board.onBoard(1, 2)) {
			board.setPiece(1, 2, new Cell(Cell.ALIVE));
			System.out.println("(1,2) is on this board, and an alive cell is placed there, replacing what existed before");
			System.out.println("The cell at (1,2) is now " + board.getPiece(1, 2));
		}
		
		board.removePiece(0, 0);
		System.out.println("The cell at (0,0) is removed.");
		System.out.println("At (0,0) there is now " + board.getPiece(0, 0));
		
		System.out.println("The board now looks like: ");
		System.out.println(board);
		
		board.clear();
		System.out.println("The board is cleared. \nIt now looks like: ");
		System.out.println(board);
		
	}

}

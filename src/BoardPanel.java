import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * A class that extends the JPanel class, adding the functionality of painting the current generation of a Game of Life.
 */
public class BoardPanel extends JPanel{
	private GameOfLife game;
	private int gridWidth, gridHeight;
	
	public BoardPanel(GameOfLife game, int gridWidth, int gridHeight){
		this.game = game;
		this.gridWidth = gridWidth; 
		this.gridHeight = gridHeight;
	}
	
	/**
	 * Paints the current state of the Game of Life board onto this panel. This method is invoked for you each time you
	 * call repaint() on either this object or on the JFrame upon which this panel is placed.
	 */
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		
		for (int i = 0; i < game.getWidth(); i++) {
			for (int j = 0; j < game.getHeight(); j++) {
				g2.drawRect(i * gridWidth, j * gridHeight, gridWidth, gridHeight);  // Draws the grids
				if (game.isAlive(i, j)) {
					g2.fillRect(i * gridWidth, j * gridHeight, gridWidth, gridHeight); // Paints in alive cells
				}
			}
		}
	}

	/**
	 * @return the width of one rectangular grid
	 */
	public int getGridWidth() {
		return gridWidth;
	}

	/**
	 * @param gridWidth the new grid width to set to
	 */
	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	/**
	 * @return the height of one rectangular grid
	 */
	public int getGridHeight() {
		return gridHeight;
	}

	/**
	 * @param gridHeight the new grid height to set to
	 */
	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

}

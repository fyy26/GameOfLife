import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import java.awt.GridLayout;
import java.awt.FlowLayout;

/**
 * Displays generations of John Conway's Game of Life.
 * Allows a user of the program to step through one generation at a time or to run the generations based on a timer.
 */
public class GameOfLifeDisplay extends JFrame {

	private JPanel contentPane;
	private JLabel txtGeneration = new JLabel();
	private JLabel txtCellsAlive = new JLabel();
	private GameOfLife game;
	private static final int GAME_WIDTH_MIN = 1, GAME_WIDTH_MAX = 1000, GAME_WIDTH_DEFAULT = 50;
	private static final int GAME_HEIGHT_MIN = 1, GAME_HEIGHT_MAX = 1000, GAME_HEIGHT_DEFAULT = 30;
	private static final int GRID_WIDTH_DEFAULT = 15, GRID_HEIGHT_DEFAULT = 15;

	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameOfLifeDisplay frame = new GameOfLifeDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Create the frame. Adds a button panel to the frame and initializes the usage of each button.
	 */
	public GameOfLifeDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, GAME_WIDTH_DEFAULT * GRID_WIDTH_DEFAULT, GAME_HEIGHT_DEFAULT * GRID_HEIGHT_DEFAULT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/*
		 * Creates the top and bottom button panels
		 */
		JPanel buttonPanelTop = new JPanel();
		contentPane.add(buttonPanelTop, BorderLayout.NORTH);
		JPanel buttonPanelBottom = new JPanel();
		contentPane.add(buttonPanelBottom, BorderLayout.SOUTH);
		
		/*
		 * Constructs a new game of life with a default size.
		 */
		game = new GameOfLife(GAME_WIDTH_DEFAULT, GAME_HEIGHT_DEFAULT); 
		
		/*
		 * Adds the panel which displays the Game of Life board. See the BoardPanel class for details.
		 */
		JPanel boardPanel = new BoardPanel(game, GRID_WIDTH_DEFAULT, GRID_HEIGHT_DEFAULT);
		contentPane.add(boardPanel, BorderLayout.CENTER);
		
		/*
		 * Resizes the grids every time the window is resized so that the game board always fills the panel.
		 */
		boardPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				((BoardPanel) boardPanel).setGridWidth(boardPanel.getWidth() / game.getWidth());
				((BoardPanel) boardPanel).setGridHeight(boardPanel.getHeight() / game.getHeight());
				repaint();
			}
		});
		
		/*
		 * Creates a Timer and defines what will occur when it is run when the user clicks the "start" button
		 */
		Timer timer = new Timer(700, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.nextGen();
				txtGeneration.setText("Generation " + game.getGen());
				txtCellsAlive.setText("Cells Alive: " + game.countLivingCells());
				repaint();
			}
			
		});
		
		/*
		 * Adds a button which allows the user to set or reset the board to a random setup
		 */
		JButton btnRandomize = new JButton("Randomize");
		btnRandomize.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.randomSetup(); 
				txtGeneration.setText("Generation " + game.getGen());
				txtCellsAlive.setText("Cells Alive: " + game.countLivingCells());
				repaint();
			}
		});
		buttonPanelTop.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanelTop.add(btnRandomize);
		
		/*
		 * Adds a button which allows the user to step through the game one generation at a time
		 */
		JButton nextGenButton = new JButton("Next Gen");
		nextGenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.nextGen();  // The game goes to the next generation
				txtGeneration.setText("Generation " + game.getGen());  // Updates the generation number label 
				txtCellsAlive.setText("Cells Alive: " + game.countLivingCells());  // Updates the number of cells alive on the board
				repaint();
			}
		});	
		buttonPanelTop.add(nextGenButton);
		
		/*
		 * Creates a button that allows the game to run on a timer. The label toggles between "Start" and "Stop"
		 */
		JButton startStopButton = new JButton("Start");
		startStopButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(startStopButton.getText().equals("Start")){
					startStopButton.setText("Stop");
					timer.start();
				}
				else{
					startStopButton.setText("Start");
					timer.stop();
				}
			}
		});
		buttonPanelTop.add(startStopButton);
		buttonPanelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblSpeed = new JLabel("Speed");
		buttonPanelBottom.add(lblSpeed);
		
		JSlider sldSpeed = new JSlider(10, 1400, 710);
		sldSpeed.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				sldSpeed.setValue(sldSpeed.getValue() - e.getWheelRotation() * 10);
			}
		});
		sldSpeed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				timer.setDelay(1410 - sldSpeed.getValue());
			}
		});
		buttonPanelBottom.add(sldSpeed);
		
		/*
		 * Creates a button that clears the game board and sets the generation to 0, so that a new game can be played.
		 * The button also stops the current game if it is running.
		 */
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timer.stop();
				game.clear();
				txtGeneration.setText("Generation 0"); 
				txtCellsAlive.setText("Cells Alive: 0");
				startStopButton.setText("Start");
				repaint();
			}
		});
		buttonPanelTop.add(btnClear);
		
		/*
		 * Displays the generation number
		 */
		txtGeneration.setText("Generation 0");
		buttonPanelTop.add(txtGeneration);
		
		/*
		 * Displays the number of cells alive
		 */
		txtCellsAlive.setText("Cells Alive: 0");
		buttonPanelTop.add(txtCellsAlive);
		
		/*
		 * Allows the user to toggle the state of a Cell on the game board with a mouse click
		 */
		boardPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				game.changeState(e.getX() / ((BoardPanel) boardPanel).getGridWidth(), e.getY() / ((BoardPanel) boardPanel).getGridHeight());
				txtCellsAlive.setText("Cells Alive: " + game.countLivingCells());
				repaint();
			}
		});
		
	}

}

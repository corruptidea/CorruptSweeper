package corruptsweeper;

// Event listeners
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Swing classes
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

// Image and image management classes
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// Other
import java.awt.Point;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class SweeperFrame extends JFrame implements ActionListener {
	
	// Define characteristics of the frame
	private static final String TITLE = "CorruptSweeper";
	private final int MAP_WIDTH = 310;
	private final int MAP_LENGTH = 318;
	private final Dimension FRAME_SIZE = new Dimension(MAP_WIDTH, MAP_LENGTH + 55);
	private static final Point DEFAULT_LOCATION = new Point(0, 0);
	private final BufferedImage SPLASH_SCREEN = getSplash();
	
	// Define swing elements of the frame
	private JPanel mainPanel;	
	private JPanel buttonPanel;
	private JButton findMap;
	private JLabel mapLabel;	
	
	// Variables used to capture, update, and store the map and its location
	private MapFinder mapFinder = new MapFinder();
	private Point mapLoc;

	/**
	 * Default constructor
	 */
	public SweeperFrame() {
		// Sets frame dimensions, location, default close operation, always on top, and resizability
		setTitle(TITLE);
		setSize(FRAME_SIZE);
		setLocation(DEFAULT_LOCATION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		setAlwaysOnTop(true);
		setResizable(false);
		setUndecorated(true);
		
		// Adds "find map" and "update map" buttons
		buttonPanel = new JPanel();
		
		findMap = new JButton("Find Map");
		findMap.setActionCommand("find");
		findMap.addActionListener(this);
		buttonPanel.add(findMap);
								
		// Adds JLabel to display map
		mapLabel = new JLabel(new ImageIcon(SPLASH_SCREEN));
		
		// Adds panels to frame
		mainPanel.add(mapLabel);
		mainPanel.add(buttonPanel);
		add(mainPanel);
		
		// Shows the frame
		setVisible(true);
	}
	
	/**
	 * updates and returns the location of the map
	 * @return the location of the map
	 */
	public Point getMapLoc() {
		mapLoc = mapFinder.findMap();
		return mapLoc;
	}	
	
	/**
	 * 
	 */
	public void updateMap(ImageIcon i) {
		mapLabel.setIcon(i);
		repaint();
	}

	/**
	 * ActionListener interface methods
	 */	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("find")) {
			try {
				mapLoc = mapFinder.findMap();
			}
			catch (NullPointerException npe) {
				// Map not found
			}
		}
	}
	
	/**
	 * Gets the default splash screen to be displayed before map is first opened
	 * @return the default splash screen
	 */
	public BufferedImage getSplash() {		
		try {
			return ImageIO.read(getClass().getResource("/resources/CorruptSweeperSplash.png"));
		}
		// If resource file is not an image
		catch(IOException e) {
			System.out.println("Cannot read input file");
			e.printStackTrace();
		}
		return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
	}
	

		
}
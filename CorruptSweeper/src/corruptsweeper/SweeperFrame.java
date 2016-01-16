package corruptsweeper;

// Event listeners
// TODO - Define custom event listeners for map appearing on screen
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


// Swing classes
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

// Image and image management classes
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// Other
import java.awt.Point;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class SweeperFrame extends JFrame implements MouseListener {
	
	private static final String TITLE = "CorruptSweeper";
	private final int MAP_LENGTH = 322;
	private final int MAP_WIDTH = 341;
	private final Dimension FRAME_SIZE = new Dimension(MAP_LENGTH, MAP_WIDTH);
	private static final Point DEFAULT_LOCATION = new Point(0, 0);
	
	// Gets the default splash screen
	private final BufferedImage SPLASH_SCREEN = getSplash();
	
	MapCapturer mapCapturer = new MapCapturer();
	private JLabel mapLabel = new JLabel(new ImageIcon(SPLASH_SCREEN));  
	
	/**
	 * Default constructor
	 */
	public SweeperFrame() {
		// Sets frame dimensions, location, default close operation, always on top, and resizability
		setTitle(TITLE);
		setSize(FRAME_SIZE);
		setLocation(DEFAULT_LOCATION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		
		//Adds JLabel to display map
		add(mapLabel);
		mapLabel.addMouseListener(this);
		
		// Sets frame visibility to true
		setVisible(true);		
	}
	
	/**
	 * Non-default constructor - allows for custom title, size, and location
	 * TODO - Add config file to manage custom variables
	 * @param title the frame title
	 * @param size the frame size
	 * @param location the frame location on the screen
	 */
	public SweeperFrame(String title, Dimension size, Point location) {
		// Sets frame dimensions, location, default close operation, always on top, and resizability
		setTitle(title);
		setSize(size);
		setLocation(location);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		
		//Adds JLabel to display map
		add(mapLabel);
		mapLabel.addMouseListener(this);
		
		// Sets frame visibility to true
		setVisible(true);		
	}

	/**
	 * Gets the default splash screen to be displayed before map is first opened
	 * @return the default splash screen
	 */
	public BufferedImage getSplash() {		
		try {
			return ImageIO.read(getClass().getResource("/resources/CorruptSweeperSplash.png"));
		}
		// If resource file is not an image and/or is corrupt
		catch(IOException e) {
			System.out.println("Cannot read input file");
			e.printStackTrace();
		}
		// If getClass.getResource(resource path) returns null
		catch(NullPointerException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
	}
	
	/**
	 * MouseListener interface methods
	 * TODO - Use custom EventListeners 
	 */	
	@Override
	public void mousePressed(MouseEvent e) {
		mapLabel.setIcon(new ImageIcon(mapCapturer.getMap()));
		repaint();		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Nothing required, implementation required by MouseListener interface		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Nothing required, implementation required by MouseListener interface	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Nothing required, implementation required by MouseListener interface
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Nothing required, implementation required by MouseListener interface
	}
}
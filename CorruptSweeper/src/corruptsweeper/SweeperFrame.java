/**
 * The SweeperFrame class displays the captured map
 * 
 *  TODO Allow size to be adjusted
 *  @author Jack Pergantis
 */

package corruptsweeper;

// Event listeners
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

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
import java.awt.Image;

// Sikuli
import org.sikuli.api.*;

// JNativeHook
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;

@SuppressWarnings("serial")
public class SweeperFrame extends JFrame implements ActionListener, NativeKeyListener, NativeMouseListener {
	
	// Define characteristics of the frame
	private final String TITLE = "CorruptSweeper";
	private final int MAP_WIDTH = 310;
	private final int MAP_LENGTH = 318;
	private final Dimension FRAME_SIZE = new Dimension(MAP_WIDTH, MAP_LENGTH + 70);
	private final Point DEFAULT_LOCATION = new Point(0, 0);
	private final BufferedImage SPLASH_SCREEN = getSplash();
	
	// Define swing elements of the frame
	private JPanel mainPanel;	
	private JPanel mapPanel;
	private JPanel buttonPanel;
	private JButton findMap;
	private JLabel mapLabel;	
	
	// Define variables used to capture, update, and store the map and its location
	private MapFinder mapFinder;
	private MapCapturer mapCapturer;
	private Point mapLoc;
	private Point mapXLoc;
	private ScreenRegion searchable;
	private Target mapX;
	
	/**
	 * Default constructor
	 */
	public SweeperFrame() {
		
		 GlobalScreen.setEventDispatcher(new SwingDispatchService());
		
		try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();

            System.exit(1);
        }
		
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.addNativeMouseListener(this);
		
		// Sets frame dimensions, location, default close operation, always on top, and resizability
		setTitle(TITLE);
		setSize(FRAME_SIZE);
		setLocation(DEFAULT_LOCATION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		setAlwaysOnTop(true);
		setResizable(false);
		setUndecorated(false);
		
		// Adds "find map" button
		buttonPanel = new JPanel();
		
		findMap = new JButton("Find Map");
		findMap.setActionCommand("find");
		findMap.addActionListener(this);
		buttonPanel.add(findMap);
								
		// Adds JLabel to display map
		mapPanel = new JPanel();
		mapLabel = new JLabel(new ImageIcon(SPLASH_SCREEN));
		mapPanel.add(mapLabel);
		
		// Adds panels to frame
		mainPanel.add(mapPanel);
		mainPanel.add(buttonPanel);
		add(mainPanel);
		
		// Instantiates necessary utilities
		try {
			mapX = new ImageTarget(ImageIO.read(getClass().getResource("/resources/MapX.png"))); // Image target to search for when updating
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		mapFinder = new MapFinder();
		mapCapturer = new MapCapturer();
		
		// Shows the frame
		setVisible(true);
		
	}
	
	/**
	 * @return the location of the map
	 */
	public Point getMapLoc() {
		return mapLoc;
	}	
	
	/**
	 * @return the location of the x
	 */
	public Point getMapXLoc() {
		return mapXLoc;
	}
	
	/**
	 * updates the displayed image of the map
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
				mapXLoc = mapFinder.findMapX();
				searchable = new DesktopScreenRegion(mapXLoc.x, mapXLoc.y, 16, 16); // Square with x is 16 px on a side
			}
			catch (NullPointerException npe) {
				// Map not found
				// System.out.println("Map not found!");
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

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		// Nothing required, implementation required by NativeMouseListener interface	
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		try {
			Thread.sleep(600); // Waits 1 tick for map to appear
		} catch (InterruptedException e) {
			System.out.println("Sleep thread interrupted");
			e.printStackTrace();
		}
		if (searchable.find(mapX) != null) {
			updateMap(new ImageIcon(mapCapturer.getMap(getMapLoc()).getScaledInstance(getWidth() - 10, getHeight() - 75, Image.SCALE_FAST)));
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// Nothing required, implementation required by NativeMouseListener interface		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		try {
			Thread.sleep(600); // Waits 1 tick for map to appear
		} catch (InterruptedException e) {
			System.out.println("Sleep thread interrupted");
			e.printStackTrace();
		}
		if (searchable.find(mapX) != null) {
			updateMap(new ImageIcon(mapCapturer.getMap(getMapLoc())));
		}	
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// Nothing required, implementation required by NativeKeyListener interface
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// Nothing required, implementation required by NativeKeyListener interface		
	}
	

		
}
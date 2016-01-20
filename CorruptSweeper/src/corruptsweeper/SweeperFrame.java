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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

// Swing classes
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

// Image and image management classes
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// Other
import java.awt.Point;

import java.awt.Dimension;
import java.awt.LayoutManager;

// Sikuli
import org.sikuli.api.*;

// Other JNativeHook classes
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;

@SuppressWarnings("serial")
public class SweeperFrame extends JFrame implements ActionListener, NativeKeyListener, NativeMouseListener {
	
	// Define characteristics of the frame
	private final String TITLE = "CorruptSweeper";
	private final int MAP_WIDTH = 310;
	private final int MAP_LENGTH = 318;
	private final Dimension FRAME_SIZE = new Dimension(MAP_WIDTH + 10, MAP_LENGTH + 40);
	private final Point DEFAULT_LOCATION = new Point(0, 0);
	private final BufferedImage SPLASH_SCREEN = getSplash();
	
	// Define swing elements of the frame
	private JPanel mainPanel;	
	private JPanel mapPanel;
	private JPanel buttonPanel;
	private JButton findMap;
	private JLabel mapLabel;	
	private int posX;
	private int posY;
	
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
		
		// Sets up and registers global event listeners
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
		
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.out.println("There was a problem registering the snative hook.");
            System.out.println(ex.getMessage());
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
		setUndecorated(true);
		makeDraggable();

		
		// Adds "find map" button
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		findMap = new JButton("Find Map");
		findMap.setActionCommand("find");
		findMap.addActionListener(this);
		buttonPanel.add(findMap);
								
		// Adds JLabel to display map
		mapPanel = new JPanel();
		mapLabel = new JLabel(new ImageIcon(SPLASH_SCREEN));
		mapPanel.add(mapLabel);
		
		// Adds panels to frame
		mainPanel.setLayout(null); // Absolute layout
		mainPanel.add(mapPanel);
		mainPanel.add(buttonPanel);
		Dimension d;
		d = mapPanel.getPreferredSize();
		mapPanel.setBounds(0, 0, d.width, d.height);
		d = buttonPanel.getPreferredSize();
		buttonPanel.setBounds((mapPanel.getPreferredSize().width / 2) - (buttonPanel.getPreferredSize().width / 2), // Centered below mapPanel
				mapPanel.getPreferredSize().height, d.width, d.height);
				
		add(mainPanel);
		
		// Look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
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
	 * Allows the frame to be draggable
	 */
	public void makeDraggable() {
		this.addMouseListener(new MouseAdapter()
		{
		   public void mousePressed(MouseEvent e)
		   {
		      posX = e.getX();
		      posY = e.getY();
		   }
		});
		this.addMouseMotionListener(new MouseAdapter()
		{
		     public void mouseDragged(MouseEvent evt)
		     {
				//sets frame position when mouse dragged			
				setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
							
		     }
		});
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
	 * NativeMouseListener interface methods
	 */
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		// Nothing required, implementation required by NativeMouseListener interface	
	}
	
	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		if (e.getButton() == NativeMouseEvent.BUTTON1) { // Only search if left button is clicked
			try {
				Thread.sleep(720); // Waits ~1 tick for map to appear
			} catch (InterruptedException ie) {
				System.out.println("Sleep thread interrupted");
				ie.printStackTrace();
			}
			if (searchable.find(mapX) != null) {
				try {
					BufferedImage map = mapCapturer.getMap(getMapLoc());
				}
				catch (NullPointerException npe) {
					// Map not found
				}
			}
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		// Nothing required, implementation required by NativeMouseListener interface		
	}

	
	/**
	 * NativeKeyListener interface methods
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {	
		if (e.getKeyCode() == NativeKeyEvent.VC_M) { // Only searches for map if key pressed is "m" - TODO Allow for variable map keys
			try {
				Thread.sleep(720); // Waits ~1 tick for map to appear
			} catch (InterruptedException ie) {
				System.out.println("Sleep thread interrupted");				
				ie.printStackTrace();
			}
			if (searchable.find(mapX) != null) {
				try {
					updateMap(new ImageIcon(mapCapturer.getMap(getMapLoc())));
				}
				catch (NullPointerException npe) {
					// Map not found
				}
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// Nothing required, implementation required by NativeKeyListener interface
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// Nothing required, implementation required by NativeKeyListener interface		
	}
}
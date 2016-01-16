package corruptsweeper;

// Shape classes
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Rectangle;

// Image capture classes
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.AWTException;

public class MapCapturer {
	
	private final int MAP_LENGTH = 310;
	private final int MAP_WIDTH = 318;
	private final Dimension MAP_SIZE = new Dimension(MAP_WIDTH, MAP_LENGTH);
	
	// BufferedImage of the map to be returned
	private BufferedImage mapImg;
	
	/**
	 * Capture an image of the map to display 
	 * TODO - Only capture if is image of map
	 * @param loc the top left corner of the map
	 * @return the BufferedImage of the map
	 */
	public BufferedImage getMap(Point loc) {
		try {
			Robot r = new Robot();
			mapImg = r.createScreenCapture(new Rectangle(loc, MAP_SIZE));
		}
		// Should not occur - only if user attempts to run in CLI environment
		catch (AWTException e) {
			System.out.println("GraphicsEnvironment is Headless");
			e.printStackTrace();
		}		
		return mapImg;
	}	
}

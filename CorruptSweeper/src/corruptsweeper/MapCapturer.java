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
	
	private MapFinder mf = new MapFinder(); 
	private final Point MAP_TOP_LEFT = mf.findMap();
	
	private final int MAP_LENGTH = 341;
	private final int MAP_WIDTH = 322;
	private final Dimension MAP_SIZE = new Dimension(MAP_LENGTH, MAP_WIDTH);
	
	// BufferedImage of the map to be returned
	private BufferedImage mapImg;
	
	/**
	 * Generates an image of the map to display 
	 * TODO - Only capture if is image of map
	 * @param p the top left corner of the map
	 * @param d the size of the map
	 * @return the BufferedImage of the map
	 */
	public BufferedImage getMap() {
		try {
			Robot r = new Robot();
			mapImg = r.createScreenCapture(new Rectangle(MAP_TOP_LEFT, MAP_SIZE));
		}
		// Should not occur - only if user attempts to run in CLI environment
		catch (AWTException e) {
			System.out.println("GraphicsEnvironment is Headless");
			e.printStackTrace();
		}		
		return mapImg;
	}	
}

/**
 * The MapCapturer class contains a function to capture the map on the screen for display
 * 
 * @author Jack Pergantis 
 */

package corruptsweeper;

// Shape classes
import java.awt.Point;

// Image capture classes
import java.awt.image.BufferedImage;

// Sikuli
import org.sikuli.api.*;

public class MapCapturer {
	
	private final int MAP_LENGTH = 310;
	private final int MAP_WIDTH = 318;
	
	// BufferedImage of the map to be returned
	private BufferedImage mapImg;
	
	/**
	 * Capture an image of the map to display 
	 * @param loc the top left corner of the map
	 * @return the BufferedImage of the map
	 */
	public BufferedImage getMap(Point loc) {	
		mapImg = new DesktopScreenRegion(loc.x, loc.y, MAP_WIDTH, MAP_LENGTH).capture();
		return mapImg;
	}	
}

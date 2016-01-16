/**
 * The purpose of the MapFinder class is to locate the map on the screen.
 * 
 * @author Jack Pergantis 
 */

package corruptsweeper;

import java.awt.Point;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.sikuli.api.*;

public class MapFinder {
	
	private final int deltaX = 313; // Difference between left bound of X and left bound of map
	private final int deltaY = 11; // Difference between top bound of X and top bound of map
	
	/**
	 * @return a Point representing the top left corner of the map
	 */
	public Point findMap() {
		
		ScreenRegion fullscreen = new DesktopScreenRegion(); // Currently works on default monitor only!
		Target imageTarget = null;
		try {
			imageTarget = new ImageTarget(ImageIO.read(getClass().getResource("/resources/MapX.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Point mapXLoc = fullscreen.find(imageTarget).getBounds().getLocation();		
		Point mapLoc = new Point(mapXLoc.x - deltaX, mapXLoc.y - deltaY);
		return mapLoc;
	}
}

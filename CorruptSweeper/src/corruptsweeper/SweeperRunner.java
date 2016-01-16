/**
 * Contains main method to execute application 
 * 
 * @author Jack Pergantis
 */

package corruptsweeper; 

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.sikuli.api.*;

public class SweeperRunner {	
	
	private SweeperFrame sf;
	
	// Variables used to capture, update, and store the map and its location
	private MapCapturer mapCapturer = new MapCapturer();
	private Target mapX; 
	/**
	 * Main method to run the program 
	 * @param args the command line arguments
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		new SweeperRunner();		
	}
	
	public SweeperRunner() throws InterruptedException { 
		sf = new SweeperFrame();
		while (sf.getMapXLoc() == null) {
			Thread.sleep(1);
		}
		System.out.println("biggles");
		try {
			mapX = new ImageTarget(ImageIO.read(getClass().getResource("/resources/MapX.png")));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		ScreenRegion searchable = new DesktopScreenRegion(sf.getMapXLoc().x, sf.getMapXLoc().y, 16, 16);
		while (true) {
			if (searchable.find(mapX) != null) {
				sf.updateMap(new ImageIcon(mapCapturer.getMap(sf.getMapLoc())));
				Thread.sleep(100);
			}
		}
	}
}

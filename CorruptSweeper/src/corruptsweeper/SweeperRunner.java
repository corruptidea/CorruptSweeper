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
import org.sikuli.api.event.*;

public class SweeperRunner {	
	
	private SweeperFrame sf;
	
	// Variables used to capture, update, and store the map and its location
	private MapCapturer mapCapturer = new MapCapturer();
	private MapFinder mapFinder = new MapFinder();
	private Target mapX;
	private ScreenRegion mapXListenerRegion;
	/**
	 * Main method to run the program 
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		new SweeperRunner();
	}
	
	public SweeperRunner() {
		sf = new SweeperFrame();		
		try {
			mapX = new ImageTarget(ImageIO.read(getClass().getResource("/resources/MapX.png")));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		mapX.setMinScore(1.00);
		mapXListenerRegion = new DesktopScreenRegion(mapFinder.findMapX().x, mapFinder.findMapX().y, 16, 16); //TODO - use variables
		mapXListenerRegion.addTargetEventListener(mapX, listener);
	}
	
	/**
	 * Creates a TargetEventListener to automatically update the map when the "x" is detected in the location designated by sf.getMapLoc()
	 */
	TargetEventListener listener = new TargetEventListener() {       				
		@Override
		public void targetAppeared(TargetEvent event) {
			System.out.println("Target found! Attempting to update...");
			sf.updateMap(new ImageIcon(mapCapturer.getMap(sf.getMapLoc())));
		}

		@Override
		public void targetMoved(TargetEvent event) {
			// Nothing required, implementation required by interface
		}

		@Override
		public void targetVanished(TargetEvent event) {
			// Nothing required, implementation required by interface
		}
	};
}

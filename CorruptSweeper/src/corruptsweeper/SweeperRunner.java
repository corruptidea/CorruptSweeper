/**
 * Contains main method to execute application 
 * 
 * @author Jack Pergantis
 */

package corruptsweeper; 

import javax.swing.SwingUtilities;

public class SweeperRunner {	

	/**
	 * Main method to run the program 
	 * @param args the command line arguments
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SweeperFrame();
			}
		});	
	}
}

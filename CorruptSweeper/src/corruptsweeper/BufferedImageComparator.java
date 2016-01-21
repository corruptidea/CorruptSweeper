/**
 * Used to compare 2 BufferedImages for similarity
 */

package corruptsweeper;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
import java.util.Comparator;

public class BufferedImageComparator implements Comparator<BufferedImage> {

	// TODO Make this something other than a glorified .equals function
	@Override
	public int compare(BufferedImage a, BufferedImage b) {
		DataBuffer dbA = a.getRaster().getDataBuffer();
		DataBuffer dbB = b.getRaster().getDataBuffer();
		
		DataBufferByte aDBAsDBByte = (DataBufferByte) dbA;
		DataBufferByte bDBAsDBByte = (DataBufferByte) dbB;
		
		for (int bank = 0; bank < aDBAsDBByte.getNumBanks();) {
			   byte[] actual = aDBAsDBByte.getData(bank);
			   byte[] expected = bDBAsDBByte.getData(bank);
			   
			   return Arrays.equals(actual, expected) ? 1 : 0;
		}
		return 0;
	}

}

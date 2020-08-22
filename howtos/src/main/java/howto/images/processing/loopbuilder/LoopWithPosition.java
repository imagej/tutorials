/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.images.processing.loopbuilder;

import net.imagej.ImageJ;
import net.imglib2.img.Img;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Intervals;

/**
 * How to use the LoopBuilder while accessing the position of a pixel
 *
 * @author Matthias Arzt
 * @author Deborah Schmidt
 */
public class LoopWithPosition {

	public static void run() {

		ImageJ ij = new ImageJ();

		Img<DoubleType> image = ij.op().create().img(new long[]{50, 60});

		LoopBuilder.setImages( Intervals.positions( image ), image ).forEachPixel(
				( position, pixel ) -> {
					int x = position.getIntPosition( 0 );
					int y = position.getIntPosition( 1 );
					pixel.set( x * x + y * y );
				}
		);

		ij.ui().show(image);

	}

	public static void main(String...args) {
		run();
	}

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 *
 * This work was supported by the German Research Foundation (DFG) under the code JU3110/1-1 (Fiji Software Sustainability).
 */

package howto.images.drawing;

import net.imagej.ImageJ;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Intervals;
import net.imglib2.view.Views;

/**
 * How to draw a rectangle
 *
 * @author Deborah Schmidt
 */
public class DrawRectangle {

	private static void run() {

		ImageJ ij = new ImageJ();

		Img<DoubleType> img = ij.op().create().img(new long[] { 256, 254 });

		// draws rectangle at position [50. 60] with a size of 20x20 px
		Views.interval(img, Intervals.createMinSize( 50, 60, 20, 20 )).forEach(pixel -> pixel.set(25));

		ij.ui().show(img);

	}

	public static void main(String...args) {
		run();
	}
}

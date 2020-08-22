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

import java.util.List;

/**
 * How to use the LoopBuilder to process an image in chunks
 *
 * @author Matthias Arzt
 * @author Deborah Schmidt
 */
public class HandleInChunks {

	public static void run() {

		ImageJ ij = new ImageJ();

		// create image
		Img<DoubleType> image = ij.op().create().img(new long[]{10, 10});
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				image.getAt(x, y).set(x+y);
			}
		}

		List<DoubleType> listOfSums = LoopBuilder.setImages( image ).multiThreaded().forEachChunk(
				chunk -> {
					DoubleType sum = new DoubleType();
					chunk.forEachPixel( pixel -> {
						sum.add(new DoubleType(pixel.getRealDouble()));
					});
					return sum;
				}
		);

		DoubleType totalSum = new DoubleType();
		listOfSums.forEach(totalSum::add);

		System.out.println(totalSum);

		ij.dispose();
	}

	public static void main(String...args) {
		run();
	}

}

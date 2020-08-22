/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.images.processing.loopbuilder;

import net.imagej.ImageJ;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

import java.io.IOException;

/**
 * How to use the LoopBuilder for processing multiple images together
 *
 * @author Matthias Arzt
 * @author Deborah Schmidt
 */
public class ProcessMultipleImages {

	public static <T extends RealType<T> & NativeType<T>> void run() throws IOException {

		ImageJ ij = new ImageJ();

		// load example image to imageA
		Img<T> imageA = (Img<T>) ij.io().open(Object.class.getResource("/blobs.png").getPath());
		// set imageB to mirrored view of ImageA
		RandomAccessibleInterval<T> imageB = ij.op().transform().invertAxisView(imageA, 0);
		// create result image
		Img<DoubleType> sum = ij.op().create().img(imageA, new DoubleType());

		LoopBuilder.setImages(imageA, imageB, sum).forEachPixel(
				(a, b, s) -> {
					s.setReal(a.getRealDouble() + b.getRealDouble());
				}
		);

		ij.ui().show(sum);

	}

	public static void main(String...args) throws IOException {
		run();
	}

}

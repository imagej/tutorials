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
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

import java.io.IOException;

/**
 * How to use the LoopBuilder running on multiple threads
 *
 * @author Matthias Arzt
 * @author Deborah Schmidt
 */
public class Multithreading {

	public static <T extends RealType<T>> void run() throws IOException {

		ImageJ ij = new ImageJ();

		// load example image
		RandomAccessibleInterval<T> source = (Img<T>) ij.io().open(Object.class.getResource("/blobs.png").getPath());

		// create result image
		Img<DoubleType> target = ij.op().create().img(source, new DoubleType());

		LoopBuilder.setImages( source, target ).multiThreaded().forEachPixel( ( s, t ) -> t.set( s.getRealDouble() ) );

		ij.ui().show(target);

	}

	public static void main(String...args) throws IOException {
		run();
	}

}

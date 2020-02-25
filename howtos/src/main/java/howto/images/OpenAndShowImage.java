/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.images;

import io.scif.img.IO;
import net.imagej.ImageJ;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;

import java.io.IOException;

/**
 * How to open and show an image
 *
 * @author Deborah Schmidt
 */
public class OpenAndShowImage {

	/**
	 * .. from the ImageJ instance:
	 */
	private static void run() throws IOException {

		ImageJ ij = new ImageJ();

		// open image from local resource
		Img img = (Img) ij.io().open(Object.class.getResource("/blobs.png").getPath());

		// show image
		ij.ui().show(img);

	}

	/**
	 * .. by using static methods: <br>
	 * (NOTE1: Only open images like this if you don't have and don't want a {@link org.scijava.Context}.)
	 * (NOTE2: {@link ImageJFunctions#show} relies on ImageJ1.)
	 */
	private static void runStatic() {

		// open image statically
		Img img = IO.openImgs(Object.class.getResource("/blobs.png").getPath()).get(0);

		// display image statically
		ImageJFunctions.show(img);
	}

	public static void main(String...args) throws IOException {
		run();
		runStatic();
	}
}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package howto.images;

import io.scif.img.IO;
import net.imagej.ImageJ;
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
	 * .. by using static methods:
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

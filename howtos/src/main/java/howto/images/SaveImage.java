/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package howto.images;

import net.imagej.ImageJ;
import net.imglib2.img.Img;

import java.io.IOException;
import java.nio.file.Files;

/**
 * How to save an image
 *
 * @author Deborah Schmidt
 */
public class SaveImage {

	private static void run() throws IOException {
		ImageJ ij = new ImageJ();
		ij.ui().showUI();

		// open image from resource
		Img img = (Img) ij.io().open(Object.class.getResource("/blobs.png").getPath());

		// create temporary path to save image to
		String dest = Files.createTempFile("img", ".png").toString();
		System.out.println("Saving image to " + dest);

		// save image
		ij.io().save(img, dest);

		// load and show saved image
		Object savedImg = ij.io().open(dest);
		ij.ui().show("saved image", savedImg);

	}

	public static void main(String...args) throws IOException {
		run();
	}
}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.images;

import net.imagej.Dataset;
import net.imagej.ImageJ;

import java.io.IOException;
import java.util.List;

/**
 * How to get all open images
 *
 * @author Deborah Schmidt
 */
public class GetOpenImages {

	private static void run() throws IOException {

		ImageJ ij = new ImageJ();

		// open images
		Object img1 = ij.io().open("http://samples.fiji.sc/blobs.png");
		Object img2 = ij.io().open("http://samples.fiji.sc/new-lenna.jpg");

		// show images
		ij.ui().show(img1);
		ij.ui().show(img2);

		// get open images
		List<Dataset> datasets = ij.dataset().getDatasets();

		// print their names
		String name1 = datasets.get(0).getName();
		String name2 = datasets.get(1).getName();
		System.out.println(name1);
		System.out.println(name2);

	}

	public static void main(String...args) throws IOException {
		run();
	}
}

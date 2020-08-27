/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.datasets;

import java.io.File;

import net.imagej.Dataset;
import net.imagej.ImageJ;

/** Loads and displays a dataset using the ImageJ API. */
public class LoadAndDisplayDataset {

	public static void main(final String... args) throws Exception {
		// create the ImageJ application context with all available services
		final ImageJ ij = new ImageJ();

		// ask the user for a file to open
		final File file = ij.ui().chooseFile(null, "open");

		// load the dataset
		final Dataset dataset = ij.scifio().datasetIO().open(file.getPath());

		// display the dataset
		ij.ui().show(dataset);
	}

}

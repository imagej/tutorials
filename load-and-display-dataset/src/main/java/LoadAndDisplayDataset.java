/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.data.Dataset;
import imagej.display.DisplayService;
import imagej.io.IOService;

import java.io.File;

import javax.swing.JFileChooser;

/** Loads and displays a dataset using the ImageJ API. */
public class LoadAndDisplayDataset {

	public static void main(final String... args) throws Exception {
		// create the ImageJ application context with all available services
		final ImageJ context = ImageJ.createContext();

		// ask the user for a file to open
		final JFileChooser chooser = new JFileChooser();
		final int returnVal = chooser.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) return;
		final File file = chooser.getSelectedFile();

		// load the dataset
		final IOService ioService = context.getService(IOService.class);
		final Dataset dataset = ioService.loadDataset(file.getAbsolutePath());

		// display the dataset
		final DisplayService displayService =
			context.getService(DisplayService.class);
		displayService.createDisplay(file.getName(), dataset);
	}

}

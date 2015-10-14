
/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.io.File;
import java.io.IOException;

import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import io.scif.services.DatasetIOService;
import net.imagej.Dataset;
import net.imagej.ImageJ;

/**
 * The ImageJ framework provides a dataset service for opening images, as well
 * as a user interface service for displaying UI elements to the user.
 *
 * This tutorial shows how to use these services to open an image and display it
 * to the user.
 *
 * A main method is provided so this class can be run directly from Eclipse (or
 * any other IDE).
 *
 * Also, because this class implements Command and is annotated as an @Plugin,
 * it will show up in the ImageJ menus: under Tutorials>Display Dataset, as
 * specified by the menuPath field of the @Plugin annotation.
 */
@Plugin(type = Command.class, menuPath = "Tutorials>Display Dataset")
public class LoadAndDisplayDataset implements Command {

	/*
	 * Our first two @Parameters are core ImageJ services (and thus @Plugins).
	 * The context will provide them automatically when this command is created.
	 */
	@Parameter
	private UIService uiService;

	@Parameter
	private DatasetIOService datasetIOService;

	/*
	 * In this command, we will be using functions that can throw exceptions.
	 * Best practice is to log these exceptions to let the user know what went
	 * wrong. By using the LogService to do this, we let the framework decide
	 * the best place to display thrown errors.
	 */
	@Parameter
	private LogService logService;

	/*
	 * We need to know what image to open. So, in java we use java.io.File when
	 * we need to access a file on disk. Files are not @Plugins, so the context
	 * cannot provide one. For non-plugin parameters like this, the active user
	 * interface is asked for an appropriate value. Typically, this will involve
	 * asking the user for that value, but this command does not need to know
	 * the specific user interface currently active. We call this "UI agnostic".
	 */
	@Parameter
	private File imageFile;

	/*
	 * The run() method is where we do the actual 'work' of the command. In this
	 * case, it is fairly trivial because we are simply calling ImageJ Services.
	 */
	@Override
	public void run() {
		try {
			final Dataset image = datasetIOService.open(imageFile.getAbsolutePath());
			uiService.show(image);
		} catch (final IOException exc) {
			// Uses LogService to report the error.
			logService.error(exc);
		}
	}

	/*
	 * This main method is for convenience - so you can run this command
	 * directly from Eclipse (or other IDE).
	 *
	 * It will launch ImageJ and then run this command using the CommandService.
	 * This is equivalent to clicking "Tutorials>Display Dataset" in the UI.
	 */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = net.imagej.Main.launch(args);

		// Launch the "LoadAndDisplayDataset" command.
		ij.command().run(LoadAndDisplayDataset.class, true);
	}
}

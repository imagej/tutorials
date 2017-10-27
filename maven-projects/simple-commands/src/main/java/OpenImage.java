/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.io.File;
import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import io.scif.services.DatasetIOService;

/**
 * This tutorial shows how to use ImageJ services to open an image and display
 * it to the user.
 * <p>
 * A main method is provided so this class can be run directly from Eclipse (or
 * any other IDE).
 * </p>
 * <p>
 * Because this class implements {@link Command} and is annotated with
 * {@code @Plugin}, it will show up in the ImageJ menus: under Tutorials &gt;
 * Open Image, as specified by the {@code menuPath} field of the {@code @Plugin}
 * annotation.
 * </p>
 * <p>
 * See also the {@code LoadAndDisplayDataset} tutorial.
 * </p>
 */
@Plugin(type = Command.class, menuPath = "Tutorials>Open Image")
public class OpenImage implements Command {

	/*
	 * This first @Parameter is a core ImageJ service (and thus @Plugin). The
	 * context will provide it automatically when this command is created.
	 */

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
	 * We need to know what image to open. So, the framework will ask the user
	 * via the active user interface to select a file to open. This command is
	 * "UI agnostic": it does not need to know the specific user interface
	 * currently active.
	 */
	@Parameter
	private File imageFile;

	/*
	 * This command will produce an image that will automatically be shown by
	 * the framework. Again, this command is "UI agnostic": how the image is
	 * shown is not specified here.
	 */
	@Parameter(type = ItemIO.OUTPUT)
	private Dataset image;

	/*
	 * The run() method is where we do the actual 'work' of the command. In this
	 * case, it is fairly trivial because we are simply calling ImageJ Services.
	 */
	@Override
	public void run() {
		try {
			image = datasetIOService.open(imageFile.getAbsolutePath());
		}
		catch (final IOException exc) {
			// Use the LogService to report the error.
			logService.error(exc);
		}
	}

	/*
	 * This main method is for convenience - so you can run this command
	 * directly from Eclipse (or any other IDE).
	 *
	 * It will launch ImageJ and then run this command using the CommandService.
	 * This is equivalent to clicking "Tutorials>Open Image" in the UI.
	 */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Launch the "OpenImage" command.
		ij.command().run(OpenImage.class, true);
	}

}

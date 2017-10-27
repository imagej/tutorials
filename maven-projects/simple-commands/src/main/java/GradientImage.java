/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.ImageJ;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * A command that generates a diagonal gradient image of user-given size.
 * <p>
 * For an even simpler command, see {@link HelloWorld} in this same
 * package!
 * </p>
 */
@Plugin(type = Command.class, headless = true,
	menuPath = "File>New>Gradient Image")
public class GradientImage implements Command {

	@Parameter
	private DatasetService datasetService;

	@Parameter(min = "1")
	private int width = 512;

	@Parameter(min = "1")
	private int height = 512;

	@Parameter(type = ItemIO.OUTPUT)
	private Dataset dataset;

	@Override
	public void run() {
		// Generate a byte array containing the diagonal gradient.
		final byte[] data = new byte[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final int index = y * width + x;
				data[index] = (byte) (x + y);
			}
		}

		// Create an empty dataset.
		final String name = "Gradient Image";
		final long[] dims = { width, height };
		final AxisType[] axes = { Axes.X, Axes.Y };
		dataset = datasetService.create(new UnsignedByteType(), dims, name, axes);

		// Populate the dataset with the gradient data.
		dataset.setPlane(0, data);

		// NB: Because the dataset is declared as an "OUTPUT" above,
		// ImageJ automatically takes care of displaying it afterwards!
	}

	/** Tests our command. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Launch the "Gradient Image" command right away.
		ij.command().run(GradientImage.class, true);
	}

}

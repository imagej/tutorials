/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.userinput;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * How to use the validater method for SciJava parameters. Here, the execution
 * is canceled if the {@link Dataset} does not have the expected number of
 * dimensions.
 */
@Plugin(type = Command.class)
public class ValidateParameter extends ContextCommand {

	@Parameter
	private UIService uiService;

	@Parameter(validater = "validateDims")
	private Dataset dataset;

	public void validateDims() {
		if (dataset.numDimensions() != 2) {
			cancel("This command only works with 2D images.");
		}
	}

	@Override
	public void run() {
		uiService.showDialog("Yay, dataset '" + dataset.getName() + "' is 2D!");
		// ... do something with the image ...
	}

	public static void main(final String[] args) {
		final ImageJ ij = new ImageJ();
		ij.ui().showUI();

		final UnsignedByteType type = new UnsignedByteType();
		final long[] dims = { 512, 384, 5 };
		final String name = "Blank";
		final AxisType[] axes = { Axes.X, Axes.Y, Axes.Z };
		final Dataset d = ij.dataset().create(type, dims, name, axes);
		ij.ui().show(d);

		ij.command().run(ValidateParameter.class, true);
	}
}

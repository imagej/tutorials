/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.extensions;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.FloatType;

import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class)
public class CommandThatChecksImageType extends ContextCommand {

	@Parameter(validater = "validateDataset")
	private Dataset dataset;

	public void validateDataset() {
		if (dataset.firstElement() instanceof UnsignedByteType) {
			cancel("This command only works with uint8 images.");
		}
	}

	@Override
	public void run() {
		System.out.println("The dataset '" + dataset.getName() + "' is uint8.");
		// ... do something with the image ...
	}

	public static void main(final String[] args) {
		final ImageJ ij = new ImageJ();
		ij.ui().showUI();

		final FloatType type = new FloatType();
		final long[] dims = { 512, 384, 5 };
		final String name = "Example";
		final AxisType[] axes = { Axes.X, Axes.Y, Axes.Z };
		final Dataset d = ij.dataset().create(type, dims, name, axes);
		ij.ui().show(d);

		ij.command().run(CommandThatChecksImageType.class, true);
	}
}

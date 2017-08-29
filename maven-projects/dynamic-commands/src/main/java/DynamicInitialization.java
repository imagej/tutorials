/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imagej.ops.Initializable;

import org.scijava.command.Command;
import org.scijava.command.DynamicCommand;
import org.scijava.module.MutableModuleItem;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;
import org.scijava.widget.NumberWidget;

/**
 * An ImageJ2 command which dynamically computes aspects of its parameters
 * during initialization.
 */
@Plugin(type = Command.class)
public class DynamicInitialization extends DynamicCommand implements
	Initializable
{

	// -- Parameters --

	@Parameter
	private UIService ui;

	@Parameter
	private Dataset image;

	@Parameter(min = "0", style = NumberWidget.SCROLL_BAR_STYLE)
	private int dimension;

	// -- Initializable methods --

	@Override
	public void initialize() {
		final MutableModuleItem<Integer> dimensionItem = //
			getInfo().getMutableInput("dimension", int.class);
		dimensionItem.setMaximumValue(image.numDimensions() - 1);
	}

	// -- Runnable methods --

	@Override
	public void run() {
		ui.showDialog("You chose dimension #" + dimension);
	}

	// -- Main method --

	/** Tests our command. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Create a beautiful test image.
		long[] dims = {512, 64, 2, 3};
		String name = "A blank 4D image";
		AxisType[] axes = {Axes.X, Axes.Y, Axes.Z, Axes.TIME};
		int bitsPerPixel = 8;
		boolean signed = false;
		boolean floating = false;
		final Dataset dataset =
			ij.dataset().create(dims, name, axes, bitsPerPixel, signed, floating);
		ij.ui().show(dataset);

		// Launch the "DynamicInitialization" command.
		ij.command().run(DynamicInitialization.class, true);
	}
}

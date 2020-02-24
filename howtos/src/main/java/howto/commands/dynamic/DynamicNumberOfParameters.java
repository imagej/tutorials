/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */
package howto.commands.dynamic;

import java.util.ArrayList;
import java.util.List;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imagej.ops.Initializable;

import org.scijava.command.Command;
import org.scijava.command.DynamicCommand;
import org.scijava.module.DefaultMutableModuleItem;
import org.scijava.module.ModuleItem;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * An ImageJ2 command which dynamically introduces new parameters during
 * initialization.
 */
@Plugin(type = Command.class)
public class DynamicNumberOfParameters extends DynamicCommand implements
	Initializable
{

	// -- Parameters --

	@Parameter
	private UIService ui;

	@Parameter
	private Dataset image;

	private List<ModuleItem<Boolean>> checkboxItems = new ArrayList<>();

	// -- Initializable methods --

	@Override
	public void initialize() {
		// Introduce one boolean checkbox per dimension of the image.
		for (int d = 0; d < image.numDimensions(); d++) {
			final ModuleItem<Boolean> item = new DefaultMutableModuleItem<>(getInfo(),
				"axis" + d, boolean.class);
			item.setLabel("Axis #" + d + " - " + image.axis(d).type());
			checkboxItems.add(item);
			getInfo().addInput(item);
		}
	}

	// -- Runnable methods --

	@Override
	public void run() {
		int count = 0;
		for (ModuleItem<Boolean> item : checkboxItems) {
			if (item.getValue(this)) count++;
		}
		switch (count) {
			case 0: ui.showDialog("You selected no dimensions"); break;
			case 1: ui.showDialog("You selected one dimension"); break;
			default: ui.showDialog("You selected " + count + " dimensions");
		}
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

		// Launch the "DynamicNumberOfParameters" command.
		ij.command().run(DynamicNumberOfParameters.class, true);
	}
}

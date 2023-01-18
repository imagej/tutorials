/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.commands.dynamic;

import java.util.Arrays;

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
import org.scijava.widget.ChoiceWidget;

/**
 * An ImageJ2 command which dynamically alters aspects of its parameters when
 * parameter values change.
 * <p>
 * NB: As of this writing, this command WILL NOT WORK AS DESIRED in the ImageJ
 * Legacy or ImageJ Swing UIs, due to limitations in the scijava-ui-swing input
 * harvester implementation.
 * </p>
 */
@Plugin(type = Command.class)
public class DynamicCallbacks extends DynamicCommand implements
	Initializable
{

	// -- Parameters --

	@Parameter
	private UIService ui;

	@Parameter(callback = "kindOfThingChanged", //
		choices = { "Animal", "Vegetable", "Mineral" },
		style = ChoiceWidget.RADIO_BUTTON_HORIZONTAL_STYLE)
	private String kindOfThing = "Animal";

	@Parameter(choices = {"a", "b", "c"})
	private String thing = "a";

	// -- Callback methods --

	private void kindOfThingChanged() {
		final MutableModuleItem<String> thingItem = //
			getInfo().getMutableInput("thing", String.class);
		switch (kindOfThing) {
			case "Animal":
				thingItem.setChoices(Arrays.asList("Lion", "Tiger", "Bear"));
				break;
			case "Vegetable":
				thingItem.setChoices(Arrays.asList("Sage", "Rosemary", "Thyme"));
				break;
			case "Mineral":
				thingItem.setChoices(Arrays.asList("Diamond", "Emerald", "Ruby"));
				break;
			default:
				thingItem.setChoices(Arrays.asList("???", "WAT", "OHNOEZ"));
				break;
		}
	}

	// -- Initializable methods --

	@Override
	public void initialize() { getInfo(); } // HACK: Workaround for bug in SJC.

	// -- Runnable methods --

	@Override
	public void run() {
		ui.showDialog("You chose: " + thing);
	}

	// -- Main method --

	/** Tests our command. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Create a beautiful test image.
		long[] dims = {512, 128};
		String name = "A spiffy blank image";
		AxisType[] axes = {Axes.X, Axes.Y};
		int bitsPerPixel = 8;
		boolean signed = false;
		boolean floating = false;
		final Dataset dataset =
			ij.dataset().create(dims, name, axes, bitsPerPixel, signed, floating);
		ij.ui().show(dataset);

		// Launch the "DynamicCallbacks" command.
		ij.command().run(DynamicCallbacks.class, true);
	}
}

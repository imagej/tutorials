/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */
package howto.ui.preview;

import ij.ImagePlus;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;

import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.command.Previewable;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * An ImageJ2 command with preview that is triggered only when the Preview
 * checkbox is active.
 */
@Plugin(type = Command.class,
	menuPath = "Tutorials>Preview Checkbox")
public class PreviewCheckbox implements Command, Previewable {

	// -- Parameters --

	@Parameter
	private ImagePlus imp;

	@Parameter(persist = false, initializer = "initTitle")
	private String title;

	@Parameter(visibility = ItemVisibility.INVISIBLE, persist = false,
		callback = "previewChanged")
	private boolean preview;

	// -- Other fields --

	/** The original title of the image. */
	private String initialTitle;

	// -- Command methods --

	@Override
	public void run() {
		// Set the image's title to the specified value.
		imp.setTitle(title);
	}

	// -- Previewable methods --

	@Override
	public void preview() {
		if (preview) run();
	}

	@Override
	public void cancel() {
		// Set the image's title back to the original value.
		imp.setTitle(initialTitle);
	}

	// -- Initializer methods --

	/** Initializes the {@link #title} parameter. */
	protected void initTitle() {
		title = initialTitle = imp.getTitle();
	}

	// -- Callback methods --

	/** Called when the {@link #preview} parameter value changes. */
	protected void previewChanged() {
		// When preview box is unchecked, reset the image title back to original.
		if (!preview) cancel();
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

		// Launch the "PreviewCheckbox" command.
		ij.command().run(PreviewCheckbox.class, true);
	}

}

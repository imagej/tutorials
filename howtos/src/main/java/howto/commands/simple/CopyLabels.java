/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */
package howto.commands.simple;

import ij.IJ;
import ij.ImagePlus;
import net.imagej.ImageJ;

import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/** A command that preserves the labels from one image to another. */
@Plugin(type = Command.class)
public class CopyLabels extends ContextCommand {

	/** Image with desired labels. */
	@Parameter
	private ImagePlus source;

	/** Image to which labels should be assigned. */
	@Parameter
	private ImagePlus target;

	@Override
	public void run() {
		final int sourceSize = source.getStackSize();
		final int targetSize = target.getStackSize();
		if (sourceSize != targetSize) {
			cancel("Source and target images must have the same number of slices." +
				"(" + sourceSize + " != " + targetSize +")");
			return;
		}
		for (int i=1; i<=sourceSize; i++) {
			final String label = source.getStack().getSliceLabel(i);
			target.getStack().setSliceLabel(label, i);
		}
		target.repaintWindow();
	}

	/** A {@code main()} method for testing. */
	public static void main(final String... args) {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Create an image with fancy slice labels.
		IJ.newImage("Fancy", "8-bit", 384, 64, 2);
		final ImagePlus fancy = IJ.getImage();
		fancy.getStack().setSliceLabel("The quick brown fox", 1);
		fancy.getStack().setSliceLabel("jumps over the lazy dogs", 2);
		fancy.repaintWindow();

		// Create an image without slice labels.
		IJ.newImage("Simple", "8-bit", 400, 50, 2);
		final ImagePlus simple = IJ.getImage();

		// Test our "CopyLabels" command.
		ij.command().run(CopyLabels.class, true, "source", fancy, "target", simple);
	}

}

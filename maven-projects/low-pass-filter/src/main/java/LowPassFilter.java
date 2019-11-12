/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

import java.io.File;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.ops.OpService;
import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.ComplexType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.util.Util;
import net.imglib2.view.Views;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.widget.FileWidget;

/**
 * A {@link Command} plugin performing a
 * <a href="https://en.wikipedia.org/wiki/Low-pass_filter">low-pass filter</a>
 * on an image.
 */
@Plugin(type = Command.class, headless = true,
	menuPath = "Tutorials > Low-pass Filter...")
public class LowPassFilter<T extends RealType<T>> implements Command {

	@Parameter
	private OpService ops;

	@Parameter
	private Img<T> image;

	@Parameter
	private double radius = 10;

	@Parameter(type = ItemIO.OUTPUT)
	private Img<FloatType> result;

	/** The run method executes the command. */
	@Override
	public void run() {
		process();
	}

	/** We use a helper method here to declare a type variable {@code C}. */
	private <C extends ComplexType<C>> void process() {
		// Perform FFT of the input.
		// To make typing simpler here, we use a raw return type.
		RandomAccessibleInterval<C> fft = ops.filter().fft(image);

		// Filter it.
		lowPass(fft, radius);

		// Reverse the FFT.
		result = ops.create().img(image, new FloatType());
		ops.filter().ifft(result, fft);
	}

	/**
	 * Performs an inplace low-pass filter on an image in Fourier space.
	 * <p>
	 * It is a good practice to structure non-trivial routines as
	 * {@code public static} so that it can be conveniently called from external
	 * code.
	 * </p>
	 * 
	 * @param fft The image in Fourier space to be filtered.
	 * @param radius The radius of the filter.
	 */
	public static <T extends ComplexType<T>> void lowPass(
		final RandomAccessibleInterval<T> fft, final double radius)
	{
		// Declare an array to hold the current position of the cursor.
		final long[] pos = new long[fft.numDimensions()];

		// Define origin as 0,0.
		final long[] origin = {0, 0};

		// Define a 2nd 'origin' at bottom left of image.
		// This is a bit of a hack. We want to draw a circle around the origin,
		// since the origin is at 0,0 - the circle will 'reflect' to the bottom.
		final long[] origin2 = {0, fft.dimension(1)};

		// Loop through all pixels.
		final Cursor<T> cursor = Views.iterable(fft).localizingCursor();
		while (cursor.hasNext()) {
			cursor.fwd();
			cursor.localize(pos);

			// Calculate distance from 0,0 and bottom left corner
			// (so we can form the reflected semi-circle).
			final double dist = Util.distance(origin, pos);
			final double dist2 = Util.distance(origin2, pos);

			// If distance is above radius (cutoff frequency),
			// set value of FFT to zero.
			if (dist > radius && dist2 > radius)
				cursor.get().setZero();
		}
	}

	/** The main method enables standalone testing of the command. */
	public static void main(final String... args) throws Exception {
		// create the ImageJ application context with all available services
		final ImageJ ij = new ImageJ();

		// display the user interface
		ij.ui().showUI();

		// open and display an image
		final File imageFile = ij.ui().chooseFile(null, FileWidget.OPEN_STYLE);
		final Dataset image = ij.scifio().datasetIO().open(imageFile.getAbsolutePath());
		ij.ui().show(image);

		// execute the filter, waiting for the operation to finish.
		ij.command().run(LowPassFilter.class, true).get().getOutput("result");
	}
}

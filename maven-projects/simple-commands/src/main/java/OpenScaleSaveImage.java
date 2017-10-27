/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import io.scif.services.DatasetIOService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import net.imagej.Dataset;
import net.imagej.DefaultDataset;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.RandomAccessible;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.ImgView;
import net.imglib2.interpolation.InterpolatorFactory;
import net.imglib2.interpolation.randomaccess.FloorInterpolatorFactory;
import net.imglib2.interpolation.randomaccess.LanczosInterpolatorFactory;
import net.imglib2.interpolation.randomaccess.NLinearInterpolatorFactory;
import net.imglib2.interpolation.randomaccess.NearestNeighborInterpolatorFactory;
import net.imglib2.type.numeric.RealType;

import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * This tutorial shows how to use open an image, scale it, then save it.
 * <p>
 * A main method is provided so this class can be run directly from Eclipse (or
 * any other IDE).
 * </p>
 * <p>
 * Because this class implements {@link Command} and is annotated with
 * {@code @Plugin}, it will show up in the ImageJ menus: under Tutorials &gt;
 * Open+Scale+Save Image, as specified by the {@code menuPath} field of the
 * {@code @Plugin} annotation.
 * </p>
 * <p>
 * See also the {@code LoadAndDisplayDataset} tutorial.
 * </p>
 */
@Plugin(type = Command.class, menuPath = "Tutorials>Open+Scale+Save Image")
public class OpenScaleSaveImage implements Command {

	// -- Scale method constants --

	private static final String LANCZOS = "Lanczos";
	private static final String N_LINEAR = "N-linear";
	private static final String NEAREST_NEIGHBOR = "Nearest neighbor";
	private static final String FLOOR = "Floor";

	// -- Needed services --

	// For opening and saving images.
	@Parameter
	private DatasetIOService datasetIOService;

	// For scaling the image.
	@Parameter
	private OpService ops;

	// For logging errors.
	@Parameter
	private LogService log;

	// -- Inputs to the command --

	/** Location on disk of the input image. */
	@Parameter(label = "Image to load")
	private File inputImage;

	/** Factor by which to scale the image. */
	@Parameter(label = "Scale factor")
	private double factor = 2;

	@Parameter(label = "Scale method", //
		choices = { LANCZOS, N_LINEAR, NEAREST_NEIGHBOR, FLOOR })
	private String method = LANCZOS;

	/** Location on disk to save the processed image. */
	@Parameter(label = "Image to save")
	private File outputImage;

	@Override
	public void run() {
		try {
			// load the image
			final Dataset image = datasetIOService.open(inputImage.getAbsolutePath());

			// scale the image
			final Dataset result = scaleImage(image);

			// save the image
			datasetIOService.save(result, outputImage.getAbsolutePath());
		}
		catch (final IOException exc) {
			log.error(exc);
		}
	}

	private Dataset scaleImage(final Dataset dataset) {
		// NB: We must do a raw cast through Img, because Dataset does not
		// retain the recursive type parameter; it has an ImgPlus<RealType<?>>.
		// This is invalid for routines that need Img<T extends RealType<T>>.
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final Img<RealType<?>> result = scaleImage((Img) dataset.getImgPlus());

		// Finally, coerce the result back to an ImageJ Dataset object.
		return new DefaultDataset(dataset.context(), new ImgPlus<>(result));
	}

	private <T extends RealType<T>> Img<T> scaleImage(final Img<T> image) {
		final double[] scaleFactors = new double[image.numDimensions()];
		Arrays.fill(scaleFactors, factor);

		final InterpolatorFactory<T, RandomAccessible<T>> interpolator;
		switch (method) {
			case N_LINEAR:
				interpolator = new NLinearInterpolatorFactory<>();
				break;
			case NEAREST_NEIGHBOR:
				interpolator = new NearestNeighborInterpolatorFactory<>();
				break;
			case LANCZOS:
				interpolator = new LanczosInterpolatorFactory<>();
				break;
			case FLOOR:
				interpolator = new FloorInterpolatorFactory<>();
				break;
			default:
				throw new IllegalArgumentException("Invalid scale method: " + method);
		}

		// Perform the transformation using Ops.
		final RandomAccessibleInterval<T> rai = //
			ops.transform().scale(image, scaleFactors, interpolator);
		return ImgView.wrap(rai, image.factory());
	}

	/*
	 * This main method is for convenience - so you can run this command
	 * directly from Eclipse (or other IDE).
	 *
	 * It will launch ImageJ and then run this command using the CommandService.
	 * This is equivalent to clicking "Tutorials>Open+Scale+Save Image" in the UI.
	 */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Launch the "OpenScaleSaveImage" command.
		ij.command().run(OpenScaleSaveImage.class, true);
	}

}

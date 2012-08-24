/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.data.Dataset;
import imagej.data.DatasetService;
import imagej.display.DisplayService;
import imagej.io.IOService;

import java.io.File;

import javax.swing.JFileChooser;

import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.io.ImgIOException;
import net.imglib2.meta.AxisType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

/** Adds two datasets using the ImgLib2 OPS framework. */
public class AddTwoDatasets {

	public static void main(final String... args) throws Exception {
		// create the ImageJ application context with all available services
		final ImageJ context = ImageJ.createContext();

		// load two datasets
		final IOService ioService = context.getService(IOService.class);
		final Dataset dataset1 = load(ioService);
		final Dataset dataset2 = load(ioService);

		// add them together
		final DatasetService datasetService =
			context.getService(DatasetService.class);
		final Dataset result = addRandomAccess(datasetService, dataset1, dataset2);

		// display the results
		final DisplayService displayService =
			context.getService(DisplayService.class);
		displayService.createDisplay(dataset1.getName(), dataset1);
		displayService.createDisplay(dataset2.getName(), dataset2);
		displayService.createDisplay(dataset2.getName(), result);
	}

	/**
	 * Adds two datasets using a loop with an ImgLib
	 * {@link net.imglib2.RandomAccess}. This is a very powerful approach but
	 * requires a verbose loop.
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Dataset addRandomAccess(final DatasetService datasetService,
		final Dataset d1, final Dataset d2)
	{
		final Dataset result = create(datasetService, d1, d2);

		// sum data into result dataset
		final RandomAccess<? extends RealType> ra1 = d1.getImgPlus().randomAccess();
		final RandomAccess<? extends RealType> ra2 = d2.getImgPlus().randomAccess();
		final Cursor<? extends RealType> cursor =
			result.getImgPlus().localizingCursor();
		final long[] pos1 = new long[d1.numDimensions()];
		final long[] pos2 = new long[d2.numDimensions()];
		while (cursor.hasNext()) {
			cursor.fwd();
			cursor.localize(pos1);
			cursor.localize(pos2);
			ra1.setPosition(pos1);
			ra2.setPosition(pos2);
			final double sum = ra1.get().getRealDouble() + ra2.get().getRealDouble();
			cursor.get().setReal(sum);
		}

		return result;
	}

	/**
	 * Adds two datasets using the ImgLib OPS framework. This is a very succinct
	 * approach that does not require a loop. It is also automatically
	 * parallelized!
	 */
	private static Dataset addOps(final Dataset d1, final Dataset d2) {
		// BDZ TODO
		return null;
	}

	/** Loads a dataset selected by the user from a dialog box. */
	private static Dataset load(final IOService ioService) throws ImgIOException,
		IncompatibleTypeException
	{
		// ask the user for a file to open
		final JFileChooser chooser = new JFileChooser();
		final int returnVal = chooser.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) return null;
		final File file = chooser.getSelectedFile();

		// load the dataset
		return ioService.loadDataset(file.getAbsolutePath());
	}

	/**
	 * Creates a dataset with bounds constrained by the minimum of the two input
	 * datasets.
	 */
	private static Dataset create(final DatasetService datasetService,
		final Dataset d1, final Dataset d2)
	{
		final int dimCount = Math.min(d1.numDimensions(), d2.numDimensions());
		final long[] dims = new long[dimCount];
		final AxisType[] axes = new AxisType[dimCount];
		for (int i = 0; i < dimCount; i++) {
			dims[i] = Math.min(d1.dimension(i), d2.dimension(i));
			axes[i] = d1.numDimensions() > i ? d1.axis(i) : d2.axis(i);
		}
		return datasetService.create(new FloatType(), dims, "result", axes);
	}

}

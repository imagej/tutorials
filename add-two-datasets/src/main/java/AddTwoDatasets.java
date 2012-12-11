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
import imagej.ui.UIService;

import java.io.File;

import javax.swing.JFileChooser;

import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.Img;
import net.imglib2.io.ImgIOException;
import net.imglib2.meta.AxisType;
import net.imglib2.ops.img.ImageCombiner;
import net.imglib2.ops.operation.BinaryOperation;
import net.imglib2.ops.operation.img.binary.ImgCombine;
import net.imglib2.ops.operation.real.binary.RealAdd;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

/** Adds two datasets using the ImgLib2 framework. */
public class AddTwoDatasets {

	public static void main(final String... args) throws Exception {
		// create the ImageJ application context with all available services
		final ImageJ context = new ImageJ();

		UIService uiService = context.getService(UIService.class);
		uiService.createUI(); 

		// load two datasets
		final IOService ioService = context.getService(IOService.class);
		final Dataset dataset1 = load(ioService);
		final Dataset dataset2 = load(ioService);

		if (dataset1.numDimensions() != dataset2.numDimensions()) {
			uiService.showDialog(
				"Input datasets must have the same number of dimensions.");
			return;
		}
		
		// add them together
		final DatasetService datasetService =
			context.getService(DatasetService.class);
		final Dataset result1 = addRandomAccess(datasetService, dataset1, dataset2);
		final Dataset result2 =
				addOpsSerial(datasetService, dataset1, dataset2, new FloatType());
		final Dataset result3 =
				addOpsParallel(datasetService, dataset1, dataset2, new FloatType());

		// display the results
		final DisplayService displayService =
			context.getService(DisplayService.class);
		displayService.createDisplay(dataset1.getName(), dataset1);
		displayService.createDisplay(dataset2.getName(), dataset2);
		displayService.createDisplay("Result: random access", result1);
		displayService.createDisplay("Result: serial OPS", result2);
		displayService.createDisplay("Result: parallel OPS", result3);
	}

	/**
	 * Adds two datasets using a loop with an ImgLib cursor. This is a very
	 * powerful approach but requires a verbose loop.
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Dataset addRandomAccess(final DatasetService datasetService,
		final Dataset d1, final Dataset d2)
	{
		final Dataset result = create(datasetService, d1, d2, new FloatType());

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
	 * approach that does not require a loop. This version is designed for small
	 * processing jobs and is not automatically parallelized.
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	private static <T extends RealType<T> & NativeType<T>>
	Dataset addOpsSerial(DatasetService dss, Dataset d1, Dataset d2, T outType) {
		final Dataset output = create(dss, d1, d2, outType);
		final Img img1 = d1.getImgPlus();
		final Img img2 = d2.getImgPlus();
		final Img outputImg = output.getImgPlus();
		final BinaryOperation addOp = new RealAdd();
		final ImgCombine combiner = new ImgCombine(addOp);
		combiner.compute(img1, img2, outputImg);
		return output;
	}

	/**
	 * Adds two datasets using the ImgLib OPS framework. This is a very succinct
	 * approach that does not require a loop. This version is automatically
	 * parallelized!
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	private static <T extends RealType<T> & NativeType<T>>
	Dataset addOpsParallel(DatasetService dss, Dataset d1, Dataset d2, T outType){
		final Dataset output = create(dss, d1, d2, outType);
		final Img img1 = d1.getImgPlus();
		final Img img2 = d2.getImgPlus();
		final Img outputImg = output.getImgPlus();
		final BinaryOperation addOp = new RealAdd();
		ImageCombiner.applyOp(addOp, img1, img2, outputImg);
		return output;
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
	private static <T extends RealType<T> & NativeType<T>>
	Dataset create(final DatasetService datasetService,
		final Dataset d1, final Dataset d2, final T type)
	{
		final int dimCount = Math.min(d1.numDimensions(), d2.numDimensions());
		final long[] dims = new long[dimCount];
		final AxisType[] axes = new AxisType[dimCount];
		for (int i = 0; i < dimCount; i++) {
			dims[i] = Math.min(d1.dimension(i), d2.dimension(i));
			axes[i] = d1.numDimensions() > i ? d1.axis(i) : d2.axis(i);
		}
		return datasetService.create(type, dims, "result", axes);
	}

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.io.File;
import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.axis.AxisType;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.ops.img.ImageCombiner;
import net.imglib2.ops.operation.BinaryOperation;
import net.imglib2.ops.operation.img.binary.ImgCombine;
import net.imglib2.ops.operation.real.binary.RealAdd;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.command.Command;
import org.scijava.command.Previewable;
import org.scijava.log.LogService;
import org.scijava.app.StatusService;
import org.scijava.ItemIO;
import org.scijava.ItemVisibility;
import net.imagej.DatasetService;
import io.scif.services.DatasetIOService;

/**
 * Adds two datasets using the ImgLib2 framework.
 */
@Plugin(type = Command.class, headless = true,
        menuPath = "Tutorials>Add Two Datasets")
public class AddTwoDatasets implements Command, Previewable {

    @Parameter
    private LogService log;

    @Parameter
    private StatusService statusService;

    @Parameter
    private DatasetService datasetService;
    
    @Parameter
    private DatasetIOService datasetIOService;

    @Parameter(visibility = ItemVisibility.MESSAGE)
    private final String header = "This demonstration adds two datasets";

    @Parameter(label = "dataset 1")
    private File file1;

    @Parameter(label = "dataset 2")
    private File file2;

    @Parameter(label = "Result 1", type = ItemIO.OUTPUT)
    private Dataset result1;
    
    @Parameter(label = "Result 2", type = ItemIO.OUTPUT)
    private Dataset result2;
        
    @Parameter(label = "Result 3", type = ItemIO.OUTPUT)
    private Dataset result3;

    public static void main(final String... args) throws Exception {
        // create the ImageJ application context with all available services
        final ImageJ ij = net.imagej.Main.launch(args);

        ij.command().run(AddTwoDatasets.class, true);
    }

    @Override
    public void run() {
        // add them together
        Dataset dataset1, dataset2;
        try {
            dataset1 = datasetIOService.open(file1.getAbsolutePath());
        }
        catch (IOException e) {
            log.error(e);
            return;
        }
        try {
            dataset2 = datasetIOService.open(file2.getAbsolutePath());
        }
        catch (IOException e) {
            log.error(e);
            return;
        }
        if (dataset1.numDimensions() != dataset2.numDimensions()) {
            log.error("Input datasets must have the same number of dimensions.");
            return;
        }
        result1 = addRandomAccess(dataset1, dataset2);
        result2 = addOpsSerial(dataset1, dataset2, new FloatType());
	result3 = addOpsParallel(dataset1, dataset2, new FloatType());
    }
    
    @Override
    public void cancel() {
        log.info("Cancelled");
    }
    
    @Override
    public void preview() {
        log.info("previews AddTwoDatasets");
        statusService.showStatus(header);
    }
        
    /**
     * Adds two datasets using a loop with an ImgLib cursor. This is a very
     * powerful approach but requires a verbose loop.
     */
    @SuppressWarnings({"rawtypes"})
    private Dataset addRandomAccess(final Dataset d1,
            final Dataset d2) {
        final Dataset result = create(d1, d2, new FloatType());

        // sum data into result dataset
        final RandomAccess<? extends RealType> ra1
                = d1.getImgPlus().randomAccess();
        final RandomAccess<? extends RealType> ra2
                = d2.getImgPlus().randomAccess();
        final Cursor<? extends RealType> cursor
                = result.getImgPlus().localizingCursor();
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
    @SuppressWarnings({"rawtypes", "unchecked"})
    private <T extends RealType<T> & NativeType<T>> Dataset addOpsSerial(
            final Dataset d1, final Dataset d2, final T outType) {
        final Dataset output = create(d1, d2, outType);
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
    @SuppressWarnings({"rawtypes", "unchecked"})
    private <T extends RealType<T> & NativeType<T>> Dataset
            addOpsParallel(final Dataset d1, final Dataset d2,
                    final T outType) {
        final Dataset output = create(d1, d2, outType);
        final Img img1 = d1.getImgPlus();
        final Img img2 = d2.getImgPlus();
        final Img outputImg = output.getImgPlus();
        final BinaryOperation addOp = new RealAdd();
        ImageCombiner.applyOp(addOp, img1, img2, outputImg);
        return output;
    }
    /**
     * Creates a dataset with bounds constrained by the minimum of the two input
     * datasets.
     */
    private <T extends RealType<T> & NativeType<T>> Dataset create(
            final Dataset d1, final Dataset d2, final T type) {
        final int dimCount = Math.min(d1.numDimensions(), d2.numDimensions());
        final long[] dims = new long[dimCount];
        final AxisType[] axes = new AxisType[dimCount];
        for (int i = 0; i < dimCount; i++) {
            dims[i] = Math.min(d1.dimension(i), d2.dimension(i));
            axes[i] = d1.numDimensions() > i ? d1.axis(i).type() : d2.axis(i).type();
        }
        return datasetService.create(type, dims, "result", axes);
    }

}

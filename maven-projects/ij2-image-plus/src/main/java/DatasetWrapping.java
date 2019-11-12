/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

import ij.ImagePlus;
import ij.ImageStack;
import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imagej.axis.CalibratedAxis;
import net.imagej.ops.Op;
import net.imagej.ops.OpEnvironment;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import org.scijava.AbstractContextual;
import org.scijava.ItemIO;
import org.scijava.convert.ConvertService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import java.util.stream.IntStream;

/**
 * An example class to test wrapping and unwrapping of a ImageJ2 Dataset. An Op
 * which inverts the colors of the given image.
 *
 * Internally the plugin operates with IJ1 style ImagePlus, but externally it
 * operates with IJ2 Datasets
 *
 * How to run via the Java API: 1) Create an instance of the class 2) Call
 * setContext with e.g. ImageJ#getContext() 3) Call setDataset 4) Call run 5)
 * Call getDataset to get the result
 *
 * How to run via an opService e.g. ImageJ.op() 1) Create an instance of ImageJ
 * 2) Call final Object outputDataset = ij.op().run("datasetExample",
 * inputDataset) 3) Object outputDataset contains the resulting dataset
 *
 * The class AbstractContextual is inherited so that you can run this class via
 * Java API. When you run it via an opService the service fields are populated
 * automatically.
 *
 * @author Richard Domander
 */
@Plugin(type = Op.class, name = "datasetExample", menuPath = "Plugins>Examples>Dataset")
public class DatasetWrapping extends AbstractContextual implements Op {
    private ImagePlus image = null;

    @Parameter
    private ConvertService convertService = null;

    @Parameter
    private DatasetService datasetService = null;

    @Parameter(type = ItemIO.BOTH)
    private Dataset dataset = null;

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        checkDataset(dataset);
        this.dataset = dataset;
        setImagePlus();
    }

    @Override
    public OpEnvironment ops() {
        return null;
    }

    @Override
    public void setEnvironment(OpEnvironment opEnvironment) {
    }

    @Override
    public void run() {
        checkNotNull(convertService, "Missing services - did you remember to call setContext?");
        checkNotNull(datasetService, "Missing services - did you remember to call setContext?");

        if (image == null) {
            // the plugin is (probably) run from an opService, check the dataset
            // obtained as @Parameter
            setDataset(dataset);
        }

        makeNegative();

        ImgPlus<UnsignedByteType> imgPlus = ImagePlusAdapter.wrapImgPlus(image);
        dataset = datasetService.create(imgPlus);
    }

    // region -- Utility methods --

    /** Run the plugin via Java API */
    public static void main(final String... args) throws Exception {
        int width = 320;
        int height = 200;
        int depth = 10;

        // Create an instance of ImageJ
        final ImageJ ij = new ImageJ();

        // NB if you show the UI after setting the Dataset, an the program
        // automatically shows the image
        // This side effect has been reported
        ij.ui().showUI();

        // Create a white monochrome dataset for the Plugin
        final Dataset inputDataset = createMonochromeDataset(ij, width, height, depth, 0xFF);

        final DatasetWrapping datasetExample = new DatasetWrapping();

        // Set the context for the plugin to populate service @Parameters
        datasetExample.setContext(ij.getContext());

        try {
            datasetExample.setDataset(inputDataset);
            datasetExample.run();
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // Display the image the plugin produced
        Dataset outputDataset = datasetExample.getDataset();
        ij.ui().show(outputDataset);
    }

    public static Dataset createMonochromeDataset(final ImageJ ijInstance, final int width, final int height,
                                                  final int depth, final int color) {
        final long[] dims = {width, height, depth};
        final AxisType[] axisTypes = {Axes.X, Axes.Y, Axes.Z};
        Dataset dataset = ijInstance.dataset().create(new UnsignedByteType(), dims, "Test image", axisTypes);

        IntStream.range(0, depth).forEach(i -> {
            final byte[] data = new byte[width * height];
            IntStream.range(0, data.length).forEach(j -> data[j] = (byte) color);
            dataset.setPlane(i, data);
        });

        return dataset;
    }
    // endregion

    // region -- Helper methods --
    private void checkNotNull(final Object object, final String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }

    private void checkArgument(final boolean argument, final String message) {
        if (!argument) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Check whether the Op can operate with the given dataset
     *
     * @throws NullPointerException     if dataset == null
     * @throws IllegalArgumentException if the dataset has wrong number of dimensions
     * @throws IllegalArgumentException if the dataset cannot be converted into an ImagePlus
     */
    private void checkDataset(final Dataset dataset) {
        checkNotNull(dataset, "Dataset cannot be null");
        checkDatasetDimensions(dataset);
        checkArgument(convertService.supports(dataset, ImagePlus.class), "Cannot convert given dataset");
    }

    private void checkDatasetDimensions(final Dataset dataset) {
        checkArgument(dataset.numDimensions() == 3, "The plugin is meant only for 3D images");
        CalibratedAxis axes[] = new CalibratedAxis[3];
        dataset.axes(axes);
        for (CalibratedAxis axis : axes) {
            checkArgument(axis.type().isSpatial(), "Unexpected dimension");
        }
    }

    /**
     * Inverts the value of each bit in each pixel
     */
    private void makeNegative() {
        final int depth = image.getNSlices();
        final ImageStack stack = image.getStack();

        IntStream.rangeClosed(1, depth).parallel().forEach(z -> {
            byte pixels[] = (byte[]) stack.getPixels(z);
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = (byte) (pixels[i] ^ 0xFF);
            }
        });
    }

    private void setImagePlus() {
        image = convertService.convert(dataset, ImagePlus.class);
    }
    // endregion
}


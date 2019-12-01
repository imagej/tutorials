package howto.processing;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imagej.ops.Ops;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Intervals;

import java.io.IOException;

/**
 * How to apply a threshold to an image stack slice by slice
 *
 * @author hadmin
 * @author stelfrich
 * @author ctrueden
 * @author imagejan
 * @author haesleinhuepf
 */
public class SliceBySliceThreshold {

    /**
     * Apply a threshold slice by slice.
     */
    private static void firstSolution() throws IOException {
        // Start an ImageJ instance
        ImageJ ij = new ImageJ();

        // show the main window
        ij.ui().showUI();

        OpService ops = ij.op();

        Dataset data = (Dataset) ij.io().open(Object.class.getResource("/confocal-series.tif").getPath());

        ImgPlus<? extends RealType<?>> img = data.getImgPlus();
        long width = data.dimension(data.dimensionIndex(Axes.X));
        long height = data.dimension(data.dimensionIndex(Axes.Y));
        long depth = data.dimension(data.dimensionIndex(Axes.Z));

        // crop a channel
        ImgPlus c0 = (ImgPlus) ops.transform().crop(img, Intervals.createMinMax(0, 0, 0, 0, width - 1, height - 1, 0, depth - 1));
        c0.setName("c0");


        // create the otsu op
        Op otsu = ops.op(Ops.Threshold.Otsu.class, data.getImgPlus());

        //# create memory for the thresholded image
        Img<BitType> thresholded = ops.create().img(data.getImgPlus(), new BitType());

        // call threshold op slice-wise for the defined axes, in this case [xDim,yDim] means process the
        // first two axes (x and y)

        //RandomAccessibleInterval slice = (RandomAccessibleInterval<O>) ops.slice(thresholded, data.getImgPlus(), otsu, new long[]{width, height});

        // try again with [xDim, yDim, zDim] is the result different? Why?
        // create an ImgPlus using the thresholded img, copy meta data from the input
        ImgPlus thresholdedPlus = new ImgPlus(thresholded, data.getImgPlus(), true);

        // set a new name to avoid confusion
        thresholdedPlus.setName("Thresholded");

        // show result
        ij.ui().show(thresholdedPlus);
    }


    public static void main(String...args) throws IOException {

        firstSolution();
    }
}

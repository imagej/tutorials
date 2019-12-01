package howto.processing;

import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.Point;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.region.hypersphere.HyperSphere;
import net.imglib2.algorithm.region.hypersphere.HyperSphereCursor;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

import java.io.IOException;

/**
 * How to convolve an image.
 *
 * @author hadmin
 * @author stelfrich
 * @author ctrueden
 * @author imagejan
 * @author haesleinhuepf
 */
public class Convolution {

    /**
     * This example takes an input image, applies a gaussian filter
     * to it, and then performs convolution with a custom kernel.
     */
    private static void firstSolution() throws IOException {
        // Start an ImageJ instance
        ImageJ ij = new ImageJ();

        // show the main window
        ij.ui().showUI();

        OpService ops = ij.op();

        Img data = (Img) ij.io().open(Object.class.getResource("/blobs.png").getPath());

        // Convert the input image
        Img img32 = ops.convert().int32(data);

        // Apply the gaussian filter
        RandomAccessibleInterval filtered = ops.filter().gauss(img32, new double[]{4.0, 4.0});

        // Create the kernel
        RandomAccessibleInterval<DoubleType> kernel = ops.create().kernel(
                new double[][]{
                        {0, 1, 0},
                        {1, -4, 1},
                        {0, 1, 0}
                }, new DoubleType());

        // Perform convolution
        RandomAccessibleInterval result = ops.filter().convolve(filtered, kernel);

        // show result
        ij.ui().show(result);
    }


    public static void main(String...args) throws IOException {

        firstSolution();
    }
}

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
import net.imglib2.type.numeric.real.DoubleType;

import java.io.IOException;

/**
 * How to use create and convolve points
 *
 * @author hadmin
 * @author stelfrich
 * @author ctrueden
 * @author imagejan
 * @author haesleinhuepf
 */
public class CreateAndConvolvePoints {

    private static void firstSolution() {
        // Start an ImageJ instance
        ImageJ ij = new ImageJ();

        // show the main window
        ij.ui().showUI();

        OpService ops = ij.op();

        int xSize = 128;
        int ySize = 128;
        int zSize = 128;
        ImgPlus phantom;
        ImgPlus convolved;

        // create an empty image
        phantom = ops.create().imgPlus(ops.create().img(new long[]{xSize, ySize, zSize}));

        // use the randomAccess interface to place points in the image
        RandomAccess<DoubleType> randomAccess = phantom.randomAccess();

        // set two pixels to 200
        randomAccess.setPosition(new long[]{xSize / 2, ySize / 2, zSize / 2});
        randomAccess.get().setReal(255.0);
        randomAccess.setPosition(new long[]{xSize / 4, ySize / 4, zSize / 4});
        randomAccess.get().setReal(255.0);

        // create a hypersphere and set the pixels in there to 16
        Point location = new Point(phantom.numDimensions());
        location.setPosition(new long[]{3 * xSize / 4, 3 * ySize / 4, 3 * zSize / 4});
        HyperSphere<DoubleType> hyperSphere = new HyperSphere(phantom, location, 5);

        // use a cursor to go through the pixels in the hypersphere
        HyperSphereCursor<DoubleType> cursor = hyperSphere.cursor();
        while (cursor.hasNext()) {
            cursor.next().setReal(16);
        }
        phantom.setName("phantom");

        // create psf using the gaussian kernel op (alternatively PSF could be an input to the script)
        RandomAccessibleInterval<DoubleType> pointSpreadFunction = ops.create().kernelGauss(5, 5, 5);

        // convolve phantom with a given point spread function
        convolved = ops.create().imgPlus((Img)ops.filter().convolve(phantom, pointSpreadFunction));
        convolved.setName("convolved");


        // show results
        ij.ui().show(phantom);
        ij.ui().show(convolved);

    }


    public static void main(String...args) throws IOException, InterruptedException {

        firstSolution();
    }
}

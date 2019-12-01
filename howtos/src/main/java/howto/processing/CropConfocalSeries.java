package howto.processing;


import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.ops.OpService;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.Intervals;
import org.scijava.command.CommandService;
import org.scijava.log.LogService;
import org.scijava.ui.UIService;

import java.io.IOException;

/**
 * How to crop and split image stacks
 *
 * @author hadmin
 * @author stelfrich
 * @author ctrueden
 * @author imagejan
 * @author haesleinhuepf
 */
public class CropConfocalSeries {


    /**
     * Crop and split confocal series
     */
    private static void firstSolution() throws IOException {
        // Start an ImageJ instance
        ImageJ ij = new ImageJ();

        // show the main window
        ij.ui().showUI();

        // retrieve necessary services
        OpService ops = ij.op();

        // load data set
        Dataset data = (Dataset) ij.io().open("src/main/resources/confocal-series.tif");
        ImgPlus c0;
        ImgPlus z12;
        ImgPlus c0z12;
        ImgPlus roiC0z12;

        // first take a look at the size and type of each dimension
        for (int d = 0; d < data.numDimensions(); d++) {
            System.out.println("axis d: type: " + data.axis(d).type() + " length: " + data.dimension(d));
        }
        ImgPlus<? extends RealType<?>> img = data.getImgPlus();

        long xLen = data.dimension(data.dimensionIndex(Axes.X));
        long yLen = data.dimension(data.dimensionIndex(Axes.Y));
        long zLen = data.dimension(data.dimensionIndex(Axes.Z));
        long cLen = data.dimension(data.dimensionIndex(Axes.CHANNEL));

        // crop a channel
        c0= (ImgPlus) ops.transform().crop(img, Intervals.createMinMax(0, 0, 0,0,xLen-1, yLen-1, 0, zLen-1));
        c0.setName("c0");

        // crop both channels at z=12
        z12= (ImgPlus) ops.transform().crop(img, Intervals.createMinMax(0,0,0,12, xLen-1, yLen-1, cLen-1, 12));
        z12.setName("z12");

        // crop channel 0 at z=12
        c0z12= (ImgPlus) ops.transform().crop(img, Intervals.createMinMax(0,0,0,12, xLen-1, yLen-1, 0, 12));
        c0z12.setName("c0z12");

        // crop an roi at channel 0, z=12
        roiC0z12= (ImgPlus) ops.transform().crop(img, Intervals.createMinMax(150,150,0,12, 200, 200, 0, 12));
        roiC0z12.setName("roiC0z12");

        // show original
        ij.ui().show(data);

        // show results
        ij.ui().show(c0);
        ij.ui().show(z12);
        ij.ui().show(c0z12);
        ij.ui().show(roiC0z12);

    }


    public static void main(String...args) throws IOException {

        firstSolution();
    }
}

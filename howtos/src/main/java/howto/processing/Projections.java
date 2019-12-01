package howto.processing;

import edu.mines.jtk.sgl.Axis;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imagej.ops.Ops;
import net.imglib2.IterableInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.basictypeaccess.array.DoubleArray;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Intervals;
import org.scijava.command.CommandService;
import org.scijava.log.LogService;
import org.scijava.ui.UIService;

import java.io.IOException;

/**
 * How to use ImageJ Ops for making max and sum projections
 *
 * @author hadmin
 * @author stelfrich
 * @author ctrueden
 * @author imagejan
 * @author haesleinhuepf
 */
public class Projections {

    /**
     * Getting Ops to apply them
     */
    public static void firstSolution() throws IOException, InterruptedException {

        // Start an ImageJ instance
        ImageJ ij = new ImageJ();

        // show the main window
        ij.ui().showUI();

        // retrieve necessary services
        OpService ops = ij.op();
        UIService ui = ij.ui();

        Dataset data = (Dataset) ij.io().open("src/main/resources/confocal-series.tif");

        ImgPlus<? extends RealType<?>> img = data.getImgPlus();
        long width = data.dimension(data.dimensionIndex(Axes.X));
        long height = data.dimension(data.dimensionIndex(Axes.Y));
        long depth = data.dimension(data.dimensionIndex(Axes.Z));
        long numChannels = data.dimension(data.dimensionIndex(Axes.CHANNEL));

        // crop a channel
        ImgPlus c0 = (ImgPlus) ops.transform().crop(img, Intervals.createMinMax(0, 0, 0, 0, width - 1, height - 1, 0, depth - 1));
        c0.setName("c0");

        // get the dimension to project
        int pDim = data.dimensionIndex(Axes.Z);

        // generate the projected dimension
        int[] projectedDimensions = new int[]{
                data.dimensionIndex(Axes.X),
                data.dimensionIndex(Axes.Y)
        };

        // create memory for projections
        Img<DoubleType> maxProjection = ops.create().img(projectedDimensions);
        Img<DoubleType> sumProjection = ops.create().img(projectedDimensions);

        // use op service to get the max op
        Op maxOp = ops.op(Ops.Stats.Max.class, data);
        // use op service to get the sum op
        Op sumOp = ops.op(Ops.Stats.Sum.class, sumProjection.firstElement(), data);

        // call the project op passing
        // maxProjection: img to put projection in
        // image: img to project
        // op: the op used to generate the projection (in this case "max")
        // dimensionToProject: the dimension to project

        //ops.transform().project(maxProjection, data, maxOp, pDim);

        // project again this time use sum projection

        //ops.transform().project(sumProjection, data, sumOp, pDim)


    }

    public static void main(String...args) throws IOException, InterruptedException {

        firstSolution();

    }
}

package howto.processing;

import net.imagej.ImageJ;
import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imglib2.IterableInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.basictypeaccess.array.DoubleArray;
import net.imglib2.type.numeric.real.DoubleType;
import org.scijava.command.CommandService;
import org.scijava.log.LogService;
import org.scijava.ui.UIService;

import java.io.IOException;

/**
 * How to use ImageJ Ops for processing
 *
 * @author hadmin
 * @author stelfrich
 * @author ctrueden
 * @author imagejan
 * @author haesleinhuepf
 */
public class UseOps {

    /**
     * Exploring and using ops for processing numbers and images
     */
    public static void firstSolution() throws IOException, InterruptedException {

        // Start an ImageJ instance
        ImageJ ij = new ImageJ();

        // show the main window
        ij.ui().showUI();

        // retrieve necessary services
        OpService opService = ij.op();
        CommandService commandService = ij.command();
        LogService logService = ij.log();
        UIService ui = ij.ui();

        // how many ops?
        final int opCount = commandService.getCommandsOfType(Op.class).size();
        logService.info(opCount + " ops are available");

        // learn about an op
        logService.info( opService.help("math.add") );

        // add two numbers
        logService.info("What is 2 + 5? " + opService.math().add(2, 5));

        // create a new blank image
        long[] dims = new long[]{150, 100};
        Img<DoubleType> blank = opService.create().img(dims);

        // fill in the image with a sinusoid using a formula
        String formula = "10 * (Math.cos(0.3 * p[0]) + Math.sin(0.3 * p[1]))";
        IterableInterval<DoubleType> sinusoid = opService.image().equation(blank, formula);

        // add a constant value to an image
        opService.math().add((ArrayImg<DoubleType, DoubleArray>) sinusoid, 13.0);

        // generate a gradient image using a formula
        IterableInterval<DoubleType> gradient = opService.image().equation(opService.create().img(dims), "p[0] + p[1]");

        // add the two images
        Object composite = opService.run("math.add", sinusoid, gradient);

        // dump the image to the console
        String ascii = opService.image().ascii((IterableInterval)composite);
        logService.info("Composite image:\n" + ascii);

        // show the image in a window
        ui.show("composite", composite);

        // execute an op on every pixel of an image
        Op addOp = opService.op("math.add", DoubleType.class, new DoubleType(10000.0));
        Object compositePlus = opService.create().img((ArrayImg<DoubleType, DoubleArray>) composite);
        opService.run("map", compositePlus, composite, addOp);
        ui.show("composite plus", compositePlus);

        // 1. Note that the two composite images look the same...
        //    but mouse over each image to inspect their pixel values!
        // 2. Use Window > Show Console to see the output from log.info.

    }

    public static void main(String...args) throws IOException, InterruptedException {

        firstSolution();
    }
}

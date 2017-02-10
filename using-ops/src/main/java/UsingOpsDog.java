/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import net.imagej.display.ImageDisplayService;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.display.ImageDisplay;
import net.imagej.ops.OpService;
import net.imagej.ops.convert.RealTypeConverter;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.FloatType;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

/**
 * This tutorial shows how to use the ImageJ Ops DoG filter, and how to use
 * ImageJ Ops normalizeScale op to do image type conversion.
 *
 * A main method is provided so this class can be run directly from Eclipse (or
 * any other IDE).
 *
 * Also, because this class implements Command and is annotated as an @Plugin,
 * it will show up in the ImageJ menus: under Tutorials>Load and Display
 * Dataset, as specified by the menuPath field of the @Plugin annotation.
 */
@Plugin(type = Command.class, menuPath = "Tutorials>DoG Filtering")
public class UsingOpsDog<T extends RealType<T> & NativeType<T>> implements Command {

    /*
     * This @Parameter is for Image Display service.
     * The context will provide it automatically when this command is created.
     */
    @Parameter
    private ImageDisplayService imageDisplayService;

    /*
     * This @Parameter is for ImageJ Ops service. The
     * context will provide it automatically when this command is created.
     */
    @Parameter
    private OpService opService;

    @Parameter(type = ItemIO.INPUT)
    private ImageDisplay displayIn;

    /*
     * This command will produce an image that will automatically be shown by
     * the framework. Again, this command is "UI agnostic": how the image is
     * shown is not specified here.
     */
    //@Parameter(type = ItemIO.INPUT)
    //private Dataset imageDataset;
    @Parameter(type = ItemIO.OUTPUT)
    private Img<T> output;

    /*
     * The run() method is where we do the actual 'work' of the command. In this
     * case, it is fairly trivial because we are simply calling ImageJ Services.
     */
    @Override
    public void run() {
        final Dataset input = imageDisplayService.getActiveDataset(displayIn);
        Img<T> image = (Img<T>) input.getImgPlus();

        // Convert image to FloatType for better numeric precision
        Img<FloatType> converted = opService.convert().float32(image);

        // Create the filtering result
        Img<FloatType> dog = opService.create().img(converted);

        // Do the DoG filtering using ImageJ Ops
        opService.filter().dog(dog, converted, 1.0, 1.25);

        // Create a NormalizeScaleRealTypes op
        RealTypeConverter<FloatType, T> scale_op;
        scale_op = (RealTypeConverter<FloatType, T>) opService.op("convert.normalizeScale", dog.firstElement(), image.firstElement());

        // Create the output image
        output = opService.create().img(image);

        // Run the op to do type conversion for better displaying
        opService.convert().imageType(output, dog, scale_op);

        // You can also use the OpService to run the op
        // opService.run(Ops.Convert.ImageType.class, output, dog, scale_op);
    }

    /*
     * This main method is for convenience - so you can run this command
     * directly from Eclipse (or other IDE).
     *
     * It will launch ImageJ and then run this command using the CommandService.
     * This is equivalent to clicking "Tutorials>DoG Filtering" in the UI.
     */
    public static void main(final String... args) throws Exception {
        // Launch ImageJ as usual.
        final ImageJ ij = net.imagej.Main.launch(args);

        // Launch the "UsingOpsDog" command.
        ij.command().run(UsingOpsDog.class, true);
    }

}

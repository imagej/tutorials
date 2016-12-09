package net.enteryourdomainhere.enteryourprojectnamehere;


import java.io.File;

import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.RealType;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import net.imagej.Dataset;
import net.imagej.ImageJ;

/**
 *
 *
 * Author: ENTER YOUR NAME HERE
 *
 */
@Plugin(type = Command.class, menuPath = "Plugins>EnterYourPluginNameHere")
public class EnterYourPluginNameHere<T extends RealType<T>> implements Command
{
    //
    // Feel free to add more parameters here...
    //

    @Parameter
    private Dataset currentData;

    @Parameter
    private UIService uiService;

    @Parameter
    private OpService ops;

    @Override
    public void run() {
        Img<T> image = (Img<T>) currentData.getImgPlus();

        //
        // Enter image processing code here. The following is just an example
        //
        double sigma_in_pixel_units = 10;
        RandomAccessibleInterval<T> result = ops.filter().gauss(image, sigma_in_pixel_units);

        // display result
        uiService.show(result);
    }

    /**
     * This main function serves for development purposes. It allows you to run the plugin immediately out of 
     * your integrated development environment 
     *
     * @param args whatever, it's ignored
     * @throws Exception
     */
    public static void main(final String... args) throws Exception {
        // create the ImageJ application context with all available services
        final ImageJ ij = new ImageJ();
        ij.ui().showUI();

        // ask the user for a file to open
        final File file = ij.ui().chooseFile(null, "open");

        // load the dataset,
        System.out.println("hello1");
        final Dataset dataset = ij.scifio().datasetIO().open(file.getPath());
        System.out.println("hello2");

        // Show the image
        ij.ui().show(dataset);

        // invoke the plugins run-function for testing...
        ij.command().run(EnterYourPluginNameHere.class, true);
    }
}
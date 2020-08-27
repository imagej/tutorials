/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.metadata;

import java.io.IOException;

import io.scif.services.FormatService;
import io.scif.Format;
import io.scif.FormatException;
import io.scif.Metadata;
import io.scif.FieldPrinter;

import net.imagej.ImageJ;
import net.imagej.Dataset;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * This example illustrates how to access and display image metadata using the {@link FormatService} and
 * {@link FieldPrinter}.
 * <p>
 * We use the {@link FormatService} to determine the {@link Format} of the input image. Then, we parse
 * the metadata from the image file and use {@link FieldPrinter} to retrieve the metadata fields as strings.
 * </p>
 * <p>
 * An optional {@code formatMetadata} method is included to (hopefully) make the text more readable.
 * </p>
 */
@Plugin(type = Command.class, menuPath = "Image>Show Metadata")
public class GetMetadata implements Command {

    // -- Needed services --

    // for determining the Format of the input image
    @Parameter
    private FormatService formatService;

    // for logging errors
    @Parameter
    private LogService log;

    // -- Inputs and outputs to the command --

    // input image
    @Parameter
    private Dataset img;

    // output metadata string
    @Parameter(label = "Metadata", type = ItemIO.OUTPUT)
    private String mString;

    @Override
    public void run() {
        // we need the file path to determine the file format
        final String filePath = img.getSource();

        // catch any Format or IO exceptions
        try {

            // determine the Format based on the extension and, if necessary, the source data
            Format format = formatService.getFormat(filePath);

            // create an instance of the associated Parser and parse metadata from the image file
            Metadata metadata = format.createParser().parse(filePath);

            // use FieldPrinter to traverse metadata tree and return as a String
            String metadataTree = new FieldPrinter(metadata).toString();

            // (optional) remove some of the tree formatting to make the metadata easier to read
            mString = formatMetadata(metadataTree);
        }
        catch (final FormatException | IOException e) {
            log.error(e);
        }

    }

    /**
     * This function makes the metadata easier to read by removing some of the tree formatting from FieldPrinter.
     * @param metadataTree raw metadata string returned by FieldPrinter().toString()
     * @return formatted version of metadataTree
     */
    private String formatMetadata(String metadataTree) {

        // remove ending braces | replace ", " between OME fields
        String tmp = metadataTree.replaceAll("(\\t+}\\n)|(,\\s)", "\n");

        // remove beginning braces | remove indenting
        return tmp.replaceAll("(\\t+\\{\\n)|(\\t+)", "");
    }

    /**
     * This main function serves for development purposes.
     * It allows you to run the plugin immediately out of
     * your integrated development environment (IDE).
     *
     * @param args unused
     * @throws Exception
     */
    public static void main(final String... args) throws Exception {
        // create the ImageJ application context with all available services
        final ImageJ ij = new ImageJ();
        ij.launch(args);

        // open a sample image
        final Dataset img = ij.scifio().datasetIO().open("http://imagej.net/images/FluorescentCells.jpg");

        // show the image
        ij.ui().show(img);

        // invoke the plugin
        ij.command().run(GetMetadata.class, true);

    }

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.metadata;

import io.scif.FieldPrinter;
import io.scif.Format;
import io.scif.Metadata;
import io.scif.services.FormatService;

import net.imagej.Dataset;
import net.imagej.ImageJ;

import org.scijava.ItemIO;
import org.scijava.io.location.Location;
import org.scijava.io.location.LocationService;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;

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
public class GetMetadata {

    // -- Needed services --

    // for determining the Format of the input image
    @Parameter
    private FormatService formatService;

    @Parameter
    private LocationService locationService;

    // for logging errors
    @Parameter
    private LogService log;

    // -- Inputs and outputs to the command --

    // output metadata string
    @Parameter(label = "Metadata", type = ItemIO.OUTPUT)
    private String mString;

    public static void run() throws Exception {
      ImageJ ij = new ImageJ();

      // open a sample image
      final Dataset img = ij.scifio().datasetIO().open("http://imagej.net/images/FluorescentCells.jpg");

      // we need the file path to determine the file format
      Location source = ij.get(LocationService.class).resolve(img.getSource());

      // catch any Format or IO exceptions
      // determine the Format based on the extension and, if necessary, the source data
      Format format = ij.scifio().format().getFormat(source);

      // create an instance of the associated Parser and parse metadata from the image file
      Metadata metadata = format.createParser().parse(source);

      // use FieldPrinter to traverse metadata tree and return as a String
      String metadataTree = new FieldPrinter(metadata).toString();

      // (optional) remove some of the tree formatting to make the metadata easier to read
      String mString = formatMetadata(metadataTree);

      // print out our precious metadata!
      ij.log().info(mString);

      // close out our ImageJ
      ij.dispose();
    }

    /**
     * This function makes the metadata easier to read by removing some of the tree formatting from FieldPrinter.
     * @param metadataTree raw metadata string returned by FieldPrinter().toString()
     * @return formatted version of metadataTree
     */
    private static String formatMetadata(String metadataTree) {

        // remove ending braces | replace ", " between OME fields
        String tmp = metadataTree.replaceAll("(\\t+}\\n)|(,\\s)", "\n");

        // remove beginning braces | remove indenting
        return tmp.replaceAll("(\\t+\\{\\n)|(\\t+)", "");
    }

    public static void main(String...args) throws Exception { run(); }
}

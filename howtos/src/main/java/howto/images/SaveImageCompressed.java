/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.images;

import io.scif.config.SCIFIOConfig;
import io.scif.formats.TIFFFormat;
import io.scif.services.DatasetIOService;

import java.io.IOException;
import java.nio.file.Files;

import net.imagej.Dataset;
import net.imagej.DefaultDataset;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imglib2.img.Img;

import org.scijava.io.location.FileLocation;

/**
 * How to save an image as LZW compressed TIFF
 *
 * @author Deborah Schmidt
 */
public class SaveImageCompressed {

	public static void run() throws IOException {

		ImageJ ij = new ImageJ();

		// open image from resource
		Img img = (Img) ij.io().open(Object.class.getResource("/blobs.png").getPath());

		// create temporary path to save image to
		FileLocation dest = new FileLocation(Files.createTempFile("img", ".tif").toFile());
		System.out.println("Saving image to " + dest);

		// create SCIFIO config to set compression algorithm
		SCIFIOConfig config = new SCIFIOConfig().writerSetCompression(TIFFFormat.Writer.COMPRESSION_LZW);

		// create dataset from image
		Dataset dataset = new DefaultDataset(ij.context(), new ImgPlus<>(img));

		// save image via DatasetIOService with SCIFIO config
		ij.get(DatasetIOService.class).save(dataset, dest, config);

		// load and show saved image
		Object savedImg = ij.io().open(dest.getFile().getAbsolutePath());
		ij.ui().show("saved image", savedImg);
	}

	public static void main(String...args) throws IOException {
		run();
	};

}

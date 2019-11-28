/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package howto.images;

import ij.ImagePlus;
import io.scif.img.IO;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.view.Views;

import java.io.IOException;

/**
 * How to convert between various image class representations
 *
 * @author Deborah Schmidt
 */
public class ConvertImageClasses {

	/**
	 * .. wrapping {@link Img} as {@link ImgPlus}:
	 */
	private static void imgToImgPlus() throws IOException {
		ImageJ ij = new ImageJ();
		Img img = ij.op().create().img(new int[]{10, 20, 3});
		ImgPlus imgPlus = new ImgPlus(img, "image", new AxisType[]{Axes.X, Axes.Y, Axes.CHANNEL});
		System.out.println(imgPlus);
	}

	/**
	 * .. converting {@link RandomAccessibleInterval} to {@link IterableInterval}:
	 */
	private static void raiToIterableInterval() throws IOException {
		ImageJ ij = new ImageJ();
		RandomAccessibleInterval rai = ij.op().create().img(new int[]{10, 20, 3});
		IterableInterval ii = Views.iterable(rai);
		System.out.println(ii);
	}

	/**
	 * .. converting {@link RandomAccessibleInterval} to {@link Img}:
	 */
	private static void raiToImg() {
		ImageJ ij = new ImageJ();
		RandomAccessibleInterval rai = ij.op().create().img(new int[]{10, 20, 3});
		Img img = (Img) rai;
		System.out.println(img);
	}

	/**
	 * .. converting {@link RandomAccessibleInterval} to IJ1 {@link ImagePlus}:
	 */
	private static void raiToImagePlus() {
		ImageJ ij = new ImageJ();
		RandomAccessibleInterval rai = ij.op().create().img(new int[]{10, 20, 3});
		ImagePlus imp = ImageJFunctions.wrap(rai, "image");
		System.out.println(imp);
	}

	/**
	 * .. converting IJ1 {@link ImagePlus} to {@link Img}:
	 */
	private static void imagePlusToImgPlus() {
		ImagePlus imp = new ImagePlus("https://samples.fiji.sc/blobs.png");
		Img img = ImageJFunctions.wrap(imp);
		System.out.println(img);
	}

	public static void main(String... args) throws IOException {
		imgToImgPlus();
		raiToIterableInterval();
		raiToImg();
		raiToImagePlus();
		imagePlusToImgPlus();
	}
}

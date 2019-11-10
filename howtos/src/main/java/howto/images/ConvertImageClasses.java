/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 *
 * This work was supported by the German Research Foundation (DFG) under the code JU3110/1-1 (Fiji Software Sustainability).
 */

package howto.images;

import ij.ImagePlus;
import io.scif.img.IO;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.view.Views;

/**
 * How to convert between various image class representations
 *
 * @author Deborah Schmidt
 */
public class ConvertImageClasses {

	/**
	 * .. wrapping {@link Img} as {@link ImgPlus}:
	 */
	private static void imgToImgPlus() {
		Img img = IO.openImgs("https://samples.fiji.sc/blobs.png").get(0);
		ImgPlus imgPlus = new ImgPlus(img, "blobs", new AxisType[]{Axes.X, Axes.Y, Axes.TIME});
		System.out.println(imgPlus);
	}

	/**
	 * .. converting {@link RandomAccessibleInterval} to {@link IterableInterval}:
	 */
	private static void raiToIterableInterval() {
		RandomAccessibleInterval rai = IO.openImgs("https://samples.fiji.sc/blobs.png").get(0);
		IterableInterval ii = Views.iterable(rai);
		System.out.println(ii);
	}

	/**
	 * .. converting {@link RandomAccessibleInterval} to {@link Img}:
	 */
	private static void raiToImg() {
		RandomAccessibleInterval rai = IO.openImgs("https://samples.fiji.sc/blobs.png").get(0);
		Img img = (Img) rai;
		System.out.println(img);
	}

	/**
	 * .. converting {@link RandomAccessibleInterval} to IJ1 {@link ImagePlus}:
	 */
	private static void raiToImagePlus() {
		RandomAccessibleInterval rai = IO.openImgs("https://samples.fiji.sc/blobs.png").get(0);
		ImagePlus imp = ImageJFunctions.wrap(rai, "blobs");
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

	public static void main(String... args) {
		imgToImgPlus();
		raiToIterableInterval();
		raiToImg();
		raiToImagePlus();
		imagePlusToImgPlus();
	}
}

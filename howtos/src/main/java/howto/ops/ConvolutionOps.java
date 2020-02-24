/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */
package howto.ops;

import net.imagej.ImageJ;
import net.imglib2.Point;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.region.hypersphere.HyperSphere;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.outofbounds.OutOfBoundsConstantValueFactory;
import net.imglib2.outofbounds.OutOfBoundsFactory;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.util.Util;
import net.imglib2.view.Views;

/** How to do convolution with ImageJ Ops. */
public class ConvolutionOps {

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		final int[] size = new int[] { 200, 200 };

		// create an input with a small sphere at the center
		final Img<FloatType> in = new ArrayImgFactory<FloatType>(new FloatType()).create(size);
		placeSphereInCenter(in);

		// show the image in a window
		ij.ui().show("input", in);

		final int[] kernelSize = new int[] { 3, 3 };
		final Img<FloatType> kernel = new ArrayImgFactory<FloatType>(new FloatType()).create(
			kernelSize);
		final RandomAccess<FloatType> kernelRa = kernel.randomAccess();
		// long[] borderSize = new long[] {1, 1};

		// Second derivative filter
		final int[] k = new int[] { 1, -2, 1, 2, -4, 2, 1, -2, 1 };

		int h = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				kernelRa.setPosition(new Point(i, j));
				kernelRa.get().set(k[h]);
				h++;
			}
		}

		// show the image in a window
		ij.ui().show("kernel", kernel);

		final Img<FloatType> out = new ArrayImgFactory<FloatType>(new FloatType()).create(in);

		final OutOfBoundsFactory<FloatType, RandomAccessibleInterval<FloatType>> obf =
			new OutOfBoundsConstantValueFactory<>(//
				Util.getTypeFromInterval(in).createVariable());

		// extend the input
		final RandomAccessibleInterval<FloatType> extendedIn = //
			Views.interval(Views.extend(in, obf), in);

		// extend the output
		final RandomAccessibleInterval<FloatType> extendedOut = //
			Views.interval(Views.extend(out, obf), out);

		// convolve
		ij.op().filter().convolve(extendedOut, extendedIn, kernel);

		// show the image in a window
		ij.ui().show("convolved", out);
	}

	/** Places a small sphere at the center of the image. */
	private static void placeSphereInCenter(final Img<FloatType> img) {
		final Point center = new Point(img.numDimensions());

		for (int d = 0; d < img.numDimensions(); d++)
			center.setPosition(img.dimension(d) / 2, d);

		final HyperSphere<FloatType> hyperSphere = //
			new HyperSphere<>(img, center, 10);

		for (final FloatType value : hyperSphere) {
			value.setReal(1);
		}
	}
}

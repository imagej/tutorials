/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package howto.images;

import net.imagej.ImageJ;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.type.numeric.real.FloatType;

/**
 * How to copy / duplicate an image
 *
 * @author Deborah Schmidt
 */
public class DuplicateImage {

	/**
	 * .. copy an {@link Img}:
	 */
	private static void copyImg() {
		Img img = new ArrayImgFactory<>(new FloatType()).create(100, 200, 50);
		Img imgCopy = img.copy();
		System.out.println(imgCopy);
	}

	/**
	 * .. copy a {@link RandomAccessibleInterval} using imagej-ops:
	 */
	private static void copyRaiUsingOps() {
		ImageJ ij = new ImageJ();
		RandomAccessibleInterval rai = new ArrayImgFactory<>(new FloatType()).create(100, 200, 50);
		RandomAccessibleInterval raiCopy = ij.op().copy().rai(rai);
		System.out.println(raiCopy);
	}

	public static void main(String... args) {
		copyImg();
		copyRaiUsingOps();
	}
}

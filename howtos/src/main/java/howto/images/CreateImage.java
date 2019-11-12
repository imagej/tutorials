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

import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imglib2.cache.img.DiskCachedCellImgFactory;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.numeric.real.FloatType;

/**
 * How to create an image
 *
 * @author Deborah Schmidt
 */
public class CreateImage {

	/**
	 * .. this will create an {@link Img} using an ImgLib2 factory:
	 */
	public static void createImgWithImgLib2() {
		Img img = new ArrayImgFactory<>(new FloatType()).create(20, 30, 40);
		System.out.println(img);
	}

	/**
	 * .. this will create an {@link Img} using imagej-ops:
	 */
	public static void createImgWithOps() {
		ImageJ ij = new ImageJ();
		Img<DoubleType> img = ij.op().create().img(new long[] { 20, 30, 40});
		System.out.println(img);
	}

	/**
	 * .. this will create an {@link Img<IntType>} with the dimensions of an existing {@link Img<DoubleType>}
	 */
	public static void createImgFromExistingDimensions() {
		ImageJ ij = new ImageJ();
		Img<DoubleType> existing = ij.op().create().img(new long[] { 20, 30, 40 });
		Img<IntType> img = ij.op().create().img(existing, new IntType());
		System.out.println(img);
	}
	/**
	 * .. this will create an {@link Img} cached to disk (e.g. in case it does not fit in memory)
	 */
	public static void createCachedImg() {
		Img img = new DiskCachedCellImgFactory<>(new FloatType()).create(1000, 2000, 500);
		System.out.println(img);
	}

	/**
	 * .. this will create an {@link ImgPlus} which can for example hold information about the axes of the image:
	 */
	public static void createImgPlus() {
		Img img = new ArrayImgFactory<>(new FloatType()).create(100, 200, 50);
		ImgPlus imgPlus = new ImgPlus(img, "raw", new AxisType[]{Axes.X, Axes.Y, Axes.TIME});
		System.out.println(imgPlus);
	}

	/**
	 * .. this will create an {@link ImgPlus} from {@link Img} using imagej-ops
	 */
	public static void createImgPlusWithOps() {
		ImageJ ij = new ImageJ();
		Img<DoubleType> img = ij.op().create().img(new long[] { 20, 30, 40 });
		ImgPlus<DoubleType> imgPlus = ij.op().create().imgPlus(img);
		System.out.println(imgPlus);
	}

	public static void main(String... args) {
		createImgWithImgLib2();
		createImgWithOps();
		createImgFromExistingDimensions();
		createCachedImg();
		createImgPlus();
		createImgPlusWithOps();
	}
}

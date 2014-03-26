/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.data.Dataset;
import imagej.ops.Op;
import net.imglib2.type.numeric.real.DoubleType;

/** How to use ImageJ Operations. */
public class UsingOps {

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		// add two numbers
		Integer seven = (Integer) ij.op().run("add", 2, 5);
		System.out.println("Expect seven: " + seven);

		Dataset data = ij.dataset().open("testImg&pixelType=double&lengths=512,512.fake");
		// add number to image
		ij.op().run("add", data.getImgPlus().getImg(), 13.0);
		ij.ui().show(data);

		Dataset moredata = ij.dataset().open("testImg2&pixelType=double&lengths=512,512.fake");
		// add two images
		ij.op().run("add", data.getImgPlus().getImg(), moredata.getImgPlus().getImg());
		// built-ins can be called directly
		ij.op().add(data.getImgPlus().getImg(), moredata.getImgPlus().getImg());
		ij.ui().show(data);

		Op addOp = ij.op().op("add", DoubleType.class, DoubleType.class, new DoubleType(5.0));
		// execute add op on every image pixel
		ij.op().map(data.getImgPlus().getImg(), data.getImgPlus().getImg(), addOp);
		ij.ui().show(data);
	}

}

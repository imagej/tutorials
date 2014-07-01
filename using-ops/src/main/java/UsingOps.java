/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import net.imagej.ImageJ;
import net.imagej.ops.Op;
import net.imglib2.type.numeric.real.DoubleType;

/** How to use ImageJ Operations. */
public class UsingOps {

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		// how many ops?
		final int opCount = ij.command().getCommandsOfType(Op.class).size();
		ij.log().info("Found " + opCount + " ops");

		// learn about an op
		ij.log().info(ij.op().help("add"));

		// add two numbers
		final Object seven = ij.op().add(2, 5);
		ij.log().info("What is 2 + 5? " + seven);

		// create a new blank image
		final long[] dims = {150, 100};
		final Object blank = ij.op().create(dims);

		// fill in the image with a sinusoid using a formula
		final String formula = "10 * (Math.cos(0.3*p[0]) + Math.sin(0.3*p[1]))";
		final Object sinusoid = ij.op().equation(blank, formula);

		// add a constant value to an image
		ij.op().add(sinusoid, 13.0);

		// generate a gradient image using a formula
		final Object gradient = ij.op().equation(ij.op().create(dims), "p[0]+p[1]");

		// add the two images
		final Object composite = ij.op().add(sinusoid, gradient);

		// dump the image to the console
		final Object ascii = ij.op().ascii(composite);
		ij.log().info("Composite image:\n" + ascii);

		// execute an op on every pixel of an image
		final Op addOp = ij.op().op("add", DoubleType.class, new DoubleType(5.0));
		ij.op().map(composite, composite, addOp);
	}

}

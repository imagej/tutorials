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

		ij.log().info("-------- How many ops? --------");
		final int opCount = ij.command().getCommandsOfType(Op.class).size();
		ij.log().info("Found " + opCount + " ops");

		ij.log().info("-------- Learn about an op --------");
		ij.log().info(ij.op().help("math.mul"));

		ij.log().info("-------- Add two numbers --------");
		final int seven = ij.op().math().add(2, 5);
		ij.log().info("What is 2 + 5? " + seven);

		ij.log().info("-------- Create a new blank image --------");
		final long[] dims = {150, 100};
		final Object blank = ij.op().create().img(dims);

		ij.log().info("-------- Fill in an image with a formula --------");
		final String formula = "10 * (Math.cos(0.3*p[0]) + Math.sin(0.3*p[1]))";
		final Object sinusoid = ij.op().image().equation(blank, formula);

		ij.log().info("-------- Add a constant value to an image --------");
		ij.op().math().add(sinusoid, 13.0);

		ij.log().info("-------- Generate gradient image using a formula --------");
		final Object gBlank = ij.op().create().img(dims);
		final Object gradient = ij.op().image().equation(gBlank, "p[0]+p[1]");

		ij.log().info("-------- Add two images --------");
		final Object composite = ij.op().math().add(sinusoid, gradient);

		ij.log().info("-------- Dump an image to the console --------");
		final Object ascii = ij.op().image().ascii(composite);
		ij.log().info("Composite image:\n" + ascii);

		ij.log().info("-------- Show the image in a window --------");
		ij.ui().show("composite", composite);

		ij.log().info("-------- Execute op on every pixel of an image --------");
		final Op addOp = ij.op().op("math.add", DoubleType.class, new DoubleType(5.0));
		ij.op().map(composite, composite, addOp);

		ij.log().info("-------- All done! --------");
	}

}

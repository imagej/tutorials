/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

import net.imagej.ImageJ;
import net.imagej.ops.Op;
import net.imagej.ops.special.computer.UnaryComputerOp;
import net.imglib2.IterableInterval;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * How to use ImageJ Operations.
 */
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
		final IterableInterval<DoubleType> blank = ij.op().create().img(dims);

		ij.log().info("-------- Fill in an image with a formula --------");
		final String formula = "10 * (Math.cos(0.3*p[0]) + Math.sin(0.3*p[1]))";
		final IterableInterval<DoubleType> sinusoid = ij.op().image().equation(blank, formula);

		ij.log().info("-------- Add a constant value to an image --------");
		final DoubleType d = new DoubleType(13.0);
		ij.op().math().add(sinusoid, d);

		ij.log().info("-------- Generate gradient image using a formula --------");
		final IterableInterval<DoubleType> gBlank = ij.op().create().img(dims);
		final IterableInterval<DoubleType> gradient = ij.op().image().equation(gBlank, "p[0]+p[1]");

		ij.log().info("-------- Add two images --------");
		final IterableInterval composite = ij.op().math().add(sinusoid, gradient);

		ij.log().info("-------- Dump an image to the console --------");
		final String ascii = ij.op().image().ascii(composite);
		ij.log().info("Composite image:\n" + ascii);

		ij.log().info("-------- Show the image in a window --------");
		ij.ui().show("composite", composite);

		ij.log().info("-------- Execute op on every pixel of an image --------");
		final Op addOp = ij.op().op("math.add", DoubleType.class, new DoubleType(5.0));
		final IterableInterval<DoubleType> mblank = ij.op().create().img(composite);
		ij.op().map(mblank, composite, (UnaryComputerOp<DoubleType, DoubleType>) addOp);

		ij.log().info("-------- All done! --------");
	}

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */
package howto.ops;

import net.imagej.ImageJ;
import net.imagej.ops.OpService;
import net.imagej.ops.Ops;
import net.imagej.ops.special.computer.BinaryComputerOp;
import net.imagej.ops.special.computer.Computers;
import net.imagej.ops.special.inplace.Inplaces;
import net.imglib2.IterableInterval;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.log.LogService;

/**
 * This tutorial shows how to use the "special" ImageJ Ops: computer, function,
 * inplace and hybrid.
 * 
 * @see net.imagej.ops.special.SpecialOp
 */
public class UsingSpecialOps {

	private static final int ITERATIONS = 1000;

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();
		final LogService log = ij.log();
		final OpService ops = ij.op();

		// -- Introduction --

		// This tutorial covers the so-called SPECIAL ops. These ops can be
		// classified into three main categories: COMPUTER, FUNCTION and INPLACE.

		// Every special op produces one primary output from some fixed number
		// of primary inputs. The number of such inputs for a given op is known
		// as its ARITY. The framework provides interfaces for three arities:
		//
		// - 1 input = UNARY e.g.: eight = math.sqrt(64)
		// - 2 inputs = BINARY e.g.: eight = math.add(3, 5)
		// - 0 inputs = NULLARY e.g.: zero = math.zero()
		//
		// Additional arities (e.g., ternary) are feasible, but not implemented.
		//
		// Special ops may also have any number of additional secondary inputs,
		// which do not affect the arity of the op. More on that below.

		// Let's try some example op calls, to illustrate the difference
		// between these various kinds of special ops.

		// -- Binary ops --

		log.info("================ BINARY OPS ================\n");

		// A BINARY op is one with two primary inputs, which produces an output.

		// Let's have some fun with images:

		log.info("-------- Create some demo images --------\n");

		final long w = 64, h = 16;

		final IterableInterval<DoubleType> sinusoid = createImage(ij, "sinusoid",
			w, h, "10 * (Math.cos(0.3*p[0]) + Math.sin(0.5*p[1]))");

		final IterableInterval<DoubleType> gradient = createImage(ij, "gradient",
			w, h, "p[0] + p[1]");

		final IterableInterval<DoubleType> circle = createImage(ij, "circle",
			w, h, "5 * Math.sqrt((p[0]-32)*(p[0]-32)/16 + (p[1]-8)*(p[1]-8))");

		final IterableInterval<DoubleType> diamond = createImage(ij, "diamond",
			w, h, "5 * (Math.abs(p[0]-32)/4 + Math.abs(p[1]-8))");

		// Suppose we want to add two images together:

		log.info("-------- Add two images using a BINARY FUNCTION --------\n");

		log.info("result = math.add(sinusoid, gradient)");

		IterableInterval<DoubleType> result = ops.math().add(sinusoid, gradient);
		printImage(ij, "result", result);

		// The above invocation calls a math.add FUNCTION, which allocates and
		// returns a _new_ output image, without mutating any of the input data.
		// This is great from a functional standpoint, but costs space and time.

		// Now suppose we already have a properly dimensioned output image
		// allocated and ready to go. In that case, it would wasteful to
		// call math.add as a function, only to then copy the data from the
		// newly allocated result back into our already-existing image.
		//
		// Much better would be to tell math.add to use our existing output
		// buffer directly. Fortunately, this approach is made possible by
		// COMPUTER ops, which require you to specify a preallocated output
		// buffer where the result will be stored:

		log.info("-------- Add two images using a BINARY COMPUTER --------\n");

		log.info("math.add(result, circle, gradient)");

		ops.math().add(result, circle, gradient);
		printImage(ij, "result", result);

		// In the above call, the same operation is performed, but the
		// output is written into the provided "result" buffer.
		//
		// Computers do have an important restriction: the output buffer MUST
		// NOT be the same reference as any of the inputs. Otherwise, the
		// computer would end up mutating the input data as a consequence of
		// writing to the output buffer. Hence, computers cannot be used to
		// calculate results "in place" -- that's what INPLACE ops are for.

		log.info("-------- Add two images using a BINARY INPLACE --------\n");

		log.info("math.add(result, sinusoid)  -- i.e. --  result += sinusoid");

		Inplaces.binary1(ops, Ops.Math.Add.class, result, sinusoid).run();
		printImage(ij, "result", result);

		// The above invocation differs from earlier: the Inplaces.binary1 method
		// requests a math.add binary op which mutates its first argument inplace.
		// A BinaryInplace1Op instance is returned, upon which we then call run().
		// Such ops can be used in other ways too; see "REUSING SPECIAL OPS" below.

		// -- Unary ops --

		log.info("================ UNARY AND NULLARY OPS ================\n");

		// In addition to binary ops (arity 2), there are also:
		//
		// - UNARY ops (arity 1) - one primary input, one primary output.
		// - NULLARY ops (arity 0) - no primary inputs, one primary output.
		//
		// Here are a couple of quick examples:

		log.info("-------- Take the square root using a UNARY COMPUTER --------\n");

		log.info("math.sqrt(number, 64)");

		final DoubleType number = new DoubleType();
		ops.math().sqrt(number, new DoubleType(64));
		log.info("number -> " + number + "\n");

		log.info("-------- Zero out a number using a NULLARY COMPUTER --------\n");

		log.info("I.e.: math.zero(number)");

		ops.math().zero(number);
		log.info("number -> " + number + "\n");

		// -- Reusing special ops --

		log.info("================ REUSING SPECIAL OPS ================\n");

		// One of the most useful characteristics of special ops is the ability
		// to look them up in a type-safe way, obtaining a reference to an op
		// instance which can then be used however you need -- one time or many.

		log.info("-------- Get an instance of a special op --------\n");

		// Look up the op and keep a reference.

		final BinaryComputerOp<IterableInterval<DoubleType>, IterableInterval<DoubleType>, IterableInterval<DoubleType>> addOp =
			Computers.binary(ops, Ops.Math.Add.class, result, gradient, diamond);

		// In this case, we use the Computers.binary utility method to
		// require a binary computer op, so that we can take advantage of
		// the BinaryComputerOp API, which is type-safe -- whereas the Op
		// interface's run() method is not.
		//
		// As usual, compile-time type safety is a two-edged sword:
		// helpful to avoid coding errors, but sometimes rather verbose,
		// as can be seen above in the generic type of the addOp variable.
		// Scripting languages built on the JVM such as Groovy and Scala can
		// help to alleviate some of Java's verboseness in these situations.

		// We can now run the op with the matched arguments:
		addOp.run();
		log.info("math.add(result, gradient, diamond)");
		printImage(ij, "result", result);

		// Or with different primary arguments (secondary inputs do not vary):
		addOp.run(result, circle, sinusoid);
		log.info("math.add(result, circle, sinusoid)");
		printImage(ij, "result", result);

		// Note the difference between primary and secondary inputs: the latter
		// are not given when calling the special op's type-safe API; hence,
		// secondary inputs remain fixed as given during the original matching.
		//
		// Which inputs are considered "primary" is a decision left to the author
		// of each op. But here are two rules of thumb:
		//
		// 1) The primary inputs and outputs should correspond to the main relevant
		//    data structure -- e.g., ops which operate on images should declare
		//    the image argument(s) as primary.
		//
		// 2) There is usually an anticipated main use case involving variation of
		//    specific input(s) -- those should be chosen as the main inputs, so
		//    that the op can be reused across different values of those inputs.

		log.info("-------- Reuse an op to improve performance --------\n");

		// It is important to understand that each call into the Ops framework
		// requires the system to search for the best-matching op amongst all
		// those available. This is very useful for reasons of extensibility:
		// the best op for the job is always selected, and anyone can extend
		// the system with new ops optimized for particular scenarios.
		// However, matching can have substantial performance implications:

		final long slowStart = System.currentTimeMillis();
		for (int i = 0; i < ITERATIONS; i++) {
			// Search for the op every time and then run it
			ops.math().add(result, sinusoid, gradient);
		}
		final long slowEnd = System.currentTimeMillis();
		log.info("Slow = " + (slowEnd - slowStart) + " ms");

		final long fastStart = System.currentTimeMillis();
		for (int i = 0; i < ITERATIONS; i++) {
			// Execute the same op instance repeatedly.
			addOp.compute(result, sinusoid, gradient);
		}
		long fastEnd = System.currentTimeMillis();
		log.info("Fast = " + (fastEnd - fastStart) + " ms\n");

		// Reusing the same matched op every time is much faster.
		// But it is the responsibility of the caller to ensure that
		// subsequent arguments passed in are compatible with that same op.

		// -- Chaining ops --

		// Some ops accept other ops as arguments, forming chains or trees of ops.
		// For example, the map op executes the given op over all elements of an iteration:

//		ops.map(args)

		// -- Hybrid ops --

		// TODO

//		// -- 
//		log.info(
//			"--------- Loop op: Execute op on the input for a certain number of times --------");
//		int iterations = 4;
//		UnaryInplaceOp<DoubleType> addLoop = RTs.inplace(ops, Ops.Loop.class, in, add5,
//			iterations);
//		// Add 5 to 'in' each time through the loop; store value in 'in'.
//		addLoop.mutate(in);
//		log.info("--------- 'In' is modified from 10.0 to " + in + " ---------\n");
//
//		log.info(
//			"--------- Hybrid op: Can be used either as a function or computer op ---------");
//		UnaryHybridCF<IterableInterval<DoubleType>, DoubleType> meanOp = //
//			RTs.hybrid(ops, Ops.Stats.Mean.class, image);
//		UnaryHybridCF<IterableInterval<DoubleType>, DoubleType> maxOp = //
//			RTs.hybrid(ops, Ops.Stats.Max.class, image);
//		DoubleType mean = new DoubleType(0);
//		// Use the HybridOp as a ComputerOp: store result in output reference.
//		meanOp.compute1(image, mean);
//		// Use the HybridOp as a FunctionOp: return result as a new object.
//		DoubleType max = maxOp.compute1(image);
//		log.info("--------- Stats: Mean = " + mean + " Max = " + max +
//			" ---------\n");
//
//		log.info("-------- Map op: Execute op on every pixel of an image --------");
//		UnaryComputerOp<IterableInterval<DoubleType>, IterableInterval<DoubleType>> mapOp =
//			IIs.computer(ops, Ops.Map.class, image, add5);
//		// Add 5 to each pixel, and return the output as the given output reference.
//		mapOp.compute1(image, output);
//		log.info("-------- Input mean: " + meanOp.compute1(image) +
//			" Output mean: " + meanOp.compute1(output) + " --------\n");
//		ij.ui().show("Map output", output);

		log.info("--------- All done! ---------");
	}

	// -- Helper methods --

	private static IterableInterval<DoubleType> createImage(final ImageJ ij,
		final String name, final long w, final long h, final String formula)
	{
		// create a blank image
		ij.log().info(name + " = create.img([" + w + ", " + h + "])");
		final long[] dims = { w, h };
		final IterableInterval<DoubleType> image = ij.op().create().img(dims);

		// fill in the formula
		ij.log().info("image.equation(" + name + ", \"" + formula + "\")");
		ij.op().image().equation(image, formula);

		// display the result
		printImage(ij, name, image);

		return image;
	}

	/** A helper function to print out an image to the console. */
	private static void printImage(final ImageJ ij, final String name,
		final IterableInterval<DoubleType> image)
	{
		ij.log().info(name + " ->\n" + ij.op().image().ascii(image));
	}

}

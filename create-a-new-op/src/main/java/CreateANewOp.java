/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import net.imagej.ImageJ;
import net.imagej.ops.Op;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/** How to use create a new ImageJ OP. */
public class CreateANewOp {

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		// Run our op!
		final Object narf = ij.op().run("narf", "Put some trousers on");

		// And what value did our op return?
		ij.log().info("The op said: " + narf);
	}

	@Plugin(type = Op.class, name = "narf")
	public static class Narf implements Op {

		@Parameter(type = ItemIO.BOTH)
		private String string;

		@Override
		public void run() {
			string = "Egads! " + string.toUpperCase();
		}
	}
}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.module.ModuleInfo;
import imagej.module.ModuleItem;
import imagej.module.process.InitPreprocessor;
import imagej.module.process.ModulePreprocessor;
import imagej.module.process.ValidityPreprocessor;
import imagej.ops.Op;
import imagej.plugins.commands.app.AboutImageJ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/** How to use ImageJ Operations. */
public class CreatingANewOp {

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		// run the simple String op
		ij.log().info("Op said: " + ij.op().run("narf", "Test String"));
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

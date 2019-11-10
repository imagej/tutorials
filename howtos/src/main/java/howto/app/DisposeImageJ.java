/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 *
 * This work was supported by the German Research Foundation (DFG) under the code JU3110/1-1 (Fiji Software Sustainability).
 */

package howto.app;

import net.imagej.ImageJ;

/**
 * How to dispose {@link ImageJ}
 *
 * @author Deborah Schmidt
 */
public class DisposeImageJ {

	public static void run() {

		// start ImageJ
		ImageJ ij = new ImageJ();

		// do something with ImageJ

		// dispose ImageJ
		ij.context().dispose();
	}

}

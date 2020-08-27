/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
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

	public static void main(String...args) { run(); }
}

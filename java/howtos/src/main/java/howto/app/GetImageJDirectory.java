/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.app;

import net.imagej.ImageJ;

import java.io.File;

/**
 *  How to get the current SciJava application installation directory
 *
 * @author Deborah Schmidt
 */
public class GetImageJDirectory {

	private static void run() {
		ImageJ ij = new ImageJ();
		File base = ij.app().getApp().getBaseDirectory();
		System.out.println(base.getAbsolutePath());
	}

	public static void main(String...args) { run(); }
}

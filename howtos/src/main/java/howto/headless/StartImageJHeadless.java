/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.headless;

import net.imagej.ImageJ;

/**
 * How to start {@link ImageJ} in headless mode
 *
 * @author Deborah Schmidt
 */
public class StartImageJHeadless {

	/**
	 * .. using system properties before spinning a new ImageJ (this is "more secure" in that context initialization code will already know that everything is headless):
	 */
	private static void viaSystemProperty() {
		System.setProperty("java.awt.headless", "true");
		ImageJ ij = new ImageJ();
		System.out.println("headless: " + ij.ui().isHeadless());
	}

	/**
	 * .. by passing a command line argument:
	 */
	private static void viaCommandLineArgument() {
		ImageJ ij = new ImageJ();
		ij.launch("--headless");
		System.out.println("headless: " + ij.ui().isHeadless());
	}

	/**
	 * .. via UI service:
	 */
	private static void viaImageJUI() {
		ImageJ ij = new ImageJ();
		ij.ui().setHeadless(true);
		System.out.println("headless: " + ij.ui().isHeadless());
	}

	public static void main(String...args) {
		viaSystemProperty();
		viaCommandLineArgument();
		viaImageJUI();
	}

}

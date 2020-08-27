/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.adv;

import java.net.URL;

import net.imagej.ImageJ;

/** An introduction to the ImageJ API. */
public class IntroToImageJAPI {

	public static void main(final String... args) throws Exception {
		// The first step when working with ImageJ is to create an *ImageJ
		// application context*. This is an instance of the class net.imagej.ImageJ,
		// and is created as follows:
		final ImageJ ij = new ImageJ();
		// This context provides access to ImageJ operations and data structures.

		// ------------------------------------------------------------------------
		// COMPARISON WITH IMAGEJ 1.x:
		// ImageJ 1.x has a similar concept with the ij.ImageJ class, which is
		// created using "new ImageJ()" and cached statically as a singleton.
		// This allows the ImageJ instance to be recovered later by calling
		// IJ.getInstance(), and simplifies the API in some ways.
		// However, the assumption that there will only ever be one ImageJ per JVM
		// limits its flexibility, and the fact that ij.ImageJ extends
		// java.awt.Frame makes ImageJ 1.x difficult to use headless or with
		// user interfaces other than Java AWT.
		// ------------------------------------------------------------------------

		// ImageJ's functionality is divided into *services*.
		// Each service provides some API methods for performing related tasks.

		// ------------------------------------------------------------------------
		// COMPARISON WITH IMAGEJ 1.x:
		// ImageJ 1.x is not service-driven, which makes it less extensible,
		// since additional functionality cannot be registered with the context.
		// ------------------------------------------------------------------------

		// Here are some examples of the API in action:

		// The plugin service manages the available ImageJ plugins.
		final int pluginCount = ij.plugin().getIndex().size();
		System.out.println("There are " + pluginCount + " plugins available.");
		// See the intro-to-plugins tutorial for more information on plugins.

		// The log service is used for logging messages.
		ij.log().warn("Death Star approaching!");

		// The status service is used to report the current status of operations.
		ij.status().showStatus("It's nine o'clock and all is well.");

		// The menu service organizes a menu hierarchy for ImageJ commands.
		final int menuItemCount = ij.menu().getMenu().size();
		System.out.println("There are " + menuItemCount + " menu items total.");
		// See the intro-to-menus tutorial for more information on menus.

		// The platform service handles platform-specific functionality.
		// E.g., it can open a URL in the default web browser for your system:
		ij.platform().open(new URL("http://imagej.net/"));
		// See the intro-to-platforms tutorial for more information on platforms.

		// To learn more about the services API, see the intro-to-services tutorial.
		// Many services also have their own dedicated tutorials.
	}

}

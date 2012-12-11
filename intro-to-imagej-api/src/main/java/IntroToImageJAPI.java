/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.event.StatusService;
import imagej.log.LogService;
import imagej.menu.MenuService;
import imagej.platform.PlatformService;
import imagej.plugin.PluginService;

import java.net.URL;

/** An introduction to the ImageJ API. */
public class IntroToImageJAPI {

	public static void main(final String... args) throws Exception {
		// The first step when working with ImageJ is to create an *ImageJ
		// application context*. This is an instance of the class imagej.ImageJ,
		// and is created as follows:
		final ImageJ context = new ImageJ();
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
		final PluginService pluginService =
			context.getService(PluginService.class);
		final int pluginCount = pluginService.getIndex().size();
		System.out.println("There are " + pluginCount + " plugins available.");
		// See the intro-to-plugins tutorial for more information on plugins.

		// The log service is used for logging messages.
		final LogService log = context.getService(LogService.class);
		log.warn("Death Star approaching!");

		// The status service is used to report the current status of operations.
		final StatusService statusService = context.getService(StatusService.class);
		statusService.showStatus("It's nine o'clock and all is well.");

		// The menu service organizes a menu hierarchy for ImageJ commands.
		final MenuService menuService = context.getService(MenuService.class);
		final int menuItemCount = menuService.getMenu().size();
		System.out.println("There are " + menuItemCount + " menu items total.");
		// See the intro-to-menus tutorial for more information on menus.

		// The platform service handles platform-specific functionality.
		final PlatformService platformService =
			context.getService(PlatformService.class);
		// E.g., it can open a URL in the default web browser for your system:
		platformService.open(new URL("http://imagej.net/"));
		// See the intro-to-platforms tutorial for more information on platforms.

		// To learn more about the services API, see the intro-to-services tutorial.
		// Many services also have their own dedicated tutorials.
	}

}

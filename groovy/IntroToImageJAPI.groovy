// @PluginService plugin
// @LogService log
// @StatusService status
// @MenuService menu
// @PlatformService platform

// Here are some examples of the API in action:

// The plugin service manages the available ImageJ plugins.
pluginCount = plugin.getIndex().size();
println ("There are " + pluginCount + " plugins available.");

// See the intro-to-plugins tutorial for more information on plugins.

// The log service is used for logging messages.
log.warn("Death Star approaching!");

// The status service is used to report the current status of operations.
status.showStatus("It's nine o'clock and all is well.");

// The menu service organizes a menu hierarchy for ImageJ commands.
menuItemCount = menu.getMenu().size();
println("There are " + menuItemCount + " menu items total.");
		
// See the intro-to-menus tutorial for more information on menus.

// The platform service handles platform-specific functionality.
// E.g., it can open a URL in the default web browser for your system:
platform.open(new URL("http://imagej.net/"));
package howto.basics;

import net.imagej.ImageJ;
import org.scijava.app.StatusService;
import org.scijava.log.LogService;
import org.scijava.menu.MenuService;
import org.scijava.platform.PlatformService;
import org.scijava.plugin.PluginService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * How to work with the ImageJ2 API
 *
 * @author hadmin
 * @author stelfrich
 * @author ctrueden
 * @author imagejan
 * @author haesleinhuepf
 */
public class IntroToImageJAPI {

        /**
         * If you work in a project out of ImageJ, you can instantiate an ImageJ
         * and retrieve / use all its services like this
         */
        public static void firstSolution() throws IOException, InterruptedException {

            // Start an ImageJ instance
            ImageJ ij = new ImageJ();

            // show the main window
            ij.ui().showUI();

            // Retrieve major services
            PluginService pluginService = ij.plugin();
            LogService logService = ij.log();
            StatusService statusService = ij.status();
            MenuService menu = ij.menu();
            PlatformService platformService = ij.platform();

            // Here are some examples of the API in action:

            // The plugin service manages the available ImageJ plugins.
            int pluginCount = pluginService.getIndex().size();
            System.out.println ("There are " + pluginCount + " plugins available.");

            // See the intro-to-plugins tutorial for more information on plugins.

            // The log service is used for logging messages.
            logService.warn("Death Star approaching!");

            // The status service is used to report the current status of operations.
            int max = 200;
            for (int i = 0; i < max; i++) {
                statusService.showStatus(i, max, "Performing an expensive operation " + i + "/" + max);
                Thread.sleep(10);
            }
            statusService.clearStatus();

            // The menu service organizes a menu hierarchy for ImageJ commands.
            int menuItemCount = menu.getMenu().size();
            System.out.println("There are " + menuItemCount + " menu items total.");

            // See the intro-to-menus tutorial for more information on menus.

            // The platform service handles platform-specific functionality.
            // E.g., it can open a URL in the default web browser for your system:
            platformService.open(new URL("http://imagej.net/"));
        }

        public static void main(String...args) throws IOException, InterruptedException {

            firstSolution();
        }
}

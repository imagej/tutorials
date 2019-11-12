/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 *
 * This work was supported by the German Research Foundation (DFG) under the code JU3110/1-1 (Fiji Software Sustainability).
 */

package howto.services;

import net.imagej.ImageJ;
import org.scijava.plugin.PluginInfo;
import org.scijava.service.Service;

import java.util.List;

/**
 * How to list all available services
 *
 * @author Deborah Schmidt
 */
public class ListAllServices {

	private static void run() {
		ImageJ ij = new ImageJ();
		List<PluginInfo<Service>> services = ij.plugin().getPluginsOfType(Service.class);
		for(PluginInfo service : services) {
			System.out.println(service.getPluginClass());
		}
	}

	public static void main(String...args) {
		run();
	}
}

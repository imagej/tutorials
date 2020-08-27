/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.app;

import net.imagej.ImageJ;
import org.scijava.command.CommandModule;

import java.util.concurrent.ExecutionException;

/**
 * How to get a bunch of useful information about your system and installation
 *
 * @author Deborah Schmidt
 */
public class GetSystemInformation {

	/**
	 * .. using the SciJava command <code>SystemInformation</code>
	 */
	private static void run() throws ExecutionException, InterruptedException {
		ImageJ ij = new ImageJ();
		CommandModule module = ij.command().run("org.scijava.plugins.commands.debug.SystemInformation", true).get();
		String result = (String) module.getOutput("info");
		System.out.println(result);
	}

	public static void main(String...args) throws ExecutionException, InterruptedException { run(); }

}

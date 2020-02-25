/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.extensions;

import net.imagej.ImageJ;

import java.util.concurrent.ExecutionException;

/**
 * How to run a {@link org.scijava.command.Command}
 *
 * @author Deborah Schmidt
 */
public class RunExampleCommand {

	/**
	 * .. this will run the Command in a new Thread:
	 */
	private static void run() {
		ImageJ ij = new ImageJ();
		ij.ui().showUI();
		ij.command().run(ExampleCommand.class, true);
	}

	/**
	 * .. this is how you can attach parameters to the Command:
	 */
	private static void runWithParameters() {
		ImageJ ij = new ImageJ();
		ij.ui().showUI();
		ij.command().run(ExampleCommand.class, true, "name", "Ada");
	}

	/**
	 * .. how to wait for the Command to finish:
	 */
	private static void runAndWait() throws ExecutionException, InterruptedException {
		ImageJ ij = new ImageJ();
		ij.ui().showUI();
		ij.command().run(ExampleCommand.class, true, "name", "Ada").get();
	}

	/**
	 * .. how to avoid pre- and postprocessing (his will prevent the popup to open which asks the user for missing input parameters, this will also prevent output parameters to be displayed automatically):
	 */
	private static void runWithoutPrePostProcessing() {
		ImageJ ij = new ImageJ();
		ij.command().run(ExampleCommand.class, false, "name", "Ada");
	}

	public static void main(String...args) {
		run();
	}
}

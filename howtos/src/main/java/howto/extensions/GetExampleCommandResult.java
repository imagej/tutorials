/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 *
 * This work was supported by the German Research Foundation (DFG) under the code JU3110/1-1 (Fiji Software Sustainability).
 */

package howto.extensions;

import net.imagej.ImageJ;
import org.scijava.command.CommandModule;

import java.util.concurrent.ExecutionException;

/**
 * How to get the result of a {@link org.scijava.command.Command}
 *
 * @author Deborah Schmidt
 */
public class GetExampleCommandResult {

	private static void run() throws ExecutionException, InterruptedException {
		ImageJ ij = new ImageJ();
		CommandModule command = ij.command().run(ExampleCommand.class, true, "name", "Ada").get();
		Object output = command.getOutput("response");
		System.out.println(output);
	}

	public static void main(String...args) throws ExecutionException, InterruptedException { run(); }

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.command.CommandService;
import imagej.command.ContextCommand;
import imagej.data.Dataset;
import imagej.module.Module;
import imagej.plugins.commands.io.OpenFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * An illustration of how to execute commands using the ImageJ API. The source
 * code demonstrates three different ways of invoking a command
 * programmatically:
 * <ol>
 * <li>Using {@link CommandService} with a list of arguments</li>
 * <li>Using {@link CommandService} with arguments in a {@link Map}</li>
 * <li>Calling a command's Java API directly</li>
 * </ol>
 * <p>
 * The first two approaches can be used to invoke any command, but the compiler
 * cannot guarantee the correctness of the input types. That is, passing any
 * {@code Object} is possible for both parameter names and values, which
 * increases the chance of coding errors.
 * </p>
 * <p>
 * To address this issue, you can provide public API methods inside the command
 * itself. If written correctly, an ImageJ command can be instantiated,
 * populated and run as a "plain old java object" (POJO) rather than needing to
 * use the command service as above. This provides stronger type guarantees
 * (i.e., compile-time safety), rather than everything being resolved
 * dynamically at runtime. The easiest way to make a command usable in this
 * manner is to extend the {@link ContextCommand} base class and include getter
 * and setter methods for its parameters.
 * </p>
 * <p>
 * Here is a point-by-point comparison of the two approaches:
 * <table>
 * <tr>
 * <th>&nbsp;</th>
 * <th>CommandService</th>
 * <th>Java API</th>
 * </tr>
 * <tr>
 * <td>Publishes events</td>
 * <td>Yes</td>
 * <td>No</td>
 * </tr>
 * <tr>
 * <td>Performs pre- and post-processing</td>
 * <td>Yes</td>
 * <td>No</td>
 * </tr>
 * <tr>
 * <td>Executes in a separate thread</td>
 * <td>Yes</td>
 * <td>No</td>
 * </tr>
 * <tr>
 * <td>Compile-time safe</td>
 * <td>No</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>Works with any command</td>
 * <td>Yes</td>
 * <td>No</td>
 * </tr>
 * </table>
 * </p>
 * 
 * @author Curtis Rueden
 * @author Grant Harris
 */
public class ExecuteCommands {

	public static void main(final String... args) throws Exception {
		// create the ImageJ application context with all available services
		final ImageJ ij = new ImageJ();

		// execute using CommandService#run(String, Object...)
		final Dataset datasetWithArgs = invokeWithArgs(ij);
		logDatasetInfo(ij, "datasetWithArgs", datasetWithArgs);

		// execute using CommandService#run(String, Map)
		final Dataset datasetWithMap = invokeWithMap(ij);
		logDatasetInfo(ij, "datasetWithMap", datasetWithMap);

		// execute using the command's Java API
		final Dataset datasetFromJava = invokeFromJava(ij);
		logDatasetInfo(ij, "datasetFromJava", datasetFromJava);
	}

	/**
	 * Invokes the {@code OpenFile} command using the {@link CommandService} with
	 * a list of arguments. This approach is very flexible and compact, but the
	 * compiler cannot guarantee the correctness of the input types.
	 */
	public static Dataset invokeWithArgs(final ImageJ ij) {
		// execute asynchronously using the command service
		final Future<Module> future =
			ij.command().run("imagej.core.commands.io.OpenFile", "inputFile",
				new File("sample-image.fake"));
		// wait for the execution thread to complete
		final Module module = ij.module().waitFor(future);
		// return the desired output parameter value
		return (Dataset) module.getOutput("data");
	}

	/**
	 * Invokes the {@code OpenFile} command using the {@link CommandService} with
	 * arguments in a {@link Map}. This approach is extremely flexible, but the
	 * compiler cannot guarantee the correctness of the input types.
	 */
	public static Dataset invokeWithMap(final ImageJ ij) {
		// populate the map of input parameters
		final Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("inputFile", new File("sample-image.fake"));
		// execute asynchronously using the command service
		final Future<Module> future =
			ij.command().run("imagej.core.commands.io.OpenFile", inputMap);
		// wait for the execution thread to complete
		final Module module = ij.module().waitFor(future);
		// return the desired output parameter value
		return (Dataset) module.getOutput("data");
	}

	/**
	 * Directly invokes the {@code OpenFile} command using its Java API.
	 * <p>
	 * This approach is compile-time safe, but only commands that expose their
	 * Java API can be invoked in this way. Note that no pre- or postprocessing of
	 * the command is done, and no events are published with respect to the
	 * execution, making this approach convenient for nesting command executions.
	 * </p>
	 * <p>
	 * Note that with this method, casting is not necessary, nor is it possible to
	 * accidentally pass in parameters of the wrong type. The only catch is that
	 * anyone wishing to call the code must inject the ImageJ application context
	 * first by calling the {@code setContext} method.
	 * </p>
	 */
	public static Dataset invokeFromJava(final ImageJ ij) {
		// construct a new instance of the command
		final OpenFile openFile = new OpenFile();
		openFile.setContext(ij.getContext());
		// assign input parameter values
		openFile.setInputFile(new File("sample-image.fake"));
		// execute the command in the same thread, blocking until complete
		// (or you could run it in a separate thread using the ThreadService)
		openFile.run();
		// return the desired output parameter value
		return (Dataset) openFile.getData();
	}

	private static void logDatasetInfo(final ImageJ ij, final String prefix,
		final Dataset dataset)
	{
		final String t = dataset.getType().getClass().getSimpleName();
		final long x = dataset.dimension(0);
		final long y = dataset.dimension(1);
		ij.log().info(prefix + ": (" + "type = " + t + ", x = " + x + ", y = " + y);
	}

}

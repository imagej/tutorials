/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.imagej.ImageJ;
import net.imagej.app.AboutImageJ;

import org.scijava.module.ModuleInfo;
import org.scijava.module.ModuleItem;
import org.scijava.module.process.InitPreprocessor;
import org.scijava.module.process.ModulePreprocessor;
import org.scijava.module.process.ValidityPreprocessor;

/** How to work with ImageJ modules. */
public class WorkingWithModules {

	public static void main(final String... args) {
		final ImageJ ij = new ImageJ();

		// First of all, what is the difference between a "module" and a "command"?
		// - A "module" is Java code which implements the interfaces in the
		//   org.scijava.module package (Module, ModuleInfo, ModuleItem).
		// - A "command" is a particular type of ImageJ plugin (implementing
		//   org.scijava.command.Command) which is executable (i.e., also
		//   implements the Runnable interface).
		//
		// Not all modules are commands. The plugin service will automatically
		// discover all available commands, whereas non-command modules are not
		// necessarily discoverable automatically (though some might be; and it is
		// possible to register modules with the module service manually as well).
		//
		// Any command can be expressed as a module (via the
		// org.scijava.command.CommandModule adapter class). However, not all
		// modules are commands. There are two services for working with modules
		// and commands respectively. As a rule of thumb, the module service is
		// more "low-level" while the command service is more "high-level".
		//
		// If you want to expose ImageJ functionality from your software, your best
		// bet is to work with modules rather than commands, since as stated above,
		// not all modules are commands.

		// You can ask ImageJ's module service for a list of available modules.

		final List<ModuleInfo> modules = ij.module().getModules();
		for (final ModuleInfo info : modules) {
			if (!info.canRunHeadless()) {
				// You may wish to skip modules which do not purport to run headless.
				continue;
			}

			// We dump some information about the module in question:
			final String title = info.getTitle();
			final String className = info.getDelegateClassName();
			ij.log().info("Module '" + title + "' [" + className + "]:");

			// A module consists of typed inputs and outputs.
			// Now we dump some details of each such input to the ImageJ log.
			for (final ModuleItem<?> input : info.inputs()) {
				final String inputName = input.getName();
				final Class<?> inputType = input.getType();
				ij.log().info("\t" + inputName + ": " + inputType.getName());
			}
		}

		// We choose a module of interest, for some additional fun!
		final ModuleInfo myInfo = ij.command().getCommand(AboutImageJ.class);

		// To execute a module, use the ModuleService#run methods.
		// To execute a command by name, use the CommandService#run methods.
		//
		// In either case, take particular note of the "boolean process" flag.
		// When set, the module will be preprocessed with all available
		// preprocessing plugins before being executed, and then after execution
		// will be postprocessed with all available postprocessing plugins.
		//
		// In particular, the preprocessors will "fill in" many parameter values
		// for you, such as single dataset parameters.
		//
		// See the "custom-preprocessor-plugin" tutorial for details on how to
		// customize such preprocessing.

		// To run a module with pre- and postprocessing:
		ij.log().info("Running " + myInfo.getTitle());
		ij.module().run(myInfo, true);

		// It may sometimes be the case that even with preprocessing, your module
		// still requires additional input values. In such situations, you can pass
		// the map of such input values explicitly as follows:
		final Map<String, Object> myInputs = new HashMap<String, Object>();
		myInputs.put("requiredStringInput", "requiredStringValue");
		myInputs.put("requiredDoubleInput", 5.6);
		// etc.
		ij.module().run(myInfo, true, myInputs);

		// Or if you prefer, there is a succinct varargs method signature:
		ij.module().run(myInfo, true,
			"requiredStringInput", "requiredStringValue",
			"requiredDoubleInput", 5.6);

		// If you desire more control over pre- and postprocessing, you can instead
		// invoke the module with the process flag set to false.
		// In this case, *no* pre- or postprocessing happens automatically.

		// The invocation is as follows:
		ij.module().run(myInfo, false);

		// Alternately, if you want to exercise complete manual control over the
		// pre- and postprocessing, you can also pass an explicit list of module
		// pre- and postprocessors.

		final List<ModulePreprocessor> pre = new ArrayList<ModulePreprocessor>();

		// The validity preprocessor ensures the module does not break the rules.
		final ValidityPreprocessor validPre = new ValidityPreprocessor();
		validPre.setContext(ij.getContext());
		pre.add(validPre);

		// The init preprocessor calls the module's initializer callbacks.
		final InitPreprocessor initPre = new InitPreprocessor();
		initPre.setContext(ij.getContext());
		pre.add(initPre);

		// Finally, we invoke the module service with our preprocessors:
		ij.module().run(myInfo, pre, null, myInputs);
	}

}

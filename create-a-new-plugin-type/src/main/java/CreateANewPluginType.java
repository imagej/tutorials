/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.core.commands.calculator.ImageCalculator;
import imagej.core.commands.display.interactive.Threshold;
import imagej.data.operator.CalculatorOp;
import imagej.data.operator.CalculatorService;
import imagej.data.threshold.AutoThresholdMethod;
import imagej.data.threshold.ThresholdService;

import java.util.Set;

/**
 * Demonstrates how to create your own new type of plugin.
 * <p>
 * We define our new plugin type in the {@link Animal} interface, with the
 * {@link Lion}, {@link Tiger} and {@link Bear} classes providing three example
 * animals.
 * </p>
 * <p>
 * Animal-related features are provided in an API: the {@link AnimalService}.
 * While not required, it is recommended to provide such a companion service to
 * your new plugin type, to make things easier for downstream code interested in
 * plugins of your new type.
 * </p>
 * <p>
 * There are many other cases of new plugin types throughout ImageJ; e.g.:
 * </p>
 * <ul>
 * <li>The {@link imagej.data.operator} package provides a {@link CalculatorOp}
 * defining a binary operation for the {@link ImageCalculator Image Calculator}
 * command, and corresponding {@link CalculatorService} with the API providing
 * the actual calculation routines.</li>
 * <li>The {@link imagej.data.threshold} package provides a
 * {@link AutoThresholdMethod} defining a method for automatically thresholding
 * an image, used by the interactive {@link Threshold} command, and
 * corresponding {@link ThresholdService} with the API providing the actual
 * auto-thresholding routines.</li>
 * </ul>
 * <p>
 * The biggest advantage of structuring things in this way is
 * <em>extensibility</em>: in ImageJ1, both Image Calculator and Auto-Threshold
 * commands are hardcoded into those plugins, whereas in ImageJ2, the operations
 * available to those commands is obtained dynamically from the appropriate
 * service, making it possible for anyone to add a new operation to the list
 * merely by implementing a new plugin of that type, rather than changing the
 * core code of ImageJ itself.
 * </p>
 */
public class CreateANewPluginType {

	public static void main(String... args) {
		// Create a new ImageJ application context, from which
		// we will access all the functionality we need.
		final ImageJ ij = new ImageJ();

		// Grab our animal service from the ImageJ context.
		final AnimalService animals = ij.get(AnimalService.class);

		// Ask the animal service for the names of all available animals.
		final Set<String> names = animals.getAnimalNames();
		ij.log().info("Total number of animals: " + names.size());

		// Print out a little more information about each animal.
		for (final String name : animals.getAnimalNames()) {
			// Create a new instance of this animal.
			final Animal animal = animals.createAnimal(name);

			// Report on the most vital detail: is this animal a type of cat?
			final String catStatus = animal.isCat() ? " is " : " is not ";
			ij.log().info("- " + name + catStatus + "a cat");
		}
	}

}

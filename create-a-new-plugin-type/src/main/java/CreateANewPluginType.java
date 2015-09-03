/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.Set;

import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.ImageJ;
import net.imagej.display.ImageDisplay;
import net.imagej.display.ImageDisplayService;
import net.imagej.display.OverlayService;
import net.imagej.overlay.Overlay;

import org.scijava.Context;
import org.scijava.Contextual;
import org.scijava.Prioritized;
import org.scijava.plugin.AbstractRichPlugin;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;

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
 * <li>The {@link net.imagej} package provides a
 * {@link DatasetService} for working with {@link Dataset}s.</li>
 * <li>The {@link net.imagej.overlay} package provides a
 * {@link OverlayService} for working with {@link Overlay}s.</li>
 * <li>The {@link net.imagej.display} package provides an
 * {@link ImageDisplayService} for working with {@link ImageDisplay}s.</li>
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
 * <p>
 * Two final side notes:
 * </p>
 * <ol>
 * <li>Unlike the core ImageJ services, the {@link AnimalService} is not split
 * between interface ({@code AnimalService}) and implementation (
 * {@code DefaultAnimalService}). The core ImageJ services all provide such a
 * split so that it is possible for downstream code to easily override the
 * behavior of services, by providing an alternative service implementation with
 * a higher priority. While we believe that such an interface/implementation
 * split is better for extensibility, we did not want to complicate this example
 * by splitting the {@code AnimalService} in such a way.</li>
 * <li>Most ImageJ plugin types extend the core {@link Contextual} and
 * {@link Prioritized} interfaces (in practice, this is easily accomplished by
 * providing e.g. an {@code AbstractAnimal} class that extends
 * {@link AbstractRichPlugin}). The advantage of extending the
 * {@link Prioritized} interface is that the {@link PluginService} will return
 * your available plugins in prioritized order; i.e., sorted by the
 * {@link Plugin#priority()} attribute. For some kinds of plugins,
 * prioritization can be very helpful. The advantage of extending the
 * {@link Contextual} interface is that plugin instances will then have a handle
 * on their {@link Context}, and hence have access to services. In particular,
 * it can be nice e.g. for an {@link Animal} to be able to make calls to the
 * {@link AnimalService} in various situations.
 * </ol>
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

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.HashMap;
import java.util.Set;

import org.scijava.plugin.AbstractPTService;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginInfo;
import org.scijava.service.Service;

/** Service which manages available {@link Animal}s. */
@Plugin(type = Service.class)
public class AnimalService extends AbstractPTService<Animal> {

	// When creating a new type of plugin (such as Animal), it is very nice to
	// have a corresponding service that provides operations relevant to your
	// new plugin type.

	// This service is a PTService (extending AbstractPTService<Animal>), so that
	// discovery of all available plugins is automatically handled for you.

	// You can easily get the list of available plugins by calling getPlugins(),
	// or use the plugin service (available via getPluginService()) to
	// instantiate any given plugin.

	/** Map of each animal name to its corresponding plugin metadata. */
	private HashMap<String, PluginInfo<Animal>> animals =
		new HashMap<String, PluginInfo<Animal>>();

	/**
	 * Gets the list of available animals. The names on this list can be passed to
	 * {@link #createAnimal(String)} to create instances of that animal.
	 */
	public Set<String> getAnimalNames() {
		return animals.keySet();
	}

	/** Creates an animal of the given name. */
	public Animal createAnimal(final String name) {
		// First, we get the animal plugin with the given name.
		final PluginInfo<Animal> info = animals.get(name);

		if (info == null) {
			throw new IllegalArgumentException("No animal of that name");
		}

		// Next, we use the plugin service to create an animal of that kind.
		final Animal animal = getPluginService().createInstance(info);

		return animal;
	}

	@Override
	public void initialize() {
		// This Service method is called when the animal service is first created.
		// It loops over the list of available animals, adding them to its own
		// animal map, which is indexed by animal name.

		// We loop over all available animal plugins.
		for (final PluginInfo<Animal> info : getPlugins()) {
			String name = info.getName();
			if (name == null || name.isEmpty()) {
				// The animal's @Plugin annotation does not specify a name,
				// so we use the fully qualified class name as a fallback.
				name = info.getClassName();
			}
			// Add the plugin to the list of known animals.
			animals.put(name, info);
		}
	}

	@Override
	public Class<Animal> getPluginType() {
		return Animal.class;
	}

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.HashMap;
import java.util.Set;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginInfo;
import org.scijava.plugin.PluginService;
import org.scijava.service.AbstractService;
import org.scijava.service.Service;

/** Service which manages available {@link Animal}s. */
@Plugin(type = Service.class)
public class AnimalService extends AbstractService {

	// When creating a new type of plugin (such as Animal), it is very nice to
	// have a corresponding service that provides operations relevant to your
	// new plugin type.

	// This service will have a dependency on the plugin service, because it
	// conveniently handles discovery of all available plugins for you.
	// You can easily ask the plugin service for all plugins of any given type,
	// such as your new type. You can also use the plugin service to instantiate
	// any given plugin.

	@Parameter
	public PluginService pluginService;

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
			// No animal by that name, so we return null. If you dislike
			// nulls, you could instead throw IllegalArgumentException.
			return null;
		}

		// Next, we use the plugin service to create an animal of that kind.
		final Animal animal = pluginService.createInstance(info);

		return animal;
	}

	@Override
	public void initialize() {
		// This Service method is called when the animal service is first created.
		// It asks the plugin service for the list of available animals, and
		// adds them to its own animal map, which is indexed by animal name.

		// We loop over each animal plugin known to the plugin service.
		for (final PluginInfo<Animal> info :
			pluginService.getPluginsOfType(Animal.class))
		{
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

}

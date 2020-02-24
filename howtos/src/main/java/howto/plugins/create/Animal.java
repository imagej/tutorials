/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */
package howto.plugins.create;

import net.imagej.ImageJPlugin;

import org.scijava.plugin.Plugin;

/**
 * A new type of ImageJ plugin that allows discovery of animal species.
 * <p>
 * Animals discoverable at runtime must implement this
 * interface and be annotated with @{@link Plugin} with attribute
 * {@link Plugin#type()} = {@link Animal}.class.
 * </p>
 * 
 * @author Curtis Rueden
 * @see AnimalService
 */
public interface Animal extends ImageJPlugin {

	/** Gets whether the animal is a feline subspecies. */
	boolean isCat();

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.plugins.create;

import org.scijava.plugin.Plugin;

/** A fierce animal that likes to pounce with powerful linear motion. */
@Plugin(type = Animal.class, name = "Tiger")
public class Tiger implements Animal {

	@Override
	public boolean isCat() {
		return true;
	}

}

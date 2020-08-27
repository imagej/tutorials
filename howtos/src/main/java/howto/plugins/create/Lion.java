/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.plugins.create;

import org.scijava.plugin.Plugin;

/** An animal that rules the jungle. */
@Plugin(type = Animal.class, name = "Lion")
public class Lion implements Animal {

	@Override
	public boolean isCat() {
		return true;
	}

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

import org.scijava.plugin.Plugin;

/** A fierce animal that likes to pounce with powerful linear motion. */
@Plugin(type = Animal.class, name = "Tiger")
public class Tiger implements Animal {

	@Override
	public boolean isCat() {
		return true;
	}

}

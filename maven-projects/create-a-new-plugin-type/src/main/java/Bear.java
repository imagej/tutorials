/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

import org.scijava.plugin.Plugin;

/** An animal with tremendous strength and adorable cubs. */
@Plugin(type = Animal.class, name = "Bear")
public class Bear implements Animal {

	@Override
	public boolean isCat() {
		return false;
	}

}

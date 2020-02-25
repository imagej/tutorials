/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.extensions;

import org.scijava.command.Command;
import org.scijava.command.DynamicCommand;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class)
public class ExampleDynamicCommand extends DynamicCommand {

	@Override
	public void run() {
	}
}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.extensions;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class, label = "Example Command")
public class ExampleCommand implements Command {

	@Parameter
	String name;

	@Parameter(type = ItemIO.OUTPUT)
	String response;

	@Override
	public void run() {
		response = "I am " + name;
	}
}

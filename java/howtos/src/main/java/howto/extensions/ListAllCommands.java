/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.extensions;

import net.imagej.ImageJ;
import org.scijava.command.CommandInfo;

import java.util.List;

/**
 * How to list all available {@link org.scijava.command.Command}s
 *
 * @author Deborah Schmidt
 */
public class ListAllCommands {

	private static void run() {
		ImageJ ij = new ImageJ();
		List<CommandInfo> commands = ij.command().getCommands();
		for(CommandInfo info : commands) {
			System.out.println(info.getClassName());
		}
	}

	public static void main(String...args) {
		run();
	}

}

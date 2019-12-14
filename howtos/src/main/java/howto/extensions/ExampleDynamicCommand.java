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

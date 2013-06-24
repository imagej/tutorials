import imagej.command.Command;
import imagej.ui.UIService;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type=Command.class, menuPath = "Help>Hello, World!")
public class HelloWorldPlugin implements Command {
    @Parameter
    private UIService uiService;

    @Override
    public void run() {
            uiService.showDialog("Hello, World!");
    }
}

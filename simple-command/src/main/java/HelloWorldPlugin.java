import imagej.ImageJ;
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

    public static void main(final String... args) {
    	final ImageJ ij = new ImageJ();
    	ij.command().run(HelloWorldPlugin.class);
    }
}

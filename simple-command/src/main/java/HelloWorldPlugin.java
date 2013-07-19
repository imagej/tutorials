/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.command.Command;
import imagej.ui.UIService;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * A very simple plugin.
 * <p>
 * The annotation {@code @Plugin} lets ImageJ know that this is a plugin. There
 * are a vast number of possible plugins; {@code Command} plugins are the most
 * common one: they take inputs and produce outputs.
 * </p>
 * <p>
 * A {@link Command} is most useful when it is bound to a menu item; that is
 * what the {@code menuPath} parameter of the {@code @Plugin} annotation does.
 * </p>
 * <p>
 * Each input to the command needs to be specified as a field with the
 * {@code @Parameter} annotation. This plugin does not have any outputs; outputs
 * would also be specified as fields, but with a
 * {@code @Parameter(type = ItemIO.OUTPUT)} annotation.
 * </p>
 * 
 * @author Johannes Schindelin
 */
@Plugin(type = Command.class, menuPath = "Help>Hello, World!")
public class HelloWorldPlugin implements Command {

	@Parameter
	private UIService uiService;

	/**
	 * Shows the well-known "Hello, World!" message in a dialog. The {@code run()}
	 * method of every {@link Command} is the entry point for ImageJ: this is what
	 * will be called when the user clicks the menu entry, after the inputs are
	 * populated.
	 */
	@Override
	public void run() {
		uiService.showDialog("Hello, World!");
	}

	/**
	 * A {@code main()} method for testing.
	 * <p>
	 * When developing a plugin in an Integrated Development Environment (such as
	 * Eclipse or NetBeans), it is most convenient to provide a simple
	 * {@code main()} method that creates an ImageJ context and calls the plugin.
	 * </p>
	 * <p>
	 * In particular, this comes in handy when one needs to debug the plugin:
	 * after setting one or more breakpoints and populating the inputs (e.g. by
	 * calling something like
	 * {@code ij.command().run(MyPlugin.class, "inputImage", myImage)} where
	 * {@code inputImage} is the name of the field specifying the input) debugging
	 * becomes a breeze.
	 * </p>
	 * 
	 * @param args unused
	 */
	public static void main(final String... args) {
		final ImageJ ij = new ImageJ();
		ij.command().run(HelloWorldPlugin.class);
	}

}

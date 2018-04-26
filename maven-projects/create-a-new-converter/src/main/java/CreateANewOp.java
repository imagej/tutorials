

import net.imagej.Dataset;
import net.imagej.ImageJ;

import org.scijava.command.Command;
import org.scijava.convert.Converter;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginInfo;

/** How to use create a new ImageJ OP. */
@Plugin(type=Command.class)
public class CreateANewOp implements Command{

	@Parameter
	Dataset in;
	
	@Override
	public void run() {
		System.out.println(in);
	}

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		PluginInfo narfInfo = new PluginInfo(Narf.class, Converter.class);
		narfInfo.setPluginClass(Narf.class);
		ij.plugin().addPlugin(narfInfo);
		
		PluginInfo narfInfo2 = new PluginInfo(Narf2.class, Converter.class);
		narfInfo2.setPluginClass(Narf2.class);
		ij.plugin().addPlugin(narfInfo2);
		
		// Run our op!
		final Object narf = ij.command().run("CreateANewOp", true, "in", "Put some trousers on");

		// And what value did our op return?
		ij.log().info("The op said: " + narf);
	}
}

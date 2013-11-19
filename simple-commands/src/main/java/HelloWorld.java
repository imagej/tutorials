/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.command.Command;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.eclipse.core.runtime.internal.adaptor.EclipseAppLauncher;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.baseadaptor.BaseAdaptor;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.eclipse.osgi.service.runnable.ApplicationLauncher;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.scijava.ItemIO;
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
 * Each input to the command is specified as a field with the {@code @Parameter}
 * annotation. Each output is specified the same way, but with a
 * {@code @Parameter(type = ItemIO.OUTPUT)} annotation.
 * </p>
 * 
 * @author Johannes Schindelin
 * @author Curtis Rueden
 */
@Plugin(type = Command.class, headless = true, menuPath = "Help>Hello, World!")
public class HelloWorld implements Command {

	@Parameter(label = "What is your name?")
	private String name = "J. Doe";

	@Parameter(type = ItemIO.OUTPUT)
	private String greeting;

	/**
	 * Produces an output with the well-known "Hello, World!" message. The
	 * {@code run()} method of every {@link Command} is the entry point for
	 * ImageJ: this is what will be called when the user clicks the menu entry,
	 * after the inputs are populated.
	 */
	@Override
	public void run() {
		greeting = "Hello, " + name + "!";
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
	 * @throws Exception
	 */
	public static void main(final String... args) throws Exception {
		String knimePath = System.getProperty("knime.home");
		if (knimePath == null) {
			knimePath = System.getProperty("user.home") + "/Desktop/eclipse_knime_2.8.2";
			if (!new File(knimePath).exists()) {
				knimePath = "C:/knime_2.8.2";
				if (!new File(knimePath).exists()) {
					throw new RuntimeException("Cannot find KNIME!");
				}
			}
		}

		String applicationId = args.length > 0 ? args[0] : null; // "org.knime.product.KNIME_BATCH_APPLICATION";
		String[] commandLineArguments = { "-help" };

		FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
		Map<String, String> config = new HashMap<String, String>();
		config.put(IApplicationContext.APPLICATION_ARGS, "-consoleLog");
		config.put("osgi.console", "");
		config.put("osgi.configuration.area", knimePath + "/configuration");
		if (applicationId != null) {
			config.put("eclipse.application.launchDefault", "true");
			config.put("eclipse.application", applicationId);
			config.put("eclipse.application.default", "true");
		}

		Framework framework = frameworkFactory.newFramework(config);
		framework.start();
		final BundleContext context = framework.getBundleContext();

		// TODO: refactor; verify that it is actually needed
		// install all bundles
		new File(knimePath + "/plugins").listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				String name = file.getName();
				if (file.isDirectory()) {
					if ("configuration".equals(name)) {
						return false;
					}
				} else if (!name.endsWith(".jar")) {
					return false;
				}
				if (name.startsWith("org.eclipse.osgi_")) {
					return false;
				}
				String url = file.toURI().toString();
				try {
					context.installBundle(url);
				}
				catch (BundleException e) {
					e.printStackTrace();
				}
				return true;
			}

		});

		// Start the application
		if (applicationId != null) {
			FrameworkLog log = new BaseAdaptor(new String[0]).getFrameworkLog();
			EclipseAppLauncher appLauncher = new EclipseAppLauncher(context, false, true, log);
			context.registerService(ApplicationLauncher.class.getName(), appLauncher, null);
			appLauncher.start(commandLineArguments);
			System.err.println("headless: " + System.getProperty("java.awt.headless"));
		}

		String simplePluginURL = new File("../simple-osgi-plugin/target/simple-osgi-plugin-1.0.0-SNAPSHOT.jar").toURI().toString();
		Bundle bundle = context.installBundle(simplePluginURL);
		bundle.start();
		ServiceReference<Runnable> reference = (ServiceReference<Runnable>) context.getServiceReference("baselib.BaseService");
		Runnable runnable = context.getService(reference);
		runnable.run();

		framework.stop();
    framework.waitForStop(0);
	}
}

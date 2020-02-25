/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imagej.event.DatasetCreatedEvent;

import org.scijava.display.event.DisplayEvent;
import org.scijava.display.event.input.MsEnteredEvent;
import org.scijava.display.event.input.MsEvent;
import org.scijava.display.event.window.WinEvent;
import org.scijava.event.EventHandler;
import org.scijava.event.SciJavaEvent;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.service.Service;

/**
 * Listens to events of interest.
 * <p>
 * ImageJ features a granular class hierarchy of event types. See
 * {@link SciJavaEvent} for the top level class. You can also watch events as
 * they occur by running ImageJ's "Watch Events" command.
 * </p>
 * <p>
 * If you register an event handling method on a particular type of event, that
 * method will receive a callback for any event published of that type,
 * including subtypes. For example, if you specify an event handler for
 * {@link MsEvent}s, it will be called when e.g. a {@link MsEnteredEvent}
 * occurs.
 * </p>
 * <p>
 * This example demonstrates how to subscribe to events from arbitrary code.
 * Note that if you want to subscribe to events from a {@link Service}, you need
 * only add a method with the @{@link EventHandler} annotation; it will be
 * automatically subscribed.
 * </p>
 */
public class ListenToEvents {

	private static MyEventSubscriber myEventSubscriber;

	public static void main(final String... args) throws Exception {
		// Create the ImageJ application context with all available services.
		final ImageJ ij = new ImageJ();

		// Create an event subscriber and inject the application context;
		// this step will automatically subscribe to event notifications
		// for all methods labeled with the "@EventHandler" annotation.
		myEventSubscriber = new MyEventSubscriber();
		ij.getContext().inject(myEventSubscriber);

		// NB: If the myEventSubscriber object falls out of scope, it may be
		// garbage collected, and then your event handling methods will no longer
		// get called. So it is important to keep a reference to any object which
		// is subscribing to ImageJ events!

		// Create a new dataset.
		final int w = 512, h = 384;
		final Dataset dataset =
			ij.dataset().create(new long[] { w, h }, "Events Demo",
				new AxisType[] { Axes.X, Axes.Y }, 8, false, false);

		// Display the dataset.
		ij.ui().show(dataset);
	}

	/** A class for subscribing to ImageJ events of interest. */
	public static class MyEventSubscriber {

		@Parameter
		private LogService log;

		/** Responds to dataset creation events. */
		@EventHandler
		public void onEvent(final DatasetCreatedEvent evt) {
			logEvent(evt);
		}

		/** Responds to display events. */
		@EventHandler
		public void onEvent(final DisplayEvent evt) {
			logEvent(evt);
		}

		/** Responds to mouse events. */
		@EventHandler
		public void onEvent(final MsEvent evt) {
			logEvent(evt);
		}

		/** Responds to window events. */
		@EventHandler
		public void onEvent(final WinEvent evt) {
			logEvent(evt);
		}

		private void logEvent(final SciJavaEvent evt) {
			log.info("[" + evt.getClass().getSimpleName() + "] " + evt);
		}

	}

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.data.Dataset;
import imagej.display.event.DisplayEvent;
import imagej.display.event.input.MsEnteredEvent;
import imagej.display.event.input.MsEvent;
import imagej.display.event.window.WinEvent;
import net.imglib2.meta.Axes;
import net.imglib2.meta.AxisType;

import org.scijava.AbstractContextual;
import org.scijava.event.EventHandler;
import org.scijava.event.SciJavaEvent;
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

		// Create a new dataset.
		final int w = 512, h = 384;
		final Dataset dataset =
			ij.dataset().create(new long[] { w, h }, "Events Demo",
				new AxisType[] { Axes.X, Axes.Y }, 8, false, false);

		// Display the dataset.
		ij.ui().show(dataset);

		// Create an event subscriber and set its ImageJ application context;
		// this step will automatically subscribe to event notifications for
		// all methods labeled with the "@EventHandler" annotation.
		myEventSubscriber = new MyEventSubscriber();
		myEventSubscriber.setContext(ij.getContext());

		// NB: If the myEventSubscriber object falls out of scope, it may be
		// garbage collected, and then your event handling methods will no longer
		// get called. So it is important to keep a reference to any object which
		// is subscribing to ImageJ events!
	}

	/** A class for subscribing to ImageJ events of interest. */
	public static class MyEventSubscriber extends AbstractContextual {

		/** Handles display events. */
		@EventHandler
		public void onEvent(final DisplayEvent evt) {
			System.out.println("[DisplayEvent] " + evt);
		}

		/** Handles mouse events. */
		@EventHandler
		public void onEvent(final MsEvent evt) {
			System.out.println("[MsEvent] " + evt);
		}

		/** Handles window events. */
		@EventHandler
		public void onEvent(final WinEvent evt) {
			System.out.println("[WinEvent] " + evt);
		}

	}

}

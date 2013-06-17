/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.data.Dataset;
import imagej.data.display.ImageDisplay;
import imagej.data.overlay.EllipseOverlay;
import imagej.data.overlay.LineOverlay;
import imagej.data.overlay.Overlay;
import imagej.data.overlay.RectangleOverlay;
import imagej.util.Colors;

import java.util.ArrayList;
import java.util.List;

import net.imglib2.meta.Axes;
import net.imglib2.meta.AxisType;

/**
 * Adds ROIs to a display.
 * <p>
 * <b>Please note that this API is <em>not</em> stable and will almost certainly
 * change prior to the final <code>2.0.0</code> release!</b>
 * </p>
 */
public class AddROIs {

	public static void main(final String... args) throws Exception {
		// create the ImageJ application context with all available services
		final ImageJ ij = new ImageJ();

		// create a new dataset
		final int w = 512, h = 384;
		final Dataset dataset =
			ij.dataset().create(new long[] { w, h }, "ROI Demo",
				new AxisType[] { Axes.X, Axes.Y }, 8, false, false);

		// create a display for the dataset
		final ImageDisplay imageDisplay =
			(ImageDisplay) ij.display().createDisplay(dataset);

		// create a line
		final LineOverlay line = new LineOverlay(ij.getContext(),
			new double[] { 0, 0 }, new double[] { w, h });
		line.setLineColor(Colors.PLUM);
		line.setLineWidth(1.5);

		// create a rectangle
		final RectangleOverlay rectangle = new RectangleOverlay(ij.getContext());
		rectangle.setOrigin(3 * w / 5, 0);
		rectangle.setOrigin(h / 5, 1);
		rectangle.setExtent(w / 4, 0);
		rectangle.setExtent(h / 4, 1);
		rectangle.setLineColor(Colors.HONEYDEW);
		rectangle.setLineWidth(2);
		rectangle.setFillColor(Colors.TOMATO);
		rectangle.setAlpha(190);

		// create an ellipse
		final EllipseOverlay ellipse = new EllipseOverlay(ij.getContext());
		ellipse.setOrigin(w / 4, 0);
		ellipse.setOrigin(3 * h / 4, 1);
		ellipse.setRadius(w / 8, 0);
		ellipse.setRadius(h / 8, 1);
		ellipse.setLineColor(Colors.CHOCOLATE);
		ellipse.setLineWidth(8);
		ellipse.setFillColor(Colors.PEACHPUFF);
		ellipse.setAlpha(120);

		// add the overlays to the display
		final List<Overlay> overlays = new ArrayList<Overlay>();
		overlays.add(line);
		overlays.add(rectangle);
		overlays.add(ellipse);
		ij.overlay().addOverlays(imageDisplay, overlays);

		// show the display
		ij.ui().show(imageDisplay);
	}

}

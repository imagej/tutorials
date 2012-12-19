/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import ij.IJ;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import imagej.ImageJ;
import imagej.data.table.DefaultGenericTable;
import imagej.data.table.GenericTable;
import imagej.display.DisplayService;
import imagej.ui.UIService;

/** Demonstrates use of the modern ImageJ API from a legacy ImageJ1 plugin. */
public class DisplayATable implements PlugIn {

	@Override
	public void run(final String arg) {
		// set the context class loader so that the ImageJ context finds the plugin classes
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

		// ask user for number of rows & columns
		final GenericDialog gd = new GenericDialog("Display a Table");
		gd.addNumericField("Rows", 50, 0);
		gd.addNumericField("Columns", 10, 0);
		gd.showDialog();
		if (gd.wasCanceled()) return;
		final int rowCount = (int) gd.getNextNumber();
		final int colCount = (int) gd.getNextNumber();

		displayTable(rowCount, colCount);
	}

	private void displayTable(final int rowCount, final int colCount) {
		// create an ImageJ application context with the necessary services
		final ImageJ context = new ImageJ(DisplayService.class, UIService.class);

		// create a spreadsheet
		final GenericTable spreadsheet =
			new DefaultGenericTable(colCount, rowCount);
		for (int col = 0; col < colCount; col++) {
			final char letter = (char) ('A' + col);
			spreadsheet.setColumnHeader(col, "" + letter);
			for (int row = 0; row < rowCount; row++) {
				final String data = "" + letter + (row + 1);
				spreadsheet.set(col, row, data);
			}
		}

		// display the spreadsheet
		final UIService uiService = context.getService(UIService.class);
		uiService.show("Spreadsheet", spreadsheet);

	}

	/** Tests the plugin. */
	public static void main(final String... args) {
		new ij.ImageJ();
		IJ.runPlugIn(DisplayATable.class.getName(), "");
	}

}

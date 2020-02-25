/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

import ij.IJ;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

import net.imagej.ImageJ;

import org.scijava.Context;
import org.scijava.table.DefaultGenericTable;
import org.scijava.table.GenericTable;

/** Demonstrates use of the modern ImageJ API from a legacy ImageJ1 plugin. */
public class DisplayATable implements PlugIn {

	@Override
	public void run(final String arg) {
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
		// retrieve the ImageJ application context
		final Context context = (Context) IJ.runPlugIn("org.scijava.Context", "");
		final ImageJ ij = new ImageJ(context);

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
		ij.ui().show("Spreadsheet", spreadsheet);
	}

	/** Tests the plugin. */
	public static void main(final String... args) {
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		IJ.runPlugIn(DisplayATable.class.getName(), "");
	}

}

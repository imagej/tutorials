/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.tables;

import net.imagej.ImageJ;
import org.scijava.table.DefaultGenericTable;
import org.scijava.table.GenericTable;

import java.io.IOException;
import java.nio.file.Files;

/**
 * How to save and load a table
 *
 * @author Deborah Schmidt
 */
public class SaveAndLoadTable {

	private static void run() throws IOException {

		ImageJ ij = new ImageJ();

		//create table
		GenericTable table = new DefaultGenericTable();
		table.appendColumn("X");
		table.appendColumn("Y");
		table.appendRow();
		table.set("X", 0, 10);
		table.set("Y", 0, 20);
		table.appendRow();
		table.set("X", 1, 30);
		table.set("Y", 1, 40);


		// create temporary path to save image to
		String dest = Files.createTempFile("table", ".csv").toString();
		System.out.println("Saving table to " + dest);

		// save table
		ij.io().save(table, dest);

		// load and show saved image
		Object savedTable = null;
		try {
			savedTable = ij.io().open(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ij.ui().show("saved table", savedTable);

	}

	public static void main(String...args) throws IOException {
		run();
	}
}

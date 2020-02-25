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

/**
 * How to create a table
 *
 * @author Deborah Schmidt
 */
public class CreateTable {

	private static void run() {

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

		//show table
		ImageJ ij = new ImageJ();
		ij.ui().show("tables", table);

		//print value in the first row of column "X"
		System.out.println(table.get("X", 1));

	}

	public static void main(String...args) {
		run();
	}
}

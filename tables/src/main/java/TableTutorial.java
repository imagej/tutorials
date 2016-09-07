/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */


import net.imagej.ImageJ;
import net.imagej.table.*;

/**
 * This tutorial shows how to work with tables using ImageJ API
 *
 * Author: Robert Haase, Scientific Computing Facility, MPI-CBG Dresden, rhaase@mpi-cbg.de
 * Date: September 2016
 */
public class TableTutorial {


	public static void main(final String... args) {
		new TableTutorial();
	}

	final ImageJ ij;

	public TableTutorial()
	{
		// we need an instance of ImageJ to show the table finally.
		ij  = new ImageJ();

		// first, we create a table
		GenericTable table = createTable();

		// after creating a table, you can show it to the user
		ij.ui().show("Population of largest towns", table);

		// now we will analyse the content of the table
		analyseTable(table);
	}


	/**
	 * This function shows how to create a table with information
	 * about the largest towns in the world.
	 *
	 * @return a table with strings and numbers
	 */
	private GenericTable createTable()
	{
		// we create two columns
		GenericColumn nameColumn = new GenericColumn("Town");
		GenericColumn populationColumn = new GenericColumn("Population");

		// we fill the columns with information about the largest towns in the world.
		nameColumn.add("Karachi");
		populationColumn.add(23500000.0);

		nameColumn.add("Bejing");
		populationColumn.add(21516000.0);

		nameColumn.add("Sao Paolo");
		populationColumn.add(21292893.0);

		// but actually, the largest town is Shanghai,
		// so let's add it at the beginning of the table.
		nameColumn.add(0, "Shanghai");
		populationColumn.add(0, 24256800.0);

		// After filling the columns, you can create a table
		GenericTable table = new DefaultGenericTable();

		// and add the columns to that table
		table.add(nameColumn);
		table.add(populationColumn);

		return table;
	}

	/**
	 * This function shows how to read out information from tables,
	 * such as
	 * - the header of a column
	 * - an entry from the table
	 *
	 * @param table A table with two columns, Town and Population
	 */
	private void analyseTable(GenericTable table)
	{
		// read out the header of the second column
		String header = table.get(1).getHeader();

		ij.log().info("The header of the second column is: " + header);

		// get a certain column
		Column populationColumn = table.get("Population");

		// get a value from the first line in the column
		Object value = populationColumn.get(0);
		double populationOfLargestTown = Double.valueOf(value.toString());

		ij.log().info("The population of the largest town is: " + populationOfLargestTown);
	}
}

// @DatasetService ds
// @UIService ui

import java.io.File;
import javax.swing.JFileChooser;
import net.imagej.Dataset;
import net.imagej.ImageJ;

// ask the user for a file to open
chooser = new JFileChooser();

returnVal = chooser.showOpenDialog(null);
		
if (returnVal == JFileChooser.APPROVE_OPTION) {

	theFile = chooser.getSelectedFile();

	// load the dataset
	dataset = ds.open(theFile.getAbsolutePath());

	// display the dataset
	ui.show(dataset);
}
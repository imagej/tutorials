# @DatasetService ds
# @UIService ui

from java.io import File;
from javax.swing import JFileChooser;
from net.imagej import Dataset;
from net.imagej import ImageJ;

# ask the user for a file to open
chooser = JFileChooser();
returnVal = chooser.showOpenDialog(None);
		
if returnVal == JFileChooser.APPROVE_OPTION:

	theFile = chooser.getSelectedFile();

	# load the dataset
	dataset = ds.open(theFile.getAbsolutePath());

	# display the dataset
	ui.show(dataset);
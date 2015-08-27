# @DisplayService display
# @OpService ops
# @net.imagej.Dataset inputData

from net.imglib2.img.display.imagej import ImageJFunctions
from ij import IJ

# create a log kernel
logKernel=ops.create().kernelLog(inputData.numDimensions(), 3.0);

# convolve with log kernel
logFiltered=ops.filter().convolve(inputData, logKernel);

# display log filter result
display.createDisplay("log", logFiltered);

# otsu threshold and display
thresholded = ops.threshold().otsu(logFiltered)
display.createDisplay("thresholded", thresholded);

# convert to imagej1 imageplus so we can run analyze particles
impThresholded=ImageJFunctions.wrap(thresholded, "wrapped")

# convert to mask and analyze particles
IJ.run(impThresholded, "Convert to Mask", "")
IJ.run(impThresholded, "Analyze Particles...", "display add");

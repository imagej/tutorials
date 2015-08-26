# @OpService ops
# @net.imagej.Dataset inputData
# @DisplayService display

# to run this tutorial run 'file->Open Samples->Confocal Series' and make sure that
# confocal-series.tif is the active image

from net.imglib2 import FinalInterval;

# first take a look at the size and type of each dimension
for d in range(inputData.numDimensions()):
	print "axis d: type: "+str(inputData.axis(d).type())+" length: "+str(inputData.dimension(d))

# crop a channel
C0=ops.image().crop(inputData.getImgPlus(), FinalInterval([0,0,0,0], [inputData.dimension(0)-1, inputData.dimension(1)-1, 0, inputData.dimension(3)-1]))
C1=ops.image().crop(inputData.getImgPlus(), FinalInterval([0,0,1,0], [inputData.dimension(0)-1, inputData.dimension(1)-1, 1, inputData.dimension(3)-1]))

# crop both channels at z=12
z12=ops.image().crop(inputData.getImgPlus(), FinalInterval([0,0,0,12], [inputData.dimension(0)-1, inputData.dimension(1)-1, 1, 12]))

# crop channel 0 at z=12
C0z12=ops.image().crop(inputData.getImgPlus(), FinalInterval([0,0,0,12], [inputData.dimension(0)-1, inputData.dimension(1)-1, 0, 12]))

# crop an roi at channel 0, z=12
roiC0z12=ops.image().crop(inputData.getImgPlus(), FinalInterval([150,150,0,12], [200, 200, 0, 12]))

# display all the cropped images
display.createDisplay("C0", C0)
display.createDisplay("C1", C1)
display.createDisplay("z12", z12)
display.createDisplay("C0z12", C0z12)
display.createDisplay("roiC0z12", roiC0z12)




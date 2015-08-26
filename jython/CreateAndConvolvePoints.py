# @DisplayService display
# @OpService ops
# @int(value=128) xSize
# @int(value=128) ySize

from jarray import array
from net.imglib2 import Point;

from net.imglib2.algorithm.region.hypersphere import HyperSphere;

# create an empty image 
phantom=ops.create().img([xSize, ySize])

# use the randomAccess interface to place points in the image
randomAccess= phantom.randomAccess();
randomAccess.setPosition(array([xSize/2, ySize/2], 'l'));
randomAccess.get().setReal(255.0);	

randomAccess.setPosition(array([xSize/4, ySize/4], 'l'));
randomAccess.get().setReal(255.0);

location = Point(phantom.numDimensions())
location.setPosition(array([3*xSize/4, 3*ySize/4], 'l'));

hyperSphere = HyperSphere(phantom, location, 5);
		
for value in hyperSphere:
	value.setReal(16);

display.createDisplay("phantom", phantom)

# create psf using the gaussian kernel op (alternatively PSF could be an input to the script)
psf=ops.create().kernelGauss(array([10, 10], 'd'));

# convolve psf with phantom
convolved=ops.filter().convolve(phantom, psf);
display.createDisplay("convolved", convolved)
	
# @DisplayService display
# @OpService ops
# @int(value=128) xSize
# @int(value=128) ySize
# @int(value=128) zSize

from net.imglib2 import Point

from net.imglib2.algorithm.region.hypersphere import HyperSphere

# create an empty image 
phantom=ops.create().img([xSize, ySize, zSize])

# use the randomAccess interface to place points in the image
randomAccess= phantom.randomAccess()
randomAccess.setPosition([xSize/2, ySize/2, zSize/2])
randomAccess.get().setReal(255.0)

randomAccess.setPosition([xSize/4, ySize/4, zSize/4])
randomAccess.get().setReal(255.0)

location = Point(phantom.numDimensions())
location.setPosition([3*xSize/4, 3*ySize/4, 3*zSize/4])

hyperSphere = HyperSphere(phantom, location, 5)
		
for value in hyperSphere:
	value.setReal(16)

display.createDisplay("phantom", phantom)

# create psf using the gaussian kernel op (alternatively PSF could be an input to the script)
psf=ops.create().kernelGauss([5, 5, 5])

# convolve psf with phantom
convolved=ops.filter().convolve(phantom, psf)
display.createDisplay("convolved", convolved)

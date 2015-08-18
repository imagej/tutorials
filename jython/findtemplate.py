# @DisplayService display
# @OpService ops
# @net.imagej.Dataset image
# @net.imagej.Dataset template

'''
This example is an 'ops' version of:

http://fiji.sc/ImgLib2_Examples#Example_6c_-_Complex_numbers_and_Fourier_transforms

for which the code and images can be found

https://github.com/imglib/imglib2-tutorials

'''
from net.imglib2.img.display.imagej import ImageJFunctions;
from net.imglib2.type.numeric.complex import ComplexFloatType;
from net.imglib2.outofbounds import OutOfBoundsMirrorExpWindowingFactory;

from net.imglib2.converter import ComplexImaginaryFloatConverter;
from net.imglib2.converter import ComplexPhaseFloatConverter;
from net.imglib2.converter import ComplexRealFloatConverter;

from jarray import array

# perform fft of the template

# basic fft call with no parameters
templateFFT=ops.filter().fft(template.getImgPlus())

# alternatively to pass an outofbounds factory we have to pass every parameter.  We want:
# output='None', input=template, borderSize=10 by 10, fast='True', outOfBoundsFactor=OutOfBoundsMirrorExpWindowingFactory
#templateFFT=ops.fft(None, template.getImgPlus(), array([10, 10], 'l'), True, OutOfBoundsMirrorExpWindowingFactory(0.25));

# display fft (by default in generalized log power spectrum)
ImageJFunctions.show(templateFFT).setTitle("fft power spectrum");

# display fft phase spectrum
ImageJFunctions.show( templateFFT,ComplexPhaseFloatConverter() ).setTitle( "fft phase spectrum" );

# display fft real values
ImageJFunctions.show( templateFFT,ComplexRealFloatConverter() ).setTitle( "fft real values" );
        
# display fft imaginary values
ImageJFunctions.show( templateFFT, ComplexImaginaryFloatConverter() ).setTitle( "fft imaginary values" );

# complex invert the fft of the template
c = ComplexFloatType();
for  t in templateFFT:
	c.set(t);
	t.complexConjugate();
	c.mul(t);
	t.div(c);

# create Img memory for inverse FFT and compute inverse 
templateInverse=ops.create().img(array([template.dimension(0), template.dimension(1)], 'l'))

ops.filter().ifft(templateInverse, templateFFT)
display.createDisplay("template inverse", templateInverse)

# convolve templateInverse with image
final=ops.filter().convolve(image, templateInverse);
display.createDisplay("final", final)
	
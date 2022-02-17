/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.real.FloatType;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

@Plugin(type = Command.class, headless = true,
	menuPath = "Deconvolution>Deconvolution Command")
public class DeconvolutionCommand implements Command {

	@Parameter
	OpService ops;

	@Parameter
	LogService log;

	@Parameter
	UIService ui;

	@Parameter
	ImgPlus img;

	@Parameter
	Double sxy;

	@Parameter
	Double sz;

	@Parameter
	Integer numIterations;

	@Parameter(type = ItemIO.OUTPUT)
	RandomAccessibleInterval<FloatType> deconvolved;

	/**
	 * Run the deconvolution process
	 */
	@Override
	public void run() {
		final Img<FloatType> imgFloat = ops.convert().float32(img);

		RandomAccessibleInterval<FloatType> psf = null;

		if (imgFloat.numDimensions() == 3) {
			psf = ops.create().kernelGauss(new double[] { sxy, sxy, sz },
				new FloatType());
		}
		else {
			psf = ops.create().kernelGauss(new double[] { sxy, sxy },
				new FloatType());
		}

		log.info("starting deconvolution");
		final Img<FloatType> deconvolved = ops.create().img(imgFloat);
		ops.deconvolve().richardsonLucy(deconvolved, imgFloat, psf, numIterations);
		log.info("finished deconvolution");

	}
}

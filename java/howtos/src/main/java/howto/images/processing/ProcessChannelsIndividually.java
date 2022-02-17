/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.images.processing;

import net.imagej.ImageJ;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.region.hypersphere.HyperSphere;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.IntervalView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * How to split the channels of an image and process them individually
 *
 * @author Deborah Schmidt
 */
public class ProcessChannelsIndividually {

	private static void run() {

		ImageJ ij = new ImageJ();

		// create image with 3 channels
		RandomAccessibleInterval input = new ArrayImgFactory<>(new FloatType()).create(20, 30, 3);

		// defining which dimension of the image represents channels
		int channelDim = 2;

		// how many channels do we have?
		long numChannels = input.dimension(channelDim);

		List outputChannels = new ArrayList<>();

		// iterate over all channels
		for (int channelIndex = 0; channelIndex < numChannels; channelIndex++) {

			// get channel
			IntervalView<FloatType> inputChannel = ij.op().transform().hyperSliceView(input, channelDim, channelIndex);

			// draw a circle at position [10, 15] with radius 5
			RandomAccess ra = inputChannel.randomAccess();
			ra.setPosition(new long[]{10, 15});
			new HyperSphere<>(inputChannel, ra, 5).forEach(value -> value.set(25));

			// apply gauss
			RandomAccessibleInterval outputChannel = ij.op().filter().gauss(inputChannel, channelIndex);

			// add to output channel list
			outputChannels.add(outputChannel);
		}

		// stack channels back together
		RandomAccessibleInterval result = ij.op().transform().stackView(outputChannels);

		// display result
		ij.ui().show(result);

	}

	public static void main(String...args) throws IOException { run(); }

}

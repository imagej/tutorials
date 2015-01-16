/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.util.Random;

import net.imagej.ImageJ;
import net.imagej.ops.Op;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.IntervalIndexer;
import net.imglib2.util.Intervals;

import org.scijava.ItemIO;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * Creates a series of blobs across a new image based on given inputs.
 *
 * @author Aparna Pal
 */
@Plugin(type = Op.class, name = "blobs")
public class RandomBlobs<T extends RealType<T>> implements Op {

	@Parameter(type = ItemIO.OUTPUT)
	private RandomAccessibleInterval<T> image;

	@Parameter
	private LogService log;

	@Parameter
	private int blobNum;

	@Parameter
	private int blobSize;

	@Parameter
	private int xDim;

	@Parameter
	private int yDim;

	@Parameter(required = false)
	private long seed = 0xcafebabe;

	@Override
	public void run() {
		// produce a XxY float64 array-backed image using the input parameters
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final RandomAccessibleInterval<T> newImage =
			(RandomAccessibleInterval) ArrayImgs.doubles(xDim, yDim);
		image = newImage;
		final long[] pos = new long[image.numDimensions()];

		final long[] blobCenter = new long[image.numDimensions()];
		final long[] dims = new long[image.numDimensions()];
		image.dimensions(dims);

		final long total = Intervals.numElements(image);

		final Random r = new Random(seed);

		final RandomAccess<T> ra = image.randomAccess(image);
		for (int i = 0; i < blobNum; i++) {
			final long index = (long) (r.nextDouble() * total);
			IntervalIndexer.indexToPosition(index, dims, blobCenter);

			for (int j = 0; j < total; j++) {
				IntervalIndexer.indexToPosition(j, dims, pos);
				final double dist = distance(pos, blobCenter);
				if (dist > blobSize) continue;

				ra.setPosition(pos);
				final double norm = 1.0 - dist / blobSize;
				ra.get().setReal(Math.max(ra.get().getRealDouble(), norm));
			}
		}
	}

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		// Run our op
		final Object blobs = ij.op().run("blobs", 20, 16, 128, 128);

		// And display the result!
		ij.ui().showUI();
		ij.ui().show(blobs);
	}

	// -- Helper methods --

	/** Computes distance between the given position and a center point. */
	private double distance(final long[] pos, final long[] center) {
		long sumDistSquared = 0;
		for (int d = 0; d < center.length; d++) {
			final long dist = pos[d] - center[d];
			sumDistSquared += dist * dist;
		}
		return Math.sqrt(sumDistSquared);
	}

}

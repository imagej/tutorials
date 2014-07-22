/*
 * #%L
 * ImageJ OPS: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2014 Board of Regents of the University of
 * Wisconsin-Madison and University of Konstanz.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

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
 * Creates a series of blobs across a new image based on input parameters
 * specified in the below main method.
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

	@Override
	public void run() {
		// produce a XxY float64 array-backed image using the input parameters
		// specified in the main method
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final RandomAccessibleInterval<T> newImage =
			(RandomAccessibleInterval) ArrayImgs.doubles(xDim, yDim);
		image = newImage;
		final long[] pos = new long[image.numDimensions()];

		final long[] blobCenter = new long[image.numDimensions()];
		final long[] dims = new long[image.numDimensions()];
		image.dimensions(dims);

		final long total = Intervals.numElements(image);

		final RandomAccess<T> ra = image.randomAccess(image);
		for (int i = 0; i < blobNum; i++) {
			final long index = (long) (Math.random() * total);
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

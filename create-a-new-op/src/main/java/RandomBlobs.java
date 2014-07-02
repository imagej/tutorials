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

/**
 * Creates a series of blobs across an outputted ascii space based on input 
 * parameters specified in the below main method
 * @author Aparna Pal
 */

import java.util.ArrayList;

import net.imagej.ImageJ;
import net.imagej.ops.Op;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.IntervalIndexer;
import net.imglib2.util.Intervals;

import org.python.google.common.primitives.Longs;
import org.scijava.ItemIO;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "blobs")
public class RandomBlobs<T extends RealType<T>> implements Op {
	@Parameter(type = ItemIO.OUTPUT)
	private RandomAccessibleInterval<T> image;
	@Parameter
	private LogService log;
	@Parameter(type = ItemIO.INPUT)
	private int blobNum;
	@Parameter(type = ItemIO.INPUT)
	private int blobSize;
	@Parameter(type = ItemIO.INPUT)
	private int xDim;
	@Parameter(type = ItemIO.INPUT)
	private int yDim;

	@Override
	public void run() {
		// produce a XxY float64 array-backed image using the input parameters
		// specified in the main method
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final RandomAccessibleInterval<T> newImage = (RandomAccessibleInterval) ArrayImgs
				.doubles(xDim, yDim);
		image = newImage;

		final long[] pos = new long[image.numDimensions()];
		final long[] dims = new long[image.numDimensions()];
		image.dimensions(dims);
		
		//calculates the number of total intervals within the created image
		long total = Intervals.numElements(image);

		ArrayList<Long[]> axes = new ArrayList<Long[]>();
		RandomAccess<T> maybe = image.randomAccess(image);
		for (int i = 0; i < blobNum; i++) {
			
			//randomly select a central point within the image
			long index = (long) (Math.random() * total);
			IntervalIndexer.indexToPosition(index, dims, pos);
			maybe.setPosition(pos);
			maybe.get().setReal(1.0);
			
			//creates a neighborhood of radius r around each generated center
			axes = draw(pos[0], pos[1], blobSize, xDim, yDim);
			
			for (int j = 0; j < axes.size(); j++) {
				//Code currently uses objects as the LongArray class as I had 
				//implemented it made the program very confusing
				Long[] point = axes.get(j);
				for (int k = 0; k < pos.length; k++) {
					pos[k] = (long) point[k];
				}

				maybe.setPosition(pos);
				maybe.get().setReal(1.0);
			}

		}

	}

	public static ArrayList<Long[]> draw(long posX, long posY, int radius,
			int xDim, int yDim) {

		int x = (int) posX;
		int y = (int) posY;

		ArrayList<Long[]> coordinate = new ArrayList<Long[]>();
		for (int i = x - radius; i <= x + radius; i++) {
			for (int j = y - radius; j <= y + radius; j++) {
				double dx = (x - i);
				double dy = (y - j);

				if (Math.abs(dx * dx + dy * dy) < radius) {
					Long[] axes = new Long[2];
					axes[0] = (long) i;
					axes[1] = (long) j;
					
					//checks to make sure each calculated coordinate is within dimension
					axes = evaluate(axes, xDim, yDim);
					coordinate.add(axes);

				}
			}
		}
		return coordinate;

	}

	public static Long[] evaluate(Long[] axes, int xDim, int yDim) {
		axes[0] = calculate(axes[0], xDim);
		axes[1] = calculate(axes[1], yDim);

		return axes;
	}

	public static long calculate(long num, int Dim) {
		//if the passed number is not within the given dimension, it is wrapped 
		if (num > Dim) {
			num = num - (Dim + 1);
		} else if (num < 0) {
			num = num + Dim;
		}
		return num;
	}

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		// Run our op!
		final Object blobs = ij.op().run("blobs", 5, 5, 32, 32);

		// And what value did our op return?
		ij.log().info(blobs);

		Object ascii = ij.op().ascii(blobs);
		ij.log().info(ascii);
	}
}

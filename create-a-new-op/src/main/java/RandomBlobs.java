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

import java.util.ArrayList;

import net.imagej.ImageJ;
import net.imagej.ops.Op;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.IntervalIndexer;

import org.python.google.common.primitives.Longs;
import org.scijava.ItemIO;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "blobs")
public class RandomBlobs<T extends RealType<T>> implements Op {
	@Parameter(type = ItemIO.OUTPUT)
	private RandomAccessibleInterval<T> image;
	private boolean isDone = false;
	@Parameter
	private LogService log;
	private static int xDim = 32;
	private static int yDim = 32; 

	public static void main(final String... args) throws Exception {
		final ImageJ ij = new ImageJ();

		// Run our op!
		final Object blobs = ij.op().run("blobs", 5, 5);

		// And what value did our op return?
		ij.log().info(blobs);

		Object ascii = ij.op().ascii(blobs);
		ij.log().info(ascii);
	}

	@Parameter(type = ItemIO.INPUT)
	private int blobNum;
	
	@Parameter(type = ItemIO.INPUT)
	private int blobSize;

	@Override
	public void run() {
		// produce a 256x256 float64 array-backed image by default
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final RandomAccessibleInterval<T> newImage = (RandomAccessibleInterval) ArrayImgs
				.doubles(xDim,yDim);
		image = newImage;

		int x = 0;
		int y = 0;

		final long[] pos = new long[image.numDimensions()];
		final long[] dims = new long[image.numDimensions()];
		image.dimensions(dims);

		long total = 1;
		for (int i = 0; i < dims.length; i++) {
			total *= dims[i];
		}
		ArrayList<Long[]> axes = new ArrayList<Long[]>();
		RandomAccess<T> maybe = image.randomAccess(image);
		for (int i = 0; i < blobNum; i++) {

			long index = (long) (Math.random() * total);
			IntervalIndexer.indexToPosition(index, dims, pos);
			maybe.setPosition(pos);
			maybe.get().setReal(1.0);
			axes = draw(pos[0], pos[1], blobSize);
			for(int j = 0; j < axes.size(); j++)
			{
				Long[] point = axes.get(j); 
				for(int k = 0; k < pos.length; k++)
				{
					pos[k] = (long) point[k];
				}
				
				maybe.setPosition(pos);
				maybe.get().setReal(1.0);
			}

		}

	}

	public static ArrayList<Long[]> draw(long posX, long posY, int radius) {

		long x = posX;
		long y = posY;

		ArrayList<Long[]> coordinate = new ArrayList<Long[]>();
		for (int i = 0; i <= 2 * posX; i++) {
			for (int j = 0; j <= 2 * posY; j++) {
				double dx = (x - i);
				double dy = (y - j);

				if (Math.abs(dx * dx + dy * dy) < radius) {
					Long[] axes = new Long[2];
					axes[0] = (long)i;
					axes[1] = (long)j;
					if(i < xDim && i > 0 && j < yDim && j > 0)
					{
					coordinate.add(axes);
					}

				}
			}
		}
		return coordinate;

	}

}

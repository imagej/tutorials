/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import net.imagej.ops.Op;
import net.imglib2.Cursor;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.basictypeaccess.array.DoubleArray;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Op.class, name = "ramp")
public class Ramp<T extends RealType<T>> implements Op {

	@Parameter(type = ItemIO.OUTPUT)
	private ArrayImg<DoubleType, DoubleArray> rampImg;

	@Override
	public void run() {
		rampImg = ArrayImgs.doubles(256, 256);

		final Cursor<DoubleType> c = rampImg.localizingCursor();
		final long[] pos = new long[rampImg.numDimensions()];
		while (c.hasNext()) {
			c.fwd();
			c.localize(pos);
			c.get().setReal(sum(pos));
		}
	}

	private float sum(long[] pos) {
		float sum = 0;
		for (long p : pos) sum += p;
		return sum;
	}

}

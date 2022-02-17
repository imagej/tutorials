/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.ops;

import net.imagej.ImageJ;
import net.imagej.ops.AbstractOp;
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
public class RampOp<T extends RealType<T>> extends AbstractOp {

	@Parameter(type = ItemIO.OUTPUT)
	private ArrayImg<DoubleType, DoubleArray> rampImg;

	@Override
	public void run() {
		rampImg = ArrayImgs.doubles(256, 256);

		final Cursor<DoubleType> c = rampImg.localizingCursor();
		final long[] pos = new long[rampImg.numDimensions()];

		// Iterate the image and get the each pixel location
		// Every pixel value is assigned its locations sum,
		// so generate the ramp pattern image.
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

    public static void main(final String... args) throws Exception {
        final ImageJ ij = new ImageJ();

        // Run our op
        final Object ramp = ij.op().run("ramp");

        // And display the result!
        ij.ui().showUI();
        ij.ui().show(ramp);
    }

}

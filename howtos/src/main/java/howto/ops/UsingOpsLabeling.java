/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */
package howto.ops;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.ops.OpService;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.labeling.ConnectedComponents.StructuringElement;
import net.imglib2.converter.Converter;
import net.imglib2.converter.Converters;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelingMapping;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.Type;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.util.Util;
import net.imglib2.view.Views;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * This tutorial shows how to do binary image labeling in ImageJ, and how to use
 * ConnectedComponents to do connected components analysis.
 * <p>
 * A main method is provided so this class can be run directly from IDE.
 * </p>
 * <p>
 * Also, because this class implements Command and is annotated as an
 * {@code @Plugin}, it will show up in the ImageJ menus: under
 * Tutorials&gt;Labeling Filtering, as specified by the {@code menuPath} field
 * of the {@code @Plugin} annotation.
 * </p>
 */
@Plugin(type = Command.class, menuPath = "Tutorials>Labeling Filtering")
public class UsingOpsLabeling<T extends RealType<T>> implements Command {

    @Parameter(type = ItemIO.INPUT)
    private Dataset dataset;

    @Parameter
    private UIService uiService;

    @Parameter
    private OpService opService;

    @Parameter
    private CommandService commandService;

    @Parameter(label = "Invert image")
    private boolean invert;

    /**
     * The run() method is where we do the actual 'work' of the command.
     */
    @Override
    public void run() {
        final RandomAccessibleInterval<T> img = (RandomAccessibleInterval<T>)dataset.getImgPlus();

        final RandomAccessibleInterval<BitType> binaryImg =
                (RandomAccessibleInterval<BitType>)opService.threshold().otsu((IterableInterval<T>)img);

        // defines a label generator
        final Iterator<Integer> labels = new Iterator<Integer>()
        {
            private int i = 1;

            @Override
            public boolean hasNext()
            {
                return true;
            }

            @Override
            public Integer next()
            {
                return i++;
            }

            @Override
            public void remove()
            {}
        };

        if (invert) {
            final RandomAccessibleInterval<BitType> invertImg = opService.create().img(binaryImg);
            opService.run("image.invert", invertImg, binaryImg);

            final long[] dims = new long[img.numDimensions()];
            // get image dimension
            img.dimensions(dims);
            // create labeling index image
            final RandomAccessibleInterval<UnsignedShortType> indexImg = ArrayImgs.unsignedShorts(dims);
            final ImgLabeling<Integer, UnsignedShortType> labeling = new ImgLabeling<>(indexImg);

            // use OPS to do the image labeling
            opService.run("labeling.cca", labeling, invertImg, StructuringElement.FOUR_CONNECTED);

            // You can also use the ConnectedComponents.labelAllConnectedComponents method
            // Label all connected components of a binary image
            //ConnectedComponents.labelAllConnectedComponents(invertImg, labeling, labels, StructuringElement.FOUR_CONNECTED);

            final Img<ARGBType> argb = ArrayImgs.argbs(dims);
            colorLabels(labeling, new ColorStream().iterator(), argb);

            // TODO: Use UIService.show method
            ImageJFunctions.show(argb);
        } else {
            final long[] dims = new long[img.numDimensions()];
            // get image dimension
            img.dimensions(dims);
            // create labeling index image
            final RandomAccessibleInterval<UnsignedShortType> indexImg = ArrayImgs.unsignedShorts(dims);
            final ImgLabeling<Integer, UnsignedShortType> labeling = new ImgLabeling<>(indexImg);

            opService.run("labeling.cca", labeling, binaryImg, StructuringElement.FOUR_CONNECTED);

            // Label all connected components of a binary image
            // ConnectedComponents.labelAllConnectedComponents(binaryImg, labeling, labels, StructuringElement.FOUR_CONNECTED);

            final Img<ARGBType> argb = ArrayImgs.argbs(dims);
            colorLabels(labeling, new ColorStream().iterator(), argb);

            // TODO: Use UIService.show method
            ImageJFunctions.show(argb);
        }
    }

    /**
     * Assign distinct colors to different labels.
     * @param labeling labeling object
     * @param colors color iterator
     * @param output colored image
     * @param <C> color type
     * @param <L> label type
     */
    private static <C extends Type<C>, L> void colorLabels(
            final ImgLabeling<L, ?> labeling,
            final Iterator<C> colors,
            final RandomAccessibleInterval<C> output)
    {
        final HashMap<Set<?>, C> colorTable = new HashMap<>();
        final LabelingMapping<?> mapping = labeling.getMapping();
        final int numLists = mapping.numSets();
        final C color = Util.getTypeFromInterval(output).createVariable();
        colorTable.put(mapping.labelsAtIndex(0), color);

        for (int i = 1; i < numLists; ++i)
        {
            final Set<?> list = mapping.labelsAtIndex(i);
            colorTable.put(list, colors.next());
        }

        final Iterator<C> o = Views.flatIterable(output).iterator();
        IterableInterval<C> colorConverter =
                Converters.convert(Views.flatIterable(labeling), new LabelingTypeConverter<C>(colorTable), color);
        for (final C c : colorConverter)
        {
            o.next().set(c);
        }
    }

    private static final class LabelingTypeConverter<T extends Type<T>> implements Converter<LabelingType<?>, T>
    {
        private final HashMap<Set<?>, T> colorTable;

        public LabelingTypeConverter(final HashMap<Set<?>, T> colorTable)
        {
            this.colorTable = colorTable;
        }

        @Override
        public void convert(final LabelingType<?> input, final T output)
        {
            final T t = colorTable.get(input);
            if (t != null)
                output.set(t);
        }
    }

    private static final class ColorStream implements Iterable<ARGBType>
    {
        final static protected double goldenRatio = 0.5 * Math.sqrt( 5 ) + 0.5;

        final static protected double stepSize = 6.0 * goldenRatio;

        final static protected double[] rs = new double[] { 1, 1, 0, 0, 0, 1, 1 };

        final static protected double[] gs = new double[] { 0, 1, 1, 1, 0, 0, 0 };

        final static protected double[] bs = new double[] { 0, 0, 0, 1, 1, 1, 0 };

        final static protected int interpolate( final double[] xs, final int k, final int l, final double u, final double v )
        {
            return ( int ) ( ( v * xs[ k ] + u * xs[ l ] ) * 255.0 + 0.5 );
        }

        final static protected int argb( final int r, final int g, final int b )
        {
            return ( ( ( r << 8 ) | g ) << 8 ) | b | 0xff000000;
        }

        final static int get( final long index )
        {
            double x = goldenRatio * index;
            x -= ( long ) x;
            x *= 6.0;
            final int k = ( int ) x;
            final int l = k + 1;
            final double u = x - k;
            final double v = 1.0 - u;

            final int r = interpolate( rs, k, l, u, v );
            final int g = interpolate( gs, k, l, u, v );
            final int b = interpolate( bs, k, l, u, v );

            return argb( r, g, b );
        }

        @Override
        final public Iterator<ARGBType> iterator()
        {
            return new Iterator< ARGBType >()
            {
                long i = -1;

                @Override
                public boolean hasNext()
                {
                    return true;
                }

                @Override
                public ARGBType next()
                {
                    return new ARGBType( get( ++i ) );
                }

                @Override
                public void remove()
                {}
            };
        }
    }

    /*
     * This main method is for convenience - so you can run this command
     * directly from Eclipse (or other IDE).
     * <p>
     * It will launch ImageJ and then run this command using the
     * CommandService. This is equivalent to clicking "Tutorials&gt;Labeling
     * Filtering" in the UI.
     * </p>
     */
    public static void main(final String... args) throws Exception {
        // create the ImageJ application context with all available services
        final ImageJ ij = new ImageJ();
        ij.ui().showUI();

        // ask the user for a file to open
        final File file = ij.ui().chooseFile(null, "open");

        if (file != null) {
            // load the dataset
            final Dataset dataset = ij.scifio().datasetIO().open(file.getPath());

            // show the image
            ij.ui().show(dataset);

            // invoke the plugin
            ij.command().run(UsingOpsLabeling.class, true);
        }
    }

}

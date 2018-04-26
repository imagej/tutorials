import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.axis.Axes;
import net.imagej.axis.AxisType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

	public class Narf2 extends AbstractConverter<String, Dataset> {

		@Parameter
		DatasetService datasetService;
	
		@SuppressWarnings("unchecked")
		@Override
		public <T> T convert(Object src, Class<T> dest) {
			Dataset ds = datasetService.create(new DoubleType(), new long[]{10,  10}, (String) src, new AxisType[]{Axes.X, Axes.Y});
			return (T) ds;
		}

		@Override
		public Class<Dataset> getOutputType() {
			return Dataset.class;
		}

		@Override
		public Class<String> getInputType() {
			return String.class;
		}

	}
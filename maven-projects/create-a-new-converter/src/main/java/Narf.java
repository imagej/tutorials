import net.imagej.Dataset;

import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.plugin.Plugin;

public class Narf extends AbstractConverter<Dataset, String> {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(Object src, Class<T> dest) {
		return (T) "Test";
	}

	@Override
	public Class<String> getOutputType() {
		return String.class;
	}

	@Override
	public Class<Dataset> getInputType() {
		return Dataset.class;
	}

}

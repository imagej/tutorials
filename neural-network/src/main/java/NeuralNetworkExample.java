import java.io.File;
import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.type.numeric.RealType;

public class NeuralNetworkExample {
	public static <T extends RealType<T>> void main(final String... args) throws IOException {
		ImageJ ij = net.imagej.Main.launch();

		String inputName = "./resources/cnn_input.png";
		String targetName = "./resources/cnn_target.png";

		final Dataset input = ij.dataset().open(new File(inputName).getAbsolutePath());
		final Dataset target = ij.dataset().open(new File(targetName).getAbsolutePath());

		ij.ui().show(input);
		ij.ui().show(target);
	}

}

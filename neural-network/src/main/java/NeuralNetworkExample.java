import java.io.File;
import java.io.IOException;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

public class NeuralNetworkExample {
	public static <T extends RealType<T>> void main(final String... args) throws IOException {
		ImageJ ij = net.imagej.Main.launch();

		String inputName = "./resources/cnn_input.png";
		String targetName = "./resources/cnn_target.png";

		final Dataset input = ij.dataset().open(new File(inputName).getAbsolutePath());
		final Dataset target = ij.dataset().open(new File(targetName).getAbsolutePath());

		ij.ui().show(input);
		ij.ui().show(target);

		Img<T> imgInput = (Img<T>) input.getImgPlus().getImg();
		Img<T> imgTarget = (Img<T>) target.getImgPlus().getImg();

		Img<DoubleType> dtypeInput = (Img<DoubleType>) ij.op().convert().float64(imgInput);
		Img<DoubleType> dtypeTarget = (Img<DoubleType>) ij.op().convert().float64(imgTarget);

		Img<DoubleType> kernel = (Img<DoubleType>) ij.op().learning().convolutionLayerBackProp(dtypeInput, dtypeTarget);

		ij.ui().show(kernel);
	}

}

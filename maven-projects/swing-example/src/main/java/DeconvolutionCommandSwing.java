/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

import javax.swing.SwingUtilities;

import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.FloatType;

import org.scijava.ItemIO;
import org.scijava.app.StatusService;
import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.thread.ThreadService;
import org.scijava.ui.UIService;

@Plugin(type = Command.class, headless = true,
	menuPath = "Deconvolution>Deconvolution Swing")
public class DeconvolutionCommandSwing implements Command {

	@Parameter
	OpService ops;

	@Parameter
	LogService log;

	@Parameter
	UIService ui;

	@Parameter
	CommandService cmd;

	@Parameter
	StatusService status;

	@Parameter
	ThreadService thread;

	private static DeconvolutionDialog dialog = null;

	@Parameter(type = ItemIO.OUTPUT)
	RandomAccessibleInterval<FloatType> deconvolved;

	/**
	 * show a dialog and give the dialog access to required IJ2 Services
	 */
	@Override
	public void run() {

		SwingUtilities.invokeLater(() -> {
			if (dialog == null) {
				dialog = new DeconvolutionDialog();
			}
			dialog.setVisible(true);

			dialog.setOps(ops);
			dialog.setLog(log);
			dialog.setStatus(status);
			dialog.setCommand(cmd);
			dialog.setThread(thread);
			dialog.setUi(ui);

		});
	}
}

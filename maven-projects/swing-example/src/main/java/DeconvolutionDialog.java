/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

import org.scijava.app.StatusService;
import org.scijava.command.CommandService;
import org.scijava.log.LogService;
import org.scijava.thread.ThreadService;
import org.scijava.ui.UIService;

import ij.IJ;
import ij.ImagePlus;

public class DeconvolutionDialog extends JDialog {

	private OpService ops;
	private LogService log;
	private StatusService status;
	private CommandService cmd;
	private ThreadService thread;
	private UIService ui;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		try {
			final DeconvolutionDialog dialog = new DeconvolutionDialog();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DeconvolutionDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			final JButton btnDeconvolve = new JButton("Deconvolve via ThreadService");
			btnDeconvolve.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {

					// start deconvolution with the scijava ThreadService
					thread.run(() -> {
						deconvolve();
					});

				}
			});
			contentPanel.add(btnDeconvolve);
		}
		{
			final JButton btnDeconvolveViaCommand = new JButton(
				"Deconvolve via Command");
			btnDeconvolveViaCommand.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					deconvolveViaCommand();
				}
			});
			contentPanel.add(btnDeconvolveViaCommand);
		}
		{
			final JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				final JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				final JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Perform deconvolution
	 */
	public <T extends RealType<T>> void deconvolve() {
		final ImagePlus imp = IJ.getImage();

		final Img img = ImageJFunctions.wrap(imp);

		final Img<FloatType> imgFloat = ops.convert().float32(img);

		RandomAccessibleInterval<FloatType> psf = null;

		if (imgFloat.numDimensions() == 3) {
			psf = ops.create().kernelGauss(new double[] { 3, 3, 7 }, new FloatType());
		}
		else {
			psf = ops.create().kernelGauss(new double[] { 3, 3 }, new FloatType());
		}

		log.info("starting deconvolution with thread service");
		final RandomAccessibleInterval<T> deconvolved = ops.deconvolve()
			.richardsonLucy(imgFloat, psf, 50);
		log.info("finished deconvolution");

		ui.show(deconvolved);
	}

	/**
	 * perform deconvolution by calling a command
	 */
	public void deconvolveViaCommand() {

		final ImagePlus imp = IJ.getImage();
		final Img img = ImageJFunctions.wrap(imp);

		cmd.run(DeconvolutionCommand.class, true, "img", img, "sxy", 3, "sz", 7,
			"numIterations", 50);

	}

	public OpService getOps() {
		return ops;
	}

	public void setOps(final OpService ops) {
		this.ops = ops;
	}

	public LogService getLog() {
		return log;
	}

	public void setLog(final LogService log) {
		this.log = log;
	}

	public StatusService getStatus() {
		return status;
	}

	public void setStatus(final StatusService status) {
		this.status = status;
	}

	public CommandService getCommand() {
		return cmd;
	}

	public void setCommand(final CommandService command) {
		this.cmd = command;
	}

	public ThreadService getThread() {
		return thread;
	}

	public void setThread(final ThreadService thread) {
		this.thread = thread;
	}

	public UIService getUi() {
		return ui;
	}

	public void setUi(final UIService ui) {
		this.ui = ui;
	}

}

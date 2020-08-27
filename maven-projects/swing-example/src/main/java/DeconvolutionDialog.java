/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;

import org.scijava.Context;
import org.scijava.app.StatusService;
import org.scijava.command.CommandService;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.thread.ThreadService;
import org.scijava.ui.UIService;

import ij.IJ;
import ij.ImagePlus;

public class DeconvolutionDialog extends JDialog {

	@Parameter
	private OpService ops;

	@Parameter
	private LogService log;

	@Parameter
	private StatusService status;

	@Parameter
	private CommandService cmd;

	@Parameter
	private ThreadService thread;

	@Parameter
	private UIService ui;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public DeconvolutionDialog(final Context ctx) {
		ctx.inject(this);
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
					thread.run(() -> deconvolve());
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
	}

	/**
	 * Perform deconvolution
	 */
	public void deconvolve() {
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
		final Img<FloatType> deconvolved = ops.create().img(imgFloat);
		ops.deconvolve().richardsonLucy(deconvolved, imgFloat, psf, 50);
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
}

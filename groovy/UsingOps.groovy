// @OpService ops
// @CommandService cmd
// @LogService log
// @UIService ui

import net.imagej.ops.Op;
import net.imglib2.FinalDimensions
import net.imglib2.type.numeric.real.DoubleType;

// how many ops?
final int opCount = cmd.getCommandsOfType(Op.class).size();
log.info(opCount+" ops are available");

// learn about an op
log.info( ops.help("math.add") );

// add two numbers
log.info("What is 2+5? "+ops.math().add(2, 5));

// create a new blank image
dims = new FinalDimensions((Long[])[150, 100]);
blank = ops.create().img(dims);

// fill in the image with a sinusoid using a formula
formula = "10 * (Math.cos(0.3*p[0]) + Math.sin(0.3*p[1]))";
sinusoid = ops.image().equation(blank, formula);

// add a constant value to an image
ops.math().add(sinusoid, 13.0);

// generate a gradient image using a formula
gradient = ops.image().equation(ops.create().img(dims), "p[0]+p[1]");

// add the two images
composite = ops.math().add(sinusoid, gradient);

// dump the image to the console
ascii = ops.image().ascii(composite);
log.info("Composite image:\n" + ascii);

// show the image in a window
ui.show("composite", composite);

// execute an op on every pixel of an image
addOp = ops.op("math.add", DoubleType.class, new DoubleType(5.0));
//ops.map(composite, composite, addOp); 
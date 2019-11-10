/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 *
 * This work was supported by the German Research Foundation (DFG) under the code JU3110/1-1 (Fiji Software Sustainability).
 */

package howto.services;

import net.imagej.ImageJ;
import org.scijava.Context;
import org.scijava.io.IOService;

/**
 * How to call a service
 *
 * @author Deborah Schmidt
 */
public class CallService {

	/**
	 * .. get an instance of the {@link IOService}
	 */
	public static void call() {

		// get an IOService instance
		ImageJ ij = new ImageJ();
		IOService io = ij.io();

		// print the name of the class implementing the IOService
		System.out.println(io.getClass().getName());

	}

	/**
	 * .. get an instance of the {@link IOService} without using the {@link ImageJ} gateway
	 */
	public static void callWithoutImageJ() {

		// get an IOService instance
		Context context = new Context(IOService.class);
		IOService io = context.getService(IOService.class);

		// print the name of the class implementing the IOService
		System.out.println(io.getClass().getName());

	}

	public static void main(String...args) {
		call();
		callWithoutImageJ();
	}

}

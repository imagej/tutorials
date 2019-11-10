/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 *
 * This work was supported by the German Research Foundation (DFG) under the code JU3110/1-1 (Fiji Software Sustainability).
 */

package howto.userinput;

import net.imagej.ImageJ;
import org.scijava.ui.DialogPrompt;

/**
 * How to ask a yes / no question
 *
 * @author Deborah Schmidt
 */
public class AskYesNo {

	private static void run() {
		ImageJ ij = new ImageJ();
		final DialogPrompt.Result result =
				ij.ui().showDialog("Do you want to run this action?", "Action confirmation",
						DialogPrompt.MessageType.QUESTION_MESSAGE,
						DialogPrompt.OptionType.YES_NO_OPTION);

		if( result == DialogPrompt.Result.YES_OPTION ) {
			System.out.println("Action confirmed");
		} else {
			System.out.println("Action denied");
		}
	}

	public static void main(String...args) {
		run();
	}

}

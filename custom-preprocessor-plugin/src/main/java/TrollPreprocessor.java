/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.module.Module;
import imagej.module.process.AbstractPreprocessorPlugin;
import imagej.module.process.PreprocessorPlugin;
import imagej.ui.DialogPrompt.MessageType;
import imagej.ui.DialogPrompt.OptionType;
import imagej.ui.DialogPrompt.Result;
import imagej.ui.UIService;
import imagej.util.MersenneTwisterFast;

import org.scijava.plugin.Plugin;

/**
 * A custom preprocessor plugin that augments every command execution.
 * <p>
 * It adds an insulting confirmation dialog before each command runs.
 * </p>
 */
@Plugin(type = PreprocessorPlugin.class)
public class TrollPreprocessor extends AbstractPreprocessorPlugin {

	private static final String[] INSULTS = { "essentially useless",
		"a waste of your time", "complete garbage" };

	private static final String[] PLATITUDES = { "It's probably for the best.",
		"It's easier this way.", "A wise decision.", "That's what I thought." };

	/** Random number generator for insults and platitudes. */
	private final MersenneTwisterFast random = new MersenneTwisterFast();

	@Override
	public void process(final Module module) {
		final String title = module.getInfo().getTitle();

		// Select a random insult.
		final String insult =
			"The \"" + title + "\" command is " + random(INSULTS) + ".";

		final String demand = "You must pay a toll of one gold coin to continue.";
		final String confirmation = "Are you sure?";
		final String message = insult + "\n" + demand + "\n" + confirmation;

		final MessageType messageType = MessageType.WARNING_MESSAGE;
		final OptionType optionType = OptionType.YES_NO_OPTION;

		// Prompt for confirmation.
		final UIService uiService = getContext().getService(UIService.class);
		final Result result =
			uiService.showDialog(message, "Troll", messageType, optionType);

		// Cancel the command execution if the user does not agree.
		canceled = result != Result.YES_OPTION;
		if (!canceled) return;

		cancelReason = random(PLATITUDES);
	}

	/** Chooses a random item from the given list. */
	private String random(final String... list) {
		return list[random.nextInt(list.length)];
	}

	/** Tests the custom preprocessor plugin. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		imagej.Main.main(args);
	}

}

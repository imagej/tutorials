package howto.extensions;

import net.imagej.ImageJ;
import org.scijava.command.DynamicCommand;
import org.scijava.module.MutableModuleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * How to modify a command programmatically and run it
 */
public class ModifyCommand {

	private static void run() throws ExecutionException, InterruptedException {

		ImageJ ij = new ImageJ();
		ij.launch();

		// create choices
		List<String> columnNames = new ArrayList<>();
		columnNames.add("item1");
		columnNames.add("item2");
		// set a name for the choices
		String choiceName = "name";

		// create an instance of a dynamic command
		final DynamicCommand command = new ExampleDynamicCommand();
		// inject the context so that the command can use services
		ij.context().inject(command);

		// create a string input
		MutableModuleItem<String> choiceInput = command.addInput( choiceName, String.class );
		// set the label and the choices of the input
		choiceInput.setLabel(choiceName);
		choiceInput.setChoices(columnNames);

		//run the command
		ij.module().run(command, true).get();

		// get the choice of the user and print it
		String choiceValue = (String) command.getInput(choiceName);
		System.out.println(choiceValue);

	}

	public static void main(String...args) throws ExecutionException, InterruptedException {
		run();
	}
}

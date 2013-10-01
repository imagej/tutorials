/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.ImageJ;
import imagej.command.Command;
import imagej.command.Previewable;
import imagej.util.ColorRGB;
import imagej.widget.ChoiceWidget;
import imagej.widget.NumberWidget;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.scijava.ItemIO;
import org.scijava.ItemVisibility;
import org.scijava.app.StatusService;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * Demonstration of various parameter types and their widgets.
 * 
 * @author Curtis Rueden
 */
@Plugin(type = Command.class, headless = true,
	menuPath = "Plugins>ImageJ Tutorials>Widget Demo")
public class WidgetDemo implements Command, Previewable {

	// These constants are for use with the labels below. You don't have
	// to use constants like this, but we do it here so we can reuse the
	// text in both the parameter declaration and the run() method.

	private static final String TOGGLE_LABEL = "-- Toggles --";
	private static final String NUMERIC_LABEL = "-- Numeric --";
	private static final String TEXT_LABEL = "-- Text --";
	private static final String OBJECT_LABEL = "-- Objects --";
	private static final String STYLE_LABEL = "-- Widget styles --";
	private static final String MISC_LABEL = "-- Miscellaneous --";

	// The following service parameters are populated automatically
	// by the service framework before the command is executed.
	//
	// The LogService is used for writing messages to a log, while
	// the StatusService is used to control the ImageJ status bar.

	@Parameter
	private LogService log;

	@Parameter
	private StatusService statusService;

	// The rest of the parameters are typically handled by ImageJ using an
	// InputHarvester preprocessing step, which prompts the user with a dialog
	// box full of widgets. The values chosen there will be automatically
	// assigned to the fields here.
	//
	// Parameters with "MESSAGE" visibility are displayed as labels.

	@Parameter(visibility = ItemVisibility.MESSAGE)
	private final String header =
		"This command demonstrates various widgets in action!";

	// This section demonstrates toggle types (i.e., booleans),
	// which are typically rendered as checkboxes.

	@Parameter(visibility = ItemVisibility.MESSAGE)
	private final String labelToggles = TOGGLE_LABEL;

	@Parameter(label = "boolean")
	private boolean pBoolean;

	@Parameter(label = "Boolean")
	private Boolean oBoolean;

	// This section demonstrates numeric types, including primitives and
	// their corresponding Object wrapper types (e.g., double is wrapped
	// by java.lang.Double), as well as other Number classes such as
	// BigInteger for arbitrary precision integer arithmetic, and
	// BigDecimal for arbitrary precision decimal arithmetic.

	@Parameter(visibility = ItemVisibility.MESSAGE)
	private final String labelNumeric = NUMERIC_LABEL;

	@Parameter(label = "byte")
	private byte pByte;

	@Parameter(label = "double")
	private double pDouble;

	@Parameter(label = "float")
	private float pFloat;

	@Parameter(label = "int")
	private int pInt;

	@Parameter(label = "long")
	private long pLong;

	@Parameter(label = "short")
	private short pShort;

	@Parameter(label = "Byte")
	private Byte oByte;

	@Parameter(label = "Double")
	private Double oDouble;

	@Parameter(label = "Float")
	private Float oFloat;

	@Parameter(label = "Integer")
	private Integer oInt;

	@Parameter(label = "Long")
	private Long oLong;

	@Parameter(label = "Short")
	private Short oShort;

	@Parameter
	private BigInteger bigInteger;

	@Parameter
	private BigDecimal bigDecimal;

	// This section demonstrates textual types such as characters and strings,
	// as well as strings chosen from a limited set (i.e., multiple choice).

	@Parameter(visibility = ItemVisibility.MESSAGE)
	private final String labelText = TEXT_LABEL;

	@Parameter(label = "char")
	private char pChar;

	@Parameter(label = "Character")
	private Character oChar;

	@Parameter(label = "String")
	private String string;

	@Parameter(label = "multiple choice", choices = { "The", "quick", "brown",
		"fox", "jumps", "over", "the", "lazy", "dog" })
	private String choice;

	// This section demonstrates some other built-in object types, including
	// files and colors. You can also specify any arbitrary object as an
	// input parameter, and ImageJ will present a multiple choice widget with
	// options pulled from those known to the ObjectService (this is, e.g.,
	// how Dataset input parameters work), but to keep things simple, we
	// don't demonstrate that here.

	@Parameter(visibility = ItemVisibility.MESSAGE)
	private final String labelObjects = OBJECT_LABEL;

	@Parameter
	private File file;

	@Parameter
	private Date date;

	@Parameter
	private ColorRGB color;

	// This section demonstrates how to use alternative widget styles to
	// customize the widget used for a particular parameter. Note that the
	// style setting is merely a *hint* indicating what you would prefer to
	// be used; it is up to individual user interface implementations to
	// provide support for those styles. In some cases, a UI may not support
	// a particular style, in which case it will fall back to a style it
	// *does* support instead.

	@Parameter(visibility = ItemVisibility.MESSAGE)
	private final String labelStyle = STYLE_LABEL;

	@Parameter(label = "number (spinner)",
		style = NumberWidget.SPINNER_STYLE, min = "0", max = "1000")
	private int spinnerNumber;

	@Parameter(label = "number (slider)", style = NumberWidget.SLIDER_STYLE,
		min = "0", max = "1000", stepSize = "50")
	private int sliderNumber;

	@Parameter(label = "number (scroll bar)",
		style = NumberWidget.SCROLL_BAR_STYLE, min = "0", max = "1000")
	private int scrollBarNumber;

	@Parameter(label = "radio buttons (horizontal)",
		style = ChoiceWidget.RADIO_BUTTON_HORIZONTAL_STYLE, choices = { "Yes",
			"No", "Maybe" })
	private String choiceRadioH;

	@Parameter(label = "radio buttons (vertical)",
		style = ChoiceWidget.RADIO_BUTTON_VERTICAL_STYLE, choices = { "Loved",
			"Really liked", "Liked", "Disliked", "Really disliked" })
	private String choiceRadioV;

	// This section demonstrates how to use callbacks. A "callback" is a
	// method that is executed whenever a specific parameter value changes.
	// They can be used for many purposes; in our case, we use them to keep
	// two parameter values synchronized, with the value of "twoX" always
	// being equal to twice that of "x".

	@Parameter(visibility = ItemVisibility.MESSAGE)
	private final String labelMisc = MISC_LABEL;

	@Parameter(label = "x", callback = "xChanged")
	private float x;

	@Parameter(label = "2x",
		description = "Demonstrates callback functionality. Equal to double x.",
		callback = "twoXChanged")
	private float twoX;

	// Here we demonstrate the "preview" feature. The preview method is a
	// special callback that occurs every time any parameter changes. In this
	// demonstration, we update the ImageJ status bar with the current value
	// of the "message" parameter.

	@Parameter(description = "Demonstrates preview functionality by "
		+ "displaying the given message in the ImageJ status bar.")
	private String message = "Type a status message here.";

	// This last parameter is an output parameter: its type is
	// ItemIO.OUTPUT rather than the usual ItemIO.INPUT. Output parameter
	// values are not shown in the InputHarvester dialog, but rather expected
	// to be populated during command execution (i.e., in the run method).
	// Then, after execution is complete, ImageJ takes care of displaying the
	// output parameters in the user interface as appropriate.

	@Parameter(label = "Results", type = ItemIO.OUTPUT)
	private String result;

	// The "run" method is executed after the user presses the "OK" button
	// and all parameter values are confirmed. Our run method dumps all
	// parameter values into the "result" output parameter, which ImageJ
	// then takes care of displaying for us.

	@Override
	public void run() {
		final StringBuilder sb = new StringBuilder();

		append(sb, "WidgetDemo results:");

		append(sb, "");
		append(sb, TOGGLE_LABEL);
		append(sb, "\tboolean = " + pBoolean);
		append(sb, "\tBoolean = " + oBoolean);

		append(sb, "");
		append(sb, NUMERIC_LABEL);
		append(sb, "\tbyte = " + pByte);
		append(sb, "\tdouble = " + pDouble);
		append(sb, "\tfloat = " + pFloat);
		append(sb, "\tint = " + pInt);
		append(sb, "\tlong = " + pLong);
		append(sb, "\tshort = " + pShort);
		append(sb, "\tByte = " + oByte);
		append(sb, "\tDouble = " + oDouble);
		append(sb, "\tFloat = " + oFloat);
		append(sb, "\tInteger = " + oInt);
		append(sb, "\tLong = " + oLong);
		append(sb, "\tShort = " + oShort);
		append(sb, "\tBigInteger = " + bigInteger);
		append(sb, "\tBigDecimal = " + bigDecimal);

		append(sb, "");
		append(sb, TEXT_LABEL);
		append(sb, "\tchar = " + "'" + pChar + "' [" +
			Character.getNumericValue(pChar) + "]");
		final String oCharValue =
			oChar == null ? "null" : "" + Character.getNumericValue(oChar);
		append(sb, "\tCharacter = " + "'" + oChar + "' [" + oCharValue + "]");
		append(sb, "\tString = " + string);
		append(sb, "\tchoice = " + choice);

		append(sb, OBJECT_LABEL);
		append(sb, "\tFile = " + file);
		append(sb, "\tDate = " + date);
		append(sb, "\tcolor = " + color);

		append(sb, "");
		append(sb, STYLE_LABEL);
		append(sb, "\tnumber (spinner) = " + spinnerNumber);
		append(sb, "\tnumber (slider) = " + sliderNumber);
		append(sb, "\tnumber (scroll bar) = " + scrollBarNumber);
		append(sb, "\tradio buttons (horizontal) = " + choiceRadioH);
		append(sb, "\tradio buttons (vertical) = " + choiceRadioV);

		append(sb, "");
		append(sb, MISC_LABEL);
		append(sb, "\tx = " + x);
		append(sb, "\t2x = " + twoX);
		append(sb, "\tmessage = " + message);

		result = sb.toString();
	}

	/** The number of previews so far. */
	private int previews = 0;

	// You can control how previews work by overriding the "preview" method.
	// The code written in this method will be automatically executed every
	// time a widget value changes. In this case, we log a message with the
	// count of previews so far, and show the current value of the "message"
	// parameter in the ImageJ status bar.

	@Override
	public void preview() {
		log.info("WidgetDemo: " + ++previews + " previews and counting");
		statusService.showStatus(message);
	}

	// You can add code to handle what happens if the user presses the
	// "Cancel" button. This is often necessary, for example, if your
	// "preview" method manipulates data; the "cancel" method will then need
	// to revert any changes done by the previews back to the original state.
	// In our case, we log a simple message stating the command was canceled.

	@Override
	public void cancel() {
		log.info("WidgetDemo: canceled");
	}

	// The following methods are known as "callbacks" which get executed
	// whenever the value of a specific linked parameter changes. In this
	// case, the two methods below keep the "x" and "twoX" parameter values
	// in sync, ensuring that the value of twoX is always twice that of x.

	/** Executed whenever the {@link #x} parameter changes. */
	protected void xChanged() {
		log.info("WidgetDemo: x changed");
		twoX = x * 2;
	}

	/** Executed whenever the {@link #twoX} parameter changes. */
	protected void twoXChanged() {
		log.info("WidgetDemo: 2x changed");
		x = twoX / 2;
	}

	// -- Helper methods --

	private void append(final StringBuilder sb, final String s) {
		sb.append(s + "\n");
	}

	/** Launches the widget demo. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = imagej.Main.launch(args);

		// Launch the "Widget Demo" command right away.
		ij.command().run(WidgetDemo.class);
	}

}

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */
package howto.ui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.scijava.Priority;
import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.ChoiceWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

@Plugin(type = InputWidget.class, priority = Priority.HIGH)
public class CustomWidget extends SwingInputWidget<String> implements ChoiceWidget<JPanel> {

    public static final String CUSTOM_STYLE = "custom";

    private final JButton addButton = new JButton("Add");

    private DefaultListModel<String> choices;
    private JList<String> listComponent;


    // -- InputWidget methods --

    @Override
    public String getValue() {
        return listComponent.getSelectedValue();
    }

    // -- WrapperPlugin methods --

    @Override
    public void set(final WidgetModel model) {
        super.set(model);

        addButton.addActionListener(actionEvent -> addChoice());

        JPanel component = getComponent();

        choices = new DefaultListModel<>();
        final String[] items = model.getChoices();
        for (final String item : items) {
            choices.addElement(item);
        }

        listComponent = new JList<>(choices);
        listComponent.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateModel();
            }
        });

        JPanel container = new JPanel();

        container.setLayout(new BorderLayout());
        container.add(listComponent, BorderLayout.CENTER);
        container.add(addButton, BorderLayout.EAST);

        component.add(container);

        refreshWidget();
    }

    // -- Typed methods --

    @Override
    public boolean supports(final WidgetModel model) {
        return super.supports(model) && model.isMultipleChoice() && model.isStyle(CUSTOM_STYLE);
    }

    // -- Helper methods --

    private void addChoice() {
        JFrame frame = new JFrame("Input");
        String choice = JOptionPane.showInputDialog(frame, "Enter animal");
        if (choice != null) {
            choices.addElement(choice);
        }
    }

    // -- AbstractUIInputWidget methods ---

    @Override
    public void doRefresh() { /* No-op. */ }
}

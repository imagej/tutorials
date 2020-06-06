import org.scijava.Priority;
import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.ChoiceWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

import javax.swing.*;
import java.awt.*;

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

        container.setLayout(new GridLayout(2, 1));
        container.add(addButton);
        container.add(listComponent);

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

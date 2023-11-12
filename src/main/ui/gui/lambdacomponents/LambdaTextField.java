package ui.gui.lambdacomponents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

//Represents a generic, text-editable JTextField which is TEXT_FIELD_LENGTH columns long and
// performs a given lambda function (actionPerformedMethod) when the user presses enter within the JTextField
public class LambdaTextField extends JTextField {
    static final int TEXT_FIELD_LENGTH = 10;
    String input;

    //EFFECTS: constructs a new JTextField with the overwritten actionPerformed method defined
    // as the given lambda function parameter, actionPerformedMethod.
    public LambdaTextField(BiConsumer<ActionEvent, String> actionPerformedMethod) {

        super(TEXT_FIELD_LENGTH);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = getInput();
                actionPerformedMethod.accept(e, input);
            }
        });
    }

    //EFFECTS: returns the text currently within this JTextField; in other words, returns the user input.
    public String getInput() {
        return this.getText();
    }
}

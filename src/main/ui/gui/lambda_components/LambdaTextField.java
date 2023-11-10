package ui.gui.lambda_components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

public class LambdaTextField extends JTextField {
    static final int TEXT_FIELD_LENGTH = 10;
    String input;

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

    public String getInput() {
        return this.getText();
    }
}

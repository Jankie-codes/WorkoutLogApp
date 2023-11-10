package ui.gui.lambda_components;

import java.util.function.Consumer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LambdaButton extends JButton {
    public LambdaButton(String buttonText, Consumer<ActionEvent> actionPerformedMethod) {
        super(buttonText);
        setActionCommand(buttonText);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformedMethod.accept(e);
            }
        });
    }
}

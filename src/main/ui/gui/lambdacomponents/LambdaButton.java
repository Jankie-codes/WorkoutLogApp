package ui.gui.lambdacomponents;

import java.util.function.Consumer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a generic JButton with a given text on the button (buttonText),
// and a lambda function which is performed when the button is pressed (actionPerformedMethod).
public class LambdaButton extends JButton {

    //EFFECTS: constructs a new JButton with text set to buttonText, and the overwritten actionPerformed method defined
    // as the given lambda function parameter, actionPerformedMethod.
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

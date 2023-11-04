package ui.sidebar_buttons;

import ui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//THIS CLASS SHOULD BE DELETED BEFORE PUSHING FINAL VERSION. IT WAS A TEMPLATE USED FOR MAKING LAMBDA BUTTON

public class AddSetButton extends JButton {

    public AddSetButton(WorkoutLogAppGUI controller) {
        super("Add set");
        setActionCommand("addSet");
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setPanelVisible(0);
            }
        });
    }




}

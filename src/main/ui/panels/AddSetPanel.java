package ui.panels;

import ui.*;

import javax.swing.*;
import java.awt.*;

public class AddSetPanel extends JPanel {
    public AddSetPanel() {
        super();
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        setBackground(Color.red);
        add(new JLabel("TEXT HERE"));
        setVisible(true);
    }
}

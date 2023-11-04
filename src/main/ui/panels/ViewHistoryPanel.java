package ui.panels;

import ui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;

public class ViewHistoryPanel extends JPanel {
    public ViewHistoryPanel() {
        super();
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        setBackground(Color.green);
        add(new JLabel("TEXT HERE"));
        setVisible(true);
    }
}

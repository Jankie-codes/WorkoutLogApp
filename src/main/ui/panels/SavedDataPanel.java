package ui.panels;

import ui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;

public class SavedDataPanel extends JPanel {
    public SavedDataPanel() {
        super();
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        setBackground(Color.yellow);
        add(new JLabel("Saved data successfully."));
        setVisible(true);
    }
}

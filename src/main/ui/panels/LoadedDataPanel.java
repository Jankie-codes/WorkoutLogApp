package ui.panels;

import ui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;

public class LoadedDataPanel extends JPanel {

    public LoadedDataPanel() {
        super();
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        setBackground(Color.orange);
        add(new JLabel("Loaded data successfully."));
        setVisible(true);
    }
}

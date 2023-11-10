package ui.gui;

import ui.gui.lambda_components.LambdaButton;

import javax.swing.*;
import java.awt.*;

public class SideMenu extends JPanel {
    GridBagConstraints gbc;

    public SideMenu(WorkoutLogAppGUI controller) {
        super();
        setSize(WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        setBackground(Color.lightGray);
        gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton addSetButton = new LambdaButton("Add set", e -> {
            controller.hideAllPanels();
            controller.setPanelVisible(0);
        });
        add(addSetButton, gbc);
        addSetButton.setVisible(true);
        gbc.gridy = 1;
        JButton viewWorkoutHistoryButton = new LambdaButton("View workout history", e -> {
            controller.hideAllPanels();
            controller.refreshViewHistoryPanel();
            controller.setPanelVisible(1);
        });
        add(viewWorkoutHistoryButton, gbc);
        viewWorkoutHistoryButton.setVisible(true);
        gbc.gridy = 2;
        JButton saveWorkoutLogButton = new LambdaButton("Save workout log", e -> {
            controller.hideAllPanels();
            controller.saveUser();
            controller.refreshSavedDataPanel();
            controller.setPanelVisible(2);
        });
        add(saveWorkoutLogButton, gbc);
        saveWorkoutLogButton.setVisible(true);
        gbc.gridy = 3;
        JButton loadWorkoutLogButton = new LambdaButton("Load workout log", e -> {
            controller.hideAllPanels();
            controller.loadUser();
            controller.refreshLoadedDataPanel();
            controller.setPanelVisible(3);
        });
        add(loadWorkoutLogButton, gbc);
        loadWorkoutLogButton.setVisible(true);

        setVisible(true);
    }
}

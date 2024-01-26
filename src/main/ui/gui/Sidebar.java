package ui.gui;

import ui.gui.lambdacomponents.LambdaButton;

import javax.swing.*;
import java.awt.*;

//Represents the sidebar with a collection of buttons to display right-hand-side panels.
// The sidebar appears on the left side of the window.
public class Sidebar extends JPanel {
    GridBagConstraints gbc;
    final JButton[] buttons = new JButton[7];

    //EFFECTS: constructs a new sidebar (with appropriate size and layout),
    // initializes all sidebar buttons and draws them.
    public Sidebar(WorkoutLogAppGUI controller) {
        super();
        setSize(WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight());
        setLayout(new GridBagLayout());
        setBackground(Color.lightGray);
        gbc = new GridBagConstraints();

        initButtons(controller);

        gbc.gridx = 0;
        gbc.gridy = 0;
        displayButtonsInLine();

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: initializes all sidebar buttons with appropriate names and
    // lambda expressions to be performed when each is clicked.
    @SuppressWarnings("methodlength")
    private void initButtons(WorkoutLogAppGUI controller) {
        buttons[0] = new LambdaButton("Add set", e -> {
            controller.hideAllPanels();
            controller.setPanelVisible(0);
        });

        buttons[1] = new LambdaButton("Workout history", e -> {
            controller.hideAllPanels();
            controller.refreshViewHistoryPanel();
            controller.setPanelVisible(1);
        });

        buttons[2] = new LambdaButton("View 1RMs", e -> {
            controller.hideAllPanels();
            controller.refreshView1RMsPanel();
            controller.setPanelVisible(4);
        });

        buttons[3] = new LambdaButton("View PR history", e -> {
            controller.hideAllPanels();
            controller.refreshPRHistoryPanel();
            controller.setPanelVisible(5);
        });

        buttons[4] = new LambdaButton("Relative strength", e -> {
            controller.hideAllPanels();
            controller.refreshRelativeStrengthPanel();
            controller.setPanelVisible(6);
        });

        buttons[5] = new LambdaButton("Save workout log", e -> {
            controller.hideAllPanels();
            controller.saveUser();
            controller.refreshSavedDataPanel();
            controller.setPanelVisible(2);
        });

        buttons[6] = new LambdaButton("Load workout log", e -> {
            controller.hideAllPanels();
            controller.loadUser();
            controller.refreshLoadedDataPanel();
            controller.setPanelVisible(3);
        });
    }

    //MODIFIES: this
    //EFFECTS: draws each sidebar button in this.buttons in order, and in a vertical line.
    private void displayButtonsInLine() {
        for (JButton button : buttons) {
            add(button, gbc);
            button.setVisible(true);
            gbc.gridy++;
        }
    }
}

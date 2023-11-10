package ui.gui.panels;

import ui.gui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class SavedDataPanel extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel savedSuccessfullyOrFailedText;
    JLabel savedAtTimeText;

    public SavedDataPanel() {
        super();
        gbc.gridx = 0;
        gbc.gridy = 0;
        savedSuccessfullyOrFailedText = new JLabel("");
        savedAtTimeText = new JLabel("");

        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        add(savedSuccessfullyOrFailedText, gbc);
        gbc.gridy++;
        refreshSavedAtDateText();
        add(savedAtTimeText, gbc);

        setVisible(true);
    }

    public void refreshSavedAtDateText() {
        savedAtTimeText.setText("Saved at " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public void setSavedSuccessfullyText(String jsonStoreDirectoryPath) {
        savedSuccessfullyOrFailedText.setForeground(Color.black);
        savedSuccessfullyOrFailedText.setText("Saved most recent workout log data to " + jsonStoreDirectoryPath);
    }

    public void warnUnableToSave(String jsonStoreDirectoryPath) {
        savedAtTimeText.setVisible(false);
        savedSuccessfullyOrFailedText.setForeground(Color.red);
        savedSuccessfullyOrFailedText.setText("Unable to write to file: " + jsonStoreDirectoryPath);
    }




}

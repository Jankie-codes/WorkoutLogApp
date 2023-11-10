package ui.gui.panels;

import ui.gui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

//BUG: subsequently after pressing load data, new data cannot be saved for some reason
public class LoadedDataPanel extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel loadedSuccessfullyOrFailedText;
    JLabel loadedAtTimeText;

    public LoadedDataPanel() {
        super();
        gbc.gridx = 0;
        gbc.gridy = 0;
        loadedSuccessfullyOrFailedText = new JLabel("");
        loadedAtTimeText = new JLabel("");

        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        add(loadedSuccessfullyOrFailedText, gbc);
        gbc.gridy++;
        refreshLoadedAtDateText();
        add(loadedAtTimeText, gbc);

        setVisible(true);
    }

    public void refreshLoadedAtDateText() {
        loadedAtTimeText.setText("Saved at " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public void setLoadedSuccessfullyText(String jsonStoreDirectoryPath) {
        loadedSuccessfullyOrFailedText.setForeground(Color.black);
        loadedSuccessfullyOrFailedText.setText("Loaded pre-existing workout log data from " + jsonStoreDirectoryPath);
    }

    public void warnUnableToLoad(String jsonStoreDirectoryPath) {
        loadedAtTimeText.setVisible(false);
        loadedSuccessfullyOrFailedText.setForeground(Color.red);
        loadedSuccessfullyOrFailedText.setText("Unable to read from file: " + jsonStoreDirectoryPath);
    }
}

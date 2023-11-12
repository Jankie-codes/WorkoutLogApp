package ui.gui.panels;

import ui.gui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

//Represents the right-hand-side JPanel made visible to the user when attempting to load previous workout log data.
// This JPanel appears when the "Load data" button on the sidebar is pressed.
public class LoadedDataPanel extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel loadedSuccessfullyOrFailedText;
    JLabel loadedAtTimeText;

    //EFFECTS: constructs a new LoadedDataPanel (with appropriate size and layout) and draws on it
    // text which states whether data was loaded successfully or failed,
    // as well as text stating the time in which data was last loaded
    public LoadedDataPanel() {
        super();
        gbc.gridx = 0;
        gbc.gridy = 0;
        loadedSuccessfullyOrFailedText = new JLabel("");
        loadedAtTimeText = new JLabel("");
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH,
                WorkoutLogAppGUI.getContentPaneHeight());
        setLayout(new GridBagLayout());

        add(loadedSuccessfullyOrFailedText, gbc);
        gbc.gridy++;
        refreshLoadedAtDateText();
        add(loadedAtTimeText, gbc);

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: updates the loadedAtTimeText to display the current time in the following format: hh:mm:ss (24hr time)
    public void refreshLoadedAtDateText() {
        loadedAtTimeText.setText("Loaded at " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    //MODIFIES: this
    //EFFECTS: displays black text stating that data was successfully loaded.
    public void setLoadedSuccessfullyText(String jsonStoreDirectoryPath) {
        loadedSuccessfullyOrFailedText.setForeground(Color.black);
        loadedSuccessfullyOrFailedText.setText("Loaded pre-existing workout log data from " + jsonStoreDirectoryPath);
    }

    //MODIFIES: this
    //EFFECTS: displays error text, in red, stating that the app has been unable to read from file.
    public void warnUnableToLoad(String jsonStoreDirectoryPath) {
        loadedAtTimeText.setVisible(false);
        loadedSuccessfullyOrFailedText.setForeground(Color.red);
        loadedSuccessfullyOrFailedText.setText("Unable to read from file: " + jsonStoreDirectoryPath);
    }
}

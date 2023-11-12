package ui.gui.panels;

import ui.gui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

//Represents the right-hand-side JPanel which is made visible to the user when attempting to save data.
// Appears when the "save data" button on the sidebar is pressed.
public class SavedDataPanel extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel savedSuccessfullyOrFailedText;
    JLabel savedAtTimeText;

    //EFFECTS: constructs a new SavedDataPanel (with appropriate size and layout) and draws on it
    // text which states whether data was saved successfully or failed,
    // as well as text stating the time in which data was last saved
    public SavedDataPanel() {
        super();
        gbc.gridx = 0;
        gbc.gridy = 0;
        savedSuccessfullyOrFailedText = new JLabel("");
        savedAtTimeText = new JLabel("");

        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH,
                WorkoutLogAppGUI.getContentPaneHeight());
        setLayout(new GridBagLayout());
        add(savedSuccessfullyOrFailedText, gbc);
        gbc.gridy++;
        refreshSavedAtTimeText();
        add(savedAtTimeText, gbc);

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: updates the savedAtTimeText to display the current time in the following format: hh:mm:ss (24hr time)
    public void refreshSavedAtTimeText() {
        savedAtTimeText.setText("Saved at " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    //MODIFIES: this
    //EFFECTS: displays black text stating that data was successfully saved.
    public void setSavedSuccessfullyText(String jsonStoreDirectoryPath) {
        savedSuccessfullyOrFailedText.setForeground(Color.black);
        savedSuccessfullyOrFailedText.setText("Saved most recent workout log data to " + jsonStoreDirectoryPath);
    }

    //MODIFIES: this
    //EFFECTS: displays error text, in red, stating that the app has been unable to write to file.
    public void warnUnableToSave(String jsonStoreDirectoryPath) {
        savedAtTimeText.setVisible(false);
        savedSuccessfullyOrFailedText.setForeground(Color.red);
        savedSuccessfullyOrFailedText.setText("Unable to write to file: " + jsonStoreDirectoryPath);
    }
}

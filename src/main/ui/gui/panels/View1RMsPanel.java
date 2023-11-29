package ui.gui.panels;

import model.Exercise;
import model.User;
import ui.gui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;

//Represents the right-hand-side JPanel made visible to the user when attempting to view one-rep-maxes for each
// exercise. This panel appears when the "1RMs" button is pressed.
public class View1RMsPanel extends JPanel {
    static final int WORKOUT_HISTORY_TEXT_BOX_WIDTH = 300;
    static final int WORKOUT_HISTORY_TEXT_BOX_HEIGHT = 300;

    GridBagConstraints gbc;

    JTextPane listOf1RMs;
    JScrollPane scrollPane;

    JLabel footnote;

    User userToView;

    //EFFECTS: constructs a new RelativeStrengthPanel (with appropriate size and layout) and instantiates and draws all
    // necessary components
    public View1RMsPanel(User userToView) {
        super();
        this.userToView = userToView;
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH,
                WorkoutLogAppGUI.getContentPaneHeight());
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        listOf1RMs = new JTextPane();
        listOf1RMs.setEditable(false);
        listOf1RMs.setText(getListOf1RMsString(this.userToView));
        scrollPane = new JScrollPane(listOf1RMs);
        scrollPane.setPreferredSize(new Dimension(WORKOUT_HISTORY_TEXT_BOX_WIDTH, WORKOUT_HISTORY_TEXT_BOX_HEIGHT));
        footnote = new JLabel("*Theoretical 1RMs calculated using the Berger equation.");
        footnote.setFont(new Font(footnote.getFont().getName(), Font.PLAIN, 10));

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(scrollPane, gbc);
        gbc.gridy++;
        add(footnote, gbc);

        setVisible(true);
    }

    //EFFECTS: Returns a String of the user's one-rep-maxes for each exercise.
    private String getListOf1RMsString(User userToView) {
        String listOfPRsString = "";
        listOfPRsString += "Viewing theoretical one-rep-maxes for each exercise:\n";
        for (Exercise exercise : userToView.getExercises()) {
            listOfPRsString += "\n";
            listOfPRsString += ((exercise.getName()) + ": " + exercise.getOneRepMax() + " " + WorkoutLogAppGUI.UNITS);
        }
        return listOfPRsString;
    }

    //MODIFIES: this
    //EFFECTS: refreshes the listOf1RMs JTextPane to show the most recent one-rep-maxes.
    public void refresh1RMsText(User userToView) {
        listOf1RMs.setText(getListOf1RMsString(userToView));
    }

    //REQUIRES: must be called whenever the user field in the associated WorkoutLogAppGUI object is re-instantiated.
    //MODIFIES: this
    //EFFECTS: updates this.userToView to point to the given updatedUser.
    // Helps ensure that this.userToView and the user field in the associated WorkoutLogAppGUI
    // object point to the same memory location.
    public void updateUserFieldPointer(User updatedUser) {
        this.userToView = updatedUser;
    }
}

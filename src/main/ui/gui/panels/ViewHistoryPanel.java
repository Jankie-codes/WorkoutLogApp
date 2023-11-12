package ui.gui.panels;

import model.*;
import ui.gui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;

//Represents the right-hand-side JPanel which is made visible to the user when attempting to view full workout history.
// Appears when the user clicks the "View history" button.
// Has a userToView field which extracts user data from an associated WorkoutLogAppGUI object.
public class ViewHistoryPanel extends JPanel {
    static final int WORKOUT_HISTORY_TEXT_BOX_WIDTH = 300;
    static final int WORKOUT_HISTORY_TEXT_BOX_HEIGHT = 300;

    GridBagConstraints gbc;

    JTextPane workoutHistory;
    JScrollPane scrollPane;

    User userToView;

    //EFFECTS: Constructs a ViewHistoryPanel (with appropriate size and layout)
    // and draws an un-editable JTextPane (with scrollbar) showing workout history.
    public ViewHistoryPanel(User userToView) {
        super();
        this.userToView = userToView;
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH,
                WorkoutLogAppGUI.getContentPaneHeight());
        setLayout(new GridBagLayout());

        workoutHistory = new JTextPane();
        workoutHistory.setEditable(false);
        workoutHistory.setText(getWorkoutHistoryString(this.userToView));
        scrollPane = new JScrollPane(workoutHistory);
        scrollPane.setPreferredSize(new Dimension(WORKOUT_HISTORY_TEXT_BOX_WIDTH, WORKOUT_HISTORY_TEXT_BOX_HEIGHT));
        add(scrollPane);

        setVisible(true);
    }

    //EFFECTS: returns a String of the user's full workout history.
    private String getWorkoutHistoryString(User userToView) {
        String workoutHistoryString = "";

        workoutHistoryString += "Full Workout History:\n";
        for (Workout workout : userToView.getWorkouts()) {
            workoutHistoryString += "\n";
            workoutHistoryString += workout.getDate();
            for (ExerciseSet set : workout.getSets()) {
                workoutHistoryString += "\n";
                workoutHistoryString += ("\t" + set.getExercise().getName() + ": " + set.getWeight()
                        + WorkoutLogAppGUI.UNITS + " "
                        + set.getReps()
                        + " reps");
            }
        }
        workoutHistoryString += ("\n-WORKOUT HISTORY END-\n");

        return workoutHistoryString;
    }

    //MODIFIES: this
    //EFFECTS: refreshes the workoutHistory JTextPane to show the most recent workout history.
    public void refreshWorkoutHistoryText(User userToView) {
        workoutHistory.setText(getWorkoutHistoryString(userToView));
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

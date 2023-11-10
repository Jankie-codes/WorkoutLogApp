package ui.gui.panels;

import model.*;
import ui.gui.WorkoutLogAppGUI;

import javax.swing.*;
import java.awt.*;

public class ViewHistoryPanel extends JPanel {
    static final int WORKOUT_HISTORY_TEXT_BOX_WIDTH = 300;
    static final int WORKOUT_HISTORY_TEXT_BOX_HEIGHT = 300;

    GridBagConstraints gbc;

    JTextPane workoutHistory;
    JScrollPane scrollPane;

    User userToView;

    public ViewHistoryPanel(User userToView) {
        super();
        this.userToView = userToView;
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        //setBackground(Color.green);

        workoutHistory = new JTextPane();
        workoutHistory.setEditable(false);
        workoutHistory.setText(getWorkoutHistoryString(this.userToView));
        scrollPane = new JScrollPane(workoutHistory);
        scrollPane.setPreferredSize(new Dimension(WORKOUT_HISTORY_TEXT_BOX_WIDTH, WORKOUT_HISTORY_TEXT_BOX_HEIGHT));
        add(scrollPane);

        setVisible(true);
    }

    //!!!
    public String getWorkoutHistoryString(User userToView) {
        String workoutHistoryString = "";

        workoutHistoryString += "Full Workout History:\n";
        for (Workout workout : userToView.getWorkouts()) {
            workoutHistoryString += "\n";
            workoutHistoryString += workout.getDate();
            for (ExerciseSet set : workout.getSets()) {
                workoutHistoryString += "\n";
                workoutHistoryString += ("\t" + set.getExercise().getName() + ": " + set.getWeight() + "lbs "
                        + set.getReps()
                        + " reps");
            }
        }
        workoutHistoryString += ("\n-WORKOUT HISTORY END-\n");

        return workoutHistoryString;
    }

    public void refreshWorkoutHistoryText(User userToView) {
        workoutHistory.setText(getWorkoutHistoryString(userToView));
    }

    //REQUIRES: must be used conjuctively with !!!
    public void updateUserFieldPointer(User updatedUser) {
        this.userToView = updatedUser;
    }
}

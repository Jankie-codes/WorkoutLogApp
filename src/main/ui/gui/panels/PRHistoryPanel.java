package ui.gui.panels;

import model.ExerciseSet;
import model.User;
import model.Exercise;
import model.exceptions.ExerciseNotFoundException;
import ui.gui.WorkoutLogAppGUI;
import ui.gui.lambdacomponents.LambdaTextField;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.TreeMap;
import java.util.Map;

//Represents the right-hand-side panel made visible to the user when attempting to view PR history
// for any given exercise. This panel appears when the "PR history" sidebar button is pressed.
public class PRHistoryPanel extends JPanel {
    public static final int PR_HISTORY_TEXT_BOX_WIDTH = 350;
    public static final int PR_HISTORY_TEXT_BOX_HEIGHT = 220;
    public static final int LIST_OF_EXERCISES_TEXT_BOX_WIDTH = 100;
    public static final int LIST_OF_EXERCISES_TEXT_BOX_HEIGHT = 100;

    User userToView;

    JTextPane prHistory;
    JScrollPane prHistoryScrollPane;

    Exercise exerciseChosen;

    JLabel chooseExerciseText;
    LambdaTextField chooseExerciseTextField;
    JTextPane listOfExercises;
    JScrollPane exercisesScrollPane;
    JLabel exerciseNotFoundText;

    GridBagConstraints gbc;

    //EFFECTS: constructs a new PRHistoryPanel (with appropriate size and layout) and defines and draws all
    // necessary components
    public PRHistoryPanel(User userToView) {
        super();

        this.userToView = userToView;
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH,
                WorkoutLogAppGUI.getContentPaneHeight());
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        initComponents();
        drawComponents();

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: draws all necessary components onto this JPanel
    @SuppressWarnings("methodlength")
    private void drawComponents() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(chooseExerciseText, gbc);
        gbc.gridy++;
        add(chooseExerciseTextField, gbc);
        gbc.gridy++;
        add(exerciseNotFoundText, gbc);
        gbc.gridy--;
        gbc.gridy--;
        gbc.gridx++;
        add(Box.createHorizontalStrut(10), gbc);
        gbc.gridx++;
        gbc.gridheight = 3;
        add(exercisesScrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridy++;
        add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;
        gbc.gridheight = 1;
        add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;
        gbc.gridwidth = 3;
        add(prHistoryScrollPane, gbc);
    }

    //MODIFIES: this
    //EFFECTS: initializes all components which will later be drawn onto this JPanel
    private void initComponents() {
        prHistory = new JTextPane();
        prHistory.setEditable(false);
        prHistoryScrollPane = new JScrollPane(prHistory);
        prHistoryScrollPane.setPreferredSize(new Dimension(PR_HISTORY_TEXT_BOX_WIDTH, PR_HISTORY_TEXT_BOX_HEIGHT));
        chooseExerciseText = new JLabel("Choose an exercise (then press enter)");
        chooseExerciseTextField = new LambdaTextField((e, input) -> {
            processInputOrPrintError(input);
        });
        listOfExercises = new JTextPane();
        listOfExercises.setEditable(false);
        listOfExercises.setText(this.userToView.getExerciseNamesString());
        exercisesScrollPane = new JScrollPane(listOfExercises);
        exercisesScrollPane.setPreferredSize(new Dimension(LIST_OF_EXERCISES_TEXT_BOX_WIDTH,
                LIST_OF_EXERCISES_TEXT_BOX_HEIGHT));
        exerciseNotFoundText = new JLabel("");
        exerciseNotFoundText.setForeground(Color.RED);
    }

    //MODIFIES: this
    //EFFECTS: parses the user's input when attempting to choose an exercise to view PR history for.
    // If input is valid, removes exerciseNotFoundText (the error text) and sets exerciseChosen to the given exercise.
    // Then, prints the PR history for the given exercise.
    // If input is invalid or the given exercise does not exist, catch ExerciseNotFoundException and print red error
    // text stating "given exercise not found".
    private void processInputOrPrintError(String input) {
        try {
            exerciseChosen = this.userToView.getExercise(input);
            exerciseNotFoundText.setText("");
        } catch (ExerciseNotFoundException enfe) {
            exerciseNotFoundText.setText("Error: given exercise not found");
        }
        prHistory.setText(getPRHistoryString(this.userToView));
    }

    //EFFECTS: attempts to parse the user's most recent exercise input.
    // If given exercise does not exist, silently catches ExerciseNotFoundException and
    // does not print any red error text.
    // Otherwise, if exercise does exist, prints PR history text for the given exercise.
    public void refreshPRHistoryTextPrintsNoError(User userToView) {
        try {
            exerciseChosen = this.userToView.getExercise(chooseExerciseTextField.getInput());
        } catch (ExerciseNotFoundException enfe) {
            //do nothing
        }
        prHistory.setText(getPRHistoryString(userToView));
    }

    //EFFECTS: Returns a String of the user's PR history for the exercise in this.exerciseChosen.
    // Or, if no exercise has been chosen yet (i.e. this.exerciseChosen == null), then prompts the user to state an
    // exercise in the JTextField above.
    private String getPRHistoryString(User userToView) {
        if (this.exerciseChosen == null) {
            return "Start by choosing an exercise in the text field above.";
        }

        String prHistoryString = "";
        TreeMap<LocalDate, ExerciseSet> personalRecordHistory = userToView.listPRs(exerciseChosen);
        prHistoryString += (exerciseChosen.getName() + " PR History:\n\n");

        for (Map.Entry<LocalDate, ExerciseSet> pr : personalRecordHistory.entrySet()) {
            prHistoryString += ("Date: " + pr.getKey() + " \nPR: "
                + User.roundToBarbellWeight(pr.getValue().theoreticalOneRepMax())
                + " " + WorkoutLogAppGUI.UNITS + " 1RM"
                + " (" + pr.getValue().getWeight() + " " + WorkoutLogAppGUI.UNITS + " "
                    + pr.getValue().getReps() + " reps)" + "\n\n");
        }

        return prHistoryString;
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

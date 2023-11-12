package ui.gui.panels;

import model.exceptions.ExerciseNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import model.*;
import ui.gui.WorkoutLogAppGUI;
import ui.gui.exceptions.EmptyInputException;
import ui.gui.lambdacomponents.LambdaButton;
import ui.gui.lambdacomponents.LambdaTextField;

//Represents the right-hand-side JPanel made visible to the user when attempting to add a set to the workout log
// This JPanel appears when the "add set" button on the sidebar is pressed.
// Has a userToModify field which extracts user data from an associated WorkoutLogAppGUI object.
public class AddSetPanel extends JPanel {
    static final int LIST_OF_EXERCISES_TEXT_BOX_WIDTH = 100;
    static final int LIST_OF_EXERCISES_TEXT_BOX_HEIGHT = 100;

    GridBagConstraints gbc;

    JTextPane listOfExercises;
    JScrollPane scrollPane;

    LambdaTextField chooseExerciseTextField;
    JLabel chooseExerciseLabel;
    JLabel chooseFromBelowText;

    JLabel chooseWeightLabel;
    LambdaTextField chooseWeightTextField;

    JLabel chooseRepsLabel;
    LambdaTextField chooseRepsTextField;

    JLabel chooseDateLabel;
    LambdaTextField chooseDateTextField;

    JButton addSetButton2;

    Exercise exerciseChosen;
    int weightChosen;
    int repsChosen;
    LocalDate dateSelected;

    JLabel outputPrompt;

    User userToModify;

    //EFFECTS: constructs a new AddSetPanel (with appropriate size and layout) and instantiates and draws all
    // necessary components
    public AddSetPanel(User userToModify) {
        super();
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH,
                WorkoutLogAppGUI.getContentPaneHeight());
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        this.userToModify = userToModify;

        initChooseExerciseComponents();
        chooseWeightLabel = new JLabel("Weight (in " + WorkoutLogAppGUI.UNITS + ")");
        chooseWeightTextField = new LambdaTextField((e, input) -> {
            //do nothing
        });
        chooseRepsLabel = new JLabel("Reps");
        chooseRepsTextField = new LambdaTextField((e, input) -> {
            //do nothing
        });
        chooseDateLabel = new JLabel("Date (mm-dd-yyyy)");
        chooseDateTextField = new LambdaTextField((e, input) -> {
            //chooseDateLabel.setText(input);
            //do nothing
        });
        addSetButton2 = new LambdaButton("Add set", e -> {
            attemptToAddSet();
        });
        outputPrompt = new JLabel("");

        drawAllComponents();
    }

    //MODIFIES: this
    //EFFECTS: draws all necessary components onto this JPanel
    private void drawAllComponents() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        addComponentsInLineVertically(new Component[]{chooseExerciseLabel,
                chooseFromBelowText, chooseExerciseTextField, scrollPane});

        horizontalIndent(1);
        gbc.gridx++;

        gbc.gridy++;
        addComponentsInLineVertically(new Component[]{chooseWeightLabel, chooseWeightTextField});

        gbc.gridx = 0;
        verticalIndent(4, 20);

        gbc.gridy++;
        addComponentsInLineVertically(new Component[]{chooseRepsLabel, chooseRepsTextField});

        gbc.gridx++;
        gbc.gridx++;
        addComponentsInLineVertically(new Component[]{chooseDateLabel, chooseDateTextField});

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridy++;
        verticalIndent(gbc.gridy, 45);

        gbc.gridy++;
        add(addSetButton2, gbc);

        gbc.gridy++;
        verticalIndent(gbc.gridy, 10);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 5;
        add(outputPrompt, gbc);

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: instantiates all components related to the user choosing an Exercise for which to add a set
    private void initChooseExerciseComponents() {
        chooseExerciseLabel = new JLabel("Exercise");
        chooseFromBelowText = new JLabel("(Choose from below)");
        chooseExerciseTextField = new LambdaTextField((e, input) -> {
            //do nothing
        });

        listOfExercises = new JTextPane();
        listOfExercises.setEditable(false);
        listOfExercises.setText(this.userToModify.getExerciseNamesString());
        scrollPane = new JScrollPane(listOfExercises);
        scrollPane.setPreferredSize(new Dimension(LIST_OF_EXERCISES_TEXT_BOX_WIDTH, LIST_OF_EXERCISES_TEXT_BOX_HEIGHT));
    }

    private void attemptToAddSet() {
        chooseExerciseTextField.getText();
        chooseWeightTextField.getText();
        chooseRepsTextField.getText();
        chooseDateTextField.getText();

        if (allInputsEmpty()) {
            setRedText("Error: all inputs are empty");
            return;
        }

        if (getNumInputsEmpty() > 1) {
            setRedText("Error: multiple inputs are empty");
            outputPrompt.setForeground(Color.RED);
            outputPrompt.setText("Error: multiple inputs are empty");
            return;
        }

        try {
            processExerciseInput(this.userToModify);
        } catch (EmptyInputException eie) {
            setRedText("Error: exercise input empty");
            return;
        } catch (ExerciseNotFoundException enfe) {
            setRedText("Error: given exercise not found");
            return;
        }

        try {
            processWeightInput(this.userToModify);
        } catch (EmptyInputException eie) {
            setRedText("Error: weight input empty");
            return;
        } catch (NumberFormatException nfe) {
            setRedText("Error: invalid weight input");
            return;
        }

        try {
            processRepsInput(this.userToModify);
        } catch (EmptyInputException eie) {
            setRedText("Error: reps input empty");
            return;
        } catch (NumberFormatException nfe) {
            setRedText("Error: invalid rep input");
            return;
        }

        try {
            processDateInput(this.userToModify);
        } catch (EmptyInputException eie) {
            setRedText("Error: date input empty");
            return;
        } catch (DateTimeParseException dtpe) {
            setRedText("Error: invalid date input. Recheck formatting!");
            return;
        }

        ExerciseSet setToBeMade = new ExerciseSet(exerciseChosen, weightChosen, repsChosen);
        if (!(this.userToModify.workoutExistsOnDate(dateSelected))) {
            Workout workoutToBeMade = new Workout(dateSelected);
            this.userToModify.addWorkout(workoutToBeMade);
            workoutToBeMade.addSet(setToBeMade);
            exerciseChosen.setNewPRIfNecessary(setToBeMade);
            outputPrompt.setForeground(Color.BLACK);
            outputPrompt.setText("Made new workout and added set. Done at "
                    + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        } else {
            this.userToModify.getWorkoutOnDate(dateSelected).addSet(setToBeMade);
            exerciseChosen.setNewPRIfNecessary(setToBeMade);
            outputPrompt.setForeground(Color.BLACK);
            outputPrompt.setText("Added set to existing workout. Done at "
                    + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        }
    }

    //MODIFIES: this
    //EFFECTS: sets this.outputPrompt to display the given prompt in red text
    private void setRedText(String prompt) {
        outputPrompt.setForeground(Color.RED);
        outputPrompt.setText(prompt);
    }

    //EFFECTS: returns true if all four JTextFields have no user inputs in them
    private boolean allInputsEmpty() {
        return (getNumInputsEmpty() == 4);
    }

    //MODIFIES: this
    //EFFECTS: draws all given components in a downwards vertical line.
    // gbc.gridy is preserved in the start and the end of the method call.
    private void addComponentsInLineVertically(Component[] components) {
        int originalGridY = gbc.gridy;
        for (Component component : components) {
            this.add(component, gbc);
            gbc.gridy++;
        }
        gbc.gridy = originalGridY;
    }

    //MODIFIES: this
    //EFFECTS: draws a horizontal indent of width 2 at the given x value.
    // Also sets gbc.gridx to the given x value.
    private void horizontalIndent(int x) {
        gbc.gridx = x;
        add(Box.createHorizontalStrut(2), gbc);
    }

    //MODIFIES: this
    //EFFECTS: draws a vertical indent of given height at the given y value.
    // Also sets gbc.gridy to the given y value.
    private void verticalIndent(int y, int height) {
        gbc.gridy = y;
        add(Box.createVerticalStrut(height), gbc);
    }

    //MODIFIES: this
    //EFFECTS: processes the exercise input in this.chooseExerciseTextField.
    // If exercise input is empty, throws EmptyInputException
    // Otherwise, attempts to set exerciseChosen to the existing user's Exercise matching the given input,
    // and throws ExerciseNotFoundException if necessary
    private void processExerciseInput(User userToModify) throws EmptyInputException, ExerciseNotFoundException {
        if (chooseExerciseTextField.getText().equals("")) {
            throw new EmptyInputException();
        }
        exerciseChosen = userToModify.getExercise(chooseExerciseTextField.getText());
    }

    //MODIFIES: this
    //EFFECTS: processes the weight input in this.chooseWeightTextField.
    // If weight input is empty, throws EmptyInputException
    // Otherwise, attempts to set weightChosen to the given weight input, and throws NumberFormatException if the
    // input cannot be converted to an int
    private void processWeightInput(User userToModify) throws EmptyInputException, NumberFormatException {
        if (chooseWeightTextField.getText().equals("")) {
            throw new EmptyInputException();
        }
        weightChosen = Integer.parseInt(chooseWeightTextField.getText());
    }

    //MODIFIES: this
    //EFFECTS: processes the reps input in this.chooseRepsTextField.
    // If reps input is empty, throws EmptyInputException
    // Otherwise, attempts to set repsChosen to the given reps input, and throws NumberFormatException if the
    // input cannot be converted to an int
    private void processRepsInput(User userToModify) throws EmptyInputException, NumberFormatException {
        if (chooseRepsTextField.getText().equals("")) {
            throw new EmptyInputException();
        }
        repsChosen = Integer.parseInt(chooseRepsTextField.getText());

    }

    //MODIFIES: this
    //EFFECTS: processes the date input in this.chooseDateTextField.
    // If date input is empty, throws EmptyInputException
    // Otherwise, attempts to set dateSelected to the given date input, and throws DateTimeParseException if the
    // input cannot be properly converted to a LocalDate.
    private void processDateInput(User userToModify) throws EmptyInputException, DateTimeParseException {
        if (chooseDateTextField.getText().equals("")) {
            throw new EmptyInputException();
        }
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        dateSelected = LocalDate.from(LocalDate.parse(chooseDateTextField.getText(), dateTimeFormat));
    }

    //EFFECTS: returns the number of inputs that are empty.
    private int getNumInputsEmpty() {
        int numInputsEmpty = 0;

        if (chooseExerciseTextField.getText().equals("")) {
            numInputsEmpty++;
        }
        if (chooseWeightTextField.getText().equals("")) {
            numInputsEmpty++;
        }
        if (chooseRepsTextField.getText().equals("")) {
            numInputsEmpty++;
        }
        if (chooseDateTextField.getText().equals("")) {
            numInputsEmpty++;
        }
        return numInputsEmpty;
    }

    //REQUIRES: must be called whenever the user field in the associated WorkoutLogAppGUI object is re-instantiated.
    //MODIFIES: this
    //EFFECTS: updates this.userToView to point to the given updatedUser.
    // Helps ensure that this.userToView and the user field in the associated WorkoutLogAppGUI
    // object point to the same memory location.
    public void updateUserFieldPointer(User updatedUser) {
        this.userToModify = updatedUser;
    }
}

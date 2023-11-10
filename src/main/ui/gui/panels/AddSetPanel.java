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
import ui.gui.lambda_components.LambdaButton;
import ui.gui.lambda_components.LambdaTextField;

public class AddSetPanel extends JPanel {
    JScrollPane scrollPane;
    static final int LIST_OF_EXERCISES_TEXT_BOX_WIDTH = 100;
    static final int LIST_OF_EXERCISES_TEXT_BOX_HEIGHT = 100;
    JTextPane listOfExercises;

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

    GridBagConstraints gbc;

    Exercise exerciseChosen;
    int weightChosen;
    int repsChosen;
    LocalDate dateSelected;

    JLabel outputPrompt;

    User userToModify;

    public AddSetPanel(User userToModify) {
        super();
        setSize(WorkoutLogAppGUI.getContentPaneWidth() - WorkoutLogAppGUI.SIDE_MENU_WIDTH, WorkoutLogAppGUI.getContentPaneHeight()); //must reduce coupling here
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        //setBackground(Color.red);
        this.userToModify = userToModify;

        chooseExerciseLabel = new JLabel("Exercise");
        chooseFromBelowText = new JLabel("(Choose from below)");
        chooseExerciseTextField = new LambdaTextField((e, input) -> {
            //do nothing
        });

        listOfExercises = new JTextPane();
        listOfExercises.setEditable(false);
        listOfExercises.setText(getExerciseNamesString());
        scrollPane = new JScrollPane(listOfExercises);
        scrollPane.setPreferredSize(new Dimension(LIST_OF_EXERCISES_TEXT_BOX_WIDTH, LIST_OF_EXERCISES_TEXT_BOX_HEIGHT));

        chooseWeightLabel = new JLabel("Weight (in lbs)");
        chooseWeightTextField = new LambdaTextField((e, input) -> {
            //do nothing
            //chooseWeightLabel.setText(input);
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
            chooseExerciseTextField.getText();
            chooseWeightTextField.getText();
            chooseRepsTextField.getText();
            chooseDateTextField.getText();

            if (allInputsEmpty()) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: all inputs are empty");
                return;
            }

            if (getNumInputsEmpty() > 1) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: multiple inputs are empty");
                return;
            }

            try {
                processExerciseInput(this.userToModify);
            } catch (EmptyInputException eie) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: exercise input empty");
                return;
            } catch (ExerciseNotFoundException enfe) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: given exercise not found");
                return;
            }

            try {
                processWeightInput(this.userToModify);
            } catch (EmptyInputException eie) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: weight input empty");
                return;
            } catch (NumberFormatException nfe) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: invalid weight input");
                return;
            }

            try {
                processRepsInput(this.userToModify);
            } catch (EmptyInputException eie) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: reps input empty");
                return;
            } catch (NumberFormatException nfe) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: invalid rep input");
                return;
            }

            try {
                processDateInput(this.userToModify);
            } catch (EmptyInputException eie) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: date input empty");
                return;
            } catch (DateTimeParseException dtpe) {
                outputPrompt.setForeground(Color.RED);
                outputPrompt.setText("Error: invalid date input. Recheck formatting!");
                return;
            }

            ExerciseSet setToBeMade = new ExerciseSet(exerciseChosen, weightChosen, repsChosen);
            if (!(this.userToModify.workoutExistsOnDate(dateSelected))) {
                Workout workoutToBeMade = new Workout(dateSelected);
                this.userToModify.addWorkout(workoutToBeMade);
                workoutToBeMade.addSet(setToBeMade);
                exerciseChosen.setNewPRIfNecessary(setToBeMade);
                outputPrompt.setForeground(Color.BLACK);
                outputPrompt.setText("Made new workout and added set. Done at " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
            } else {
                this.userToModify.getWorkoutOnDate(dateSelected).addSet(setToBeMade);
                exerciseChosen.setNewPRIfNecessary(setToBeMade);
                outputPrompt.setForeground(Color.BLACK);
                outputPrompt.setText("Added set to existing workout. Done at " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
            }
        });

        outputPrompt = new JLabel("");

        gbc.gridx = 0;
        gbc.gridy = 0;

        addComponentsInLineVertically(new Component[]{chooseExerciseLabel, chooseFromBelowText, chooseExerciseTextField, scrollPane});

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

    private boolean allInputsEmpty() {
        return (getNumInputsEmpty() == 4);
    }

    //!!!
    public void addComponentsInLineVertically(Component[] components) {
        int originalGridY = gbc.gridy;
        for (Component component : components) {
            this.add(component, gbc);
            gbc.gridy++;
        }
        gbc.gridy = originalGridY;
    }

    //!!!
    public void horizontalIndent(int x) {
        gbc.gridx = x;
        add(Box.createHorizontalStrut(2), gbc);
    }

    //!!!
    public void verticalIndent(int y, int height) {
        gbc.gridy = y;
        add(Box.createVerticalStrut(height), gbc);
    }

    //!!!
    public void processExerciseInput(User userToModify) throws EmptyInputException, ExerciseNotFoundException {
        if (chooseExerciseTextField.getText().equals("")) {
            throw new EmptyInputException();
        }
        exerciseChosen = userToModify.getExercise(chooseExerciseTextField.getText());
    }

    public void processWeightInput(User userToModify) throws EmptyInputException, NumberFormatException {
        if (chooseWeightTextField.getText().equals("")) {
            throw new EmptyInputException();
        }
        weightChosen = Integer.parseInt(chooseWeightTextField.getText());
    }

    public void processRepsInput(User userToModify) throws EmptyInputException, NumberFormatException {
        if (chooseRepsTextField.getText().equals("")) {
            throw new EmptyInputException();
        }
        repsChosen = Integer.parseInt(chooseRepsTextField.getText());

    }

    public void processDateInput(User userToModify) throws EmptyInputException, DateTimeParseException {
        if (chooseDateTextField.getText().equals("")) {
            throw new EmptyInputException();
        }
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        dateSelected = LocalDate.from(LocalDate.parse(chooseDateTextField.getText(), dateTimeFormat));
    }

    public int getNumInputsEmpty() {
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

    //REQUIRES: Must be used in conjunction with !!!
    public void updateUserFieldPointer(User updatedUser) {
        this.userToModify = updatedUser;
    }

    public String getExerciseNamesString() {
        String listOfNames = "";
        for (Exercise exercise : this.userToModify.getExercises()) {
            listOfNames += exercise.getName();
            listOfNames += "\n";
        }
        listOfNames = listOfNames.trim();
        return listOfNames;
    }
}

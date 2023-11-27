package ui.gui.panels;

import model.*;
import ui.gui.WorkoutLogAppGUI;
import ui.gui.lambdacomponents.LambdaTextField;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

//Represents the right-hand-side JPanel made visible to the user when attempting to view relative strengths for each
// exercise. This panel appears when the "relative strength" button is pressed.
public class RelativeStrengthPanel extends JPanel {
    static final int RELATIVE_STRENGTH_TEXT_BOX_WIDTH = 300;
    static final int RELATIVE_STRENGTH_TEXT_BOX_HEIGHT = 300;

    GridBagConstraints gbc;

    JTextPane relativeStrength;
    JScrollPane scrollPane;

    JLabel bodyWeightPrompt;
    LambdaTextField bodyWeightTextField;
    JLabel errorText;

    User userToView;

    final DecimalFormat decimalFormat = new DecimalFormat("#.###");

    //EFFECTS: constructs a new RelativeStrengthPanel (with appropriate size and layout) and defines and draws all
    // necessary components
    public RelativeStrengthPanel(User userToView) {
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
    private void drawComponents() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(bodyWeightPrompt, gbc);
        gbc.gridx++;
        add(bodyWeightTextField, gbc);
        gbc.gridy++;
        add(errorText, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);
    }

    //MODIFIES: this
    //EFFECTS: initializes all components which will later be drawn onto this JPanel
    private void initComponents() {
        relativeStrength = new JTextPane();
        relativeStrength.setEditable(false);
        relativeStrength.setText(getRelativeStrengthString(this.userToView));
        scrollPane = new JScrollPane(relativeStrength);
        scrollPane.setPreferredSize(new Dimension(RELATIVE_STRENGTH_TEXT_BOX_WIDTH, RELATIVE_STRENGTH_TEXT_BOX_HEIGHT));
        bodyWeightPrompt = new JLabel("My bodyweight: ");
        errorText = new JLabel("");
        errorText.setForeground(Color.RED);
        bodyWeightTextField = new LambdaTextField((e, input) -> {
            try {
                if (Integer.parseInt(input) <= 0) {
                    errorText.setText("Error: bodyweight must be a positive integer");
                    return;
                }
                this.userToView.setBodyWeight(Integer.parseInt(input));
                errorText.setText("");
                relativeStrength.setText(getRelativeStrengthString(this.userToView));
            } catch (NumberFormatException nfe) {
                errorText.setText("Error: invalid");
            }
        });
    }

    //EFFECTS: Returns a String of the user's relative strengths for each exercise,
    // based on this.userToView.getBodyWeight() rather than current input. If bodyweight has not yet been set
    // (default bodyweight value is 0 before it is set), returns a String prompting the user to set a bodyweight.
    private String getRelativeStrengthString(User userToView) {
        String relativeStrengthString = "";

        if (this.userToView.getBodyWeight() == 0) {
            return "To start, please enter bodyweight above. (whole number please)";
        }

        relativeStrengthString += ("Viewing relative strength for each exercise:\n\n");
        for (Exercise exercise : userToView.getExercises()) {
            if (exercise.isStrong(userToView.getBodyWeight())) {
                relativeStrengthString += ((exercise.getName()) + ": "
                        + decimalFormat.format(exercise.oneRepMaxInPercentBodyWeight(userToView.getBodyWeight()))
                        + "x bodyweight"
                        + " STRONG!\n");

            } else {
                relativeStrengthString += ((exercise.getName()) + ": "
                        + decimalFormat.format(exercise.oneRepMaxInPercentBodyWeight(userToView.getBodyWeight()))
                        + "x bodyweight\n");
            }
        }
        relativeStrengthString += ("\n");

        return relativeStrengthString;
    }

    //MODIFIES: this
    //EFFECTS: changes this.bodyWeightTextField to show the pre-set bodyweight value (or "" if bodyweight has not been
    // set yet).
    // Then, updates the relativeStrength textPane to show relative strength stats based on the
    // previously-set bodyweight value. (If bodyweight == 0 then prompts the user to set a bodyweight).
    // Removes any error text previously shown.
    // Once this method is called, the previous input which was on the bodyWeightTextField is discarded.
    public void refreshRelativeStrengthTextAndBodyWeightTextField(User userToView) {
        if (this.userToView.getBodyWeight() != 0) {
            bodyWeightTextField.setText(Integer.toString(this.userToView.getBodyWeight()));
        } else if (this.userToView.getBodyWeight() == 0) {
            bodyWeightTextField.setText("");
        }
        errorText.setText("");
        relativeStrength.setText(getRelativeStrengthString(this.userToView));
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

package ui.gui;

import persistence.JsonReader;
import persistence.JsonWriter;
import ui.gui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.*;

//Represents the WorkoutLogApp JFrame window which displays all components and holds User data
public class WorkoutLogAppGUI extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int SIDE_MENU_WIDTH = 175;
    private static final String JSON_STORE = "./data/user.json";
    public static final String UNITS = "lbs";
    Sidebar sidebar;
    final JPanel[] panels = new JPanel[7];
    static Container contentPane;

    User user;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: creates WorkoutLogApp window with appropriate size, initializes user data,
    // plays the splash screen animation,
    // and then finally paints the side menu and panels
    public WorkoutLogAppGUI() {
        super("WorkoutLogApp");
        contentPane = this.getContentPane();
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        initData();
        initSplashScreen();
        initSideMenu();
        initPanels();

        revalidate();
        repaint();
    }

    //MODIFIES: this
    //EFFECTS: initializes all model and persistence data necessary to run the application.
    // This includes a blank user with no bodyweight, no workouts added, and bench press, deadlift, and squats added
    // as the user's default three exercises.
    // jsonWriter and jsonReader are also instantiated to allow for data persistence.
    public void initData() {
        user = new User();
        Exercise benchPress = new Exercise("Bench Press", 1.0);
        Exercise deadLift = new Exercise("Dead Lift", 2.0);
        Exercise squat = new Exercise("Squat", 1.75);
        user.addExercise(benchPress);
        user.addExercise(deadLift);
        user.addExercise(squat);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //EFFECTS: plays the splash screen animation (lasts 3 seconds).
    // The app logo appears along with loading text below it.
    // The loading text plays for 3 seconds before disappearing and ending the entire animation.
    // Once the animation is complete, JFrame layout is set to null.
    public void initSplashScreen() {
        ui.gui.SplashScreen splashScreen = new SplashScreen();
        add(splashScreen, BorderLayout.CENTER);
        setLayout(new BorderLayout(10, 5));
        setVisible(true);
        splashScreen.playLoadingText();
        splashScreen.setVisible(false);
        setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: initializes a new sidebar and draws it onto the JFrame window.
    public void initSideMenu() {
        sidebar = new Sidebar(this);
        sidebar.setLocation(0,0);
        add(sidebar);
        sidebar.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: draws all right-hand-side panels onto the JFrame, but sets them all invisible
    public void initPanels() {
        panels[0] = new AddSetPanel(this.user);
        panels[1] = new ViewHistoryPanel(this.user);
        panels[2] = new SavedDataPanel();
        panels[3] = new LoadedDataPanel();
        panels[4] = new View1RMsPanel(this.user);
        panels[5] = new PRHistoryPanel(this.user);
        panels[6] = new RelativeStrengthPanel(this.user);
        for (JPanel panel : panels) {
            panel.setLocation(SIDE_MENU_WIDTH,0);
            add(panel);
            panel.setVisible(false);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets a given right-hand-side panel visible.
    // More specifically: receives an int index, and sets panel[index] to visible.
    public void setPanelVisible(int index) {
        panels[index].setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: sets all right-hand-side panels invisible.
    public void hideAllPanels() {
        for (JPanel panel : panels) {
            panel.setVisible(false);
        }
    }

    //EFFECTS: returns the height of the JFrame content pane.
    public static int getContentPaneHeight() {
        return contentPane.getHeight();
    }

    //EFFECTS: returns the width of the JFrame content pane.
    public static int getContentPaneWidth() {
        return contentPane.getWidth();
    }

    //EFFECTS: returns this.user
    public User getUser() {
        return this.user;
    }

    //REQUIRES: panels[1] has actual type ViewHistoryPanel
    //MODIFIES: this
    //EFFECTS: refreshes the view history panel, or panels[1], so that it shows the most recent workout history
    public void refreshViewHistoryPanel() {
        ((ViewHistoryPanel) panels[1]).refreshWorkoutHistoryText(this.user);
    }

    //REQUIRES: panels[2] has actual type SavedDataPanel
    //MODIFIES: this
    //EFFECTS: refreshes the saved data panel, or panels[2],
    // so that it shows the most recent time at which data was saved.
    public void refreshSavedDataPanel() {
        ((SavedDataPanel) panels[2]).refreshSavedAtTimeText();
    }

    //REQUIRES: panels[3] has actual type LoadedDataPanel
    //MODIFIES: this
    //EFFECTS: refreshes the loaded data panel, or panels[3],
    // so that it shows the most recent time at which data has been loaded.
    public void refreshLoadedDataPanel() {
        ((LoadedDataPanel) panels[3]).refreshLoadedAtDateText();
    }

    //REQUIRES: panels[4] has actual type View1RMsPanel
    //MODIFIES: this
    //EFFECTS: refreshes the 1RM panel, or panels[4], so that it shows the most recent one-rep-maxes for each exercise.
    public void refreshView1RMsPanel() {
        ((View1RMsPanel) panels[4]).refresh1RMsText(this.user);
    }

    //REQUIRES: panels[5] has actual type PRHistoryPanel
    //MODIFIES: this
    //EFFECTS: refreshes the PR history panel, or panels[5], so that it shows the most recent PR history
    // for the exercise specified by the user.
    public void refreshPRHistoryPanel() {
        ((PRHistoryPanel) panels[5]).refreshPRHistoryTextPrintsNoError(this.user);
    }

    //REQUIRES: panels[6] has actual type RelativeStrengthPanel
    //MODIFIES: this
    //EFFECTS: refreshes the relative strength panel, or panels[6], so that it shows the most recent relative strengths
    // for each exercise.
    public void refreshRelativeStrengthPanel() {
        ((RelativeStrengthPanel) panels[6]).refreshRelativeStrengthTextAndBodyWeightTextField(this.user);
    }

    //Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    //REQUIRES: panels[2] has actual type SavedDataPanel
    //EFFECTS: saves all User data to file
    public void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.user);
            jsonWriter.close();
            ((SavedDataPanel) panels[2]).setSavedSuccessfullyText(JSON_STORE);
        } catch (FileNotFoundException e) {
            ((SavedDataPanel) panels[2]).warnUnableToSave(JSON_STORE);
        }
    }

    //Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // REQUIRES: panels[0] has actual type AddSetPanel, panels[1] has actual type ViewHistoryPanel
    // MODIFIES: this
    // EFFECTS: loads User from file
    public void loadUser() {
        try {
            //User updatedUser = jsonReader.read();
            this.user = jsonReader.read();
            //this.user.updateAllFieldsAccordingTo(updatedUser);
            ((AddSetPanel) panels[0]).updateUserFieldPointer(this.user);
            ((ViewHistoryPanel) panels[1]).updateUserFieldPointer(this.user);
            ((View1RMsPanel) panels[4]).updateUserFieldPointer(this.user);
            ((PRHistoryPanel) panels[5]).updateUserFieldPointer(this.user);
            ((RelativeStrengthPanel) panels[6]).updateUserFieldPointer(this.user);
            ((LoadedDataPanel) panels[3]).setLoadedSuccessfullyText(JSON_STORE);
        } catch (IOException e) {
            ((LoadedDataPanel) panels[3]).warnUnableToLoad(JSON_STORE);
        }
    }
}

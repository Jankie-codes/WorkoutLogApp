package ui.gui;

import persistence.JsonReader;
import persistence.JsonWriter;
import ui.gui.panels.AddSetPanel;
import ui.gui.panels.LoadedDataPanel;
import ui.gui.panels.SavedDataPanel;
import ui.gui.panels.ViewHistoryPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.*;

public class WorkoutLogAppGUI extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int SIDE_MENU_WIDTH = 175;
    private static final String JSON_STORE = "./data/user.json";
    final JPanel[] panels = new JPanel[4];
    static Container contentPane;

    User user;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public WorkoutLogAppGUI() {
        super("WorkoutLogApp");
        user = new User();
        Exercise benchPress = new Exercise("Bench Press", 1.0);
        Exercise deadLift = new Exercise("Dead Lift", 2.0);
        Exercise squat = new Exercise("Squat", 1.75);
        user.addExercise(benchPress);
        user.addExercise(deadLift);
        user.addExercise(squat);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        contentPane = this.getContentPane();
        setSize(WIDTH, HEIGHT);
        ui.gui.SplashScreen splashScreen = new SplashScreen();
        add(splashScreen, BorderLayout.CENTER);
        setLayout(new BorderLayout(10, 5));
        setVisible(true);
        splashScreen.playLoadingText();
        splashScreen.setVisible(false);
        setLayout(null);

        SideMenu sideMenu = new SideMenu(this);
        sideMenu.setLocation(0,0);
        add(sideMenu);
        sideMenu.setVisible(true);
        panels[0] = new AddSetPanel(this.user);
        panels[1] = new ViewHistoryPanel(this.user);
        panels[2] = new SavedDataPanel();
        panels[3] = new LoadedDataPanel();
        for (JPanel panel : panels) {
            panel.setLocation(SIDE_MENU_WIDTH,0);
            add(panel);
            panel.setVisible(false);
        }
        revalidate();
        repaint();
    }

    //!!!
    public void setPanelVisible(int index) {
        panels[index].setVisible(true);
    }

    //!!!
    public void hideAllPanels() {
        for (JPanel panel : panels) {
            panel.setVisible(false);
        }
    }

    //!!!
    public static int getContentPaneHeight() {
        return contentPane.getHeight();
    }

    //!!!
    public static int getContentPaneWidth() {
        return contentPane.getWidth();
    }

    public User getUser() {
        return this.user;
    }

    //REQUIRES: panels[1] has actual type ViewHistoryPanel
    //!!!
    public void refreshViewHistoryPanel() {
        ((ViewHistoryPanel) panels[1]).refreshWorkoutHistoryText(this.user);
    }

    //REQUIRES: panels[2] has actual type SavedDataPanel
    //!!!
    public void refreshSavedDataPanel() {
        ((SavedDataPanel) panels[2]).refreshSavedAtDateText();
    }

    //Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    //REQUIRES: panels[2] has actual type SavedDataPanel
    // EFFECTS: saves all User data to file
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
            ((LoadedDataPanel) panels[3]).setLoadedSuccessfullyText(JSON_STORE);
        } catch (IOException e) {
            ((LoadedDataPanel) panels[3]).warnUnableToLoad(JSON_STORE);
        }
    }

    //REQUIRES: panels[3] has actual type LoadedDataPanel
    //!!!
    public void refreshLoadedDataPanel() {
        ((LoadedDataPanel) panels[3]).refreshLoadedAtDateText();
    }

}

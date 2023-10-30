package ui;

import javax.swing.*;

public class WorkoutLogAppGUI extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public WorkoutLogAppGUI() {
        super("WorkoutLogApp");
        setSize(WIDTH, HEIGHT);
        SplashScreen splashScreen = new SplashScreen();
        add(splashScreen);
        setVisible(true);
    }
}

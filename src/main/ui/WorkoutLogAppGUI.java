package ui;

import ui.panels.*;

import javax.swing.*;
import java.awt.*;

public class WorkoutLogAppGUI extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int SIDE_MENU_WIDTH = 175;
    JLabel testJLabelWhenButtonPressed; //make arrayList of panels
    JPanel[] panels = new JPanel[4];
    static Container contentPane;

    public WorkoutLogAppGUI() {
        super("WorkoutLogApp");
        contentPane = this.getContentPane();
        setSize(WIDTH, HEIGHT);
        SplashScreen splashScreen = new SplashScreen();
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
        //testJLabelWhenButtonPressed = new JLabel("Button pressed!");
        //add(testJLabelWhenButtonPressed, gbc);
        //testJLabelWhenButtonPressed.setVisible(true);
        panels[0] = new AddSetPanel();
        panels[1] = new ViewHistoryPanel();
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
}

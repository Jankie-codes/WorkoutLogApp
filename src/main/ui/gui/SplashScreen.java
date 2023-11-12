package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

//Represents a splash screen which adds an app logo and displays loading text for three seconds
public class SplashScreen extends JPanel {
    static final int LOGO_WIDTH = 150;
    static final int LOGO_HEIGHT = 150;
    GridBagConstraints gbc;
    JLabel loadingText;

    //EFFECTS: constructs a new SplashScreen (with appropriate size and layout)
    // and draws a logo image, thanks text, and loading text onto it.
    public SplashScreen() {
        super();
        setSize(WorkoutLogAppGUI.WIDTH, WorkoutLogAppGUI.HEIGHT);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        addLogo();
        addThanksText();
        addLoadingText();
        this.setVisible(true);
    }

    //EFFECTS: constructs a new URL based off of the given urlString
    private URL instantiateURL(String urlString) {
        URL resultingURL;
        try {
            resultingURL = new URL(urlString);
            return resultingURL;
        } catch (MalformedURLException mue) {
            return null;
        }
    }

    //MODIFIES: this
    //EFFECTS: scales the logo image, then draws it onto the splashscreen
    private void addLogo() {
        URL logoURL = instantiateURL("https://static.vecteezy.com"
                + "/system/resources/previews/007/121/730/original/"
                + "weightlifting-icon-on-white-vector.jpg");
        ImageIcon logoImageIcon = new ImageIcon(logoURL);
        Image logoScaled = logoImageIcon.getImage().getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT,
                java.awt.Image.SCALE_SMOOTH);
        logoImageIcon = new ImageIcon(logoScaled);
        JLabel logoLabel = new JLabel(logoImageIcon);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(logoLabel, gbc);
        logoLabel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates a vertical strut of height 50 and then draws thank you text below the strut
    private void addThanksText() {
        JLabel thanksText = new JLabel("Thanks for using WorkoutLogApp!");

        gbc.gridy = 1;
        add(Box.createVerticalStrut(50), gbc);
        gbc.gridy = 2;
        add(thanksText, gbc);
        thanksText.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates a vertical strut of height 50 and then draws loading text below the strut
    private void addLoadingText() {
        loadingText = new JLabel("Loading");
        gbc.gridy = 3;
        add(Box.createVerticalStrut(50), gbc);
        gbc.gridy = 4;

        add(loadingText, gbc);
        loadingText.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: animates the loading text.
    // Accomplishes this by adding a "." to the end of the loading text every 500 milliseconds,
    // then changing the loading text to display "Ready!" after 3 periods have been added.
    public void playLoadingText() {
        int timeBetweenAnimations = 500;
        try {
            Thread.sleep(timeBetweenAnimations);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            loadingText.setText(loadingText.getText() + ".");
            try {
                Thread.sleep(timeBetweenAnimations);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        loadingText.setText("Ready!");
        try {
            Thread.sleep(timeBetweenAnimations);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashScreen extends JPanel {
    GridBagConstraints gbc; //manages locations of various text and images on the panel
    JLabel loadingText;

    //adds logo, thank you text, and loading text to JPanel
    public SplashScreen() {
        super();
        setSize(600, 400); //must reduce coupling here
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        addLogo();
        addThanksText();
        addLoadingText();
        this.setVisible(true);
    }

    //!!!
    private URL instantiateURL(String urlString) {
        URL resultingURL;
        try {
            resultingURL = new URL(urlString);
            return resultingURL;
        } catch (MalformedURLException mue) {
            return null;
        }
    }

    //scales logo image, then adds it to panel
    public void addLogo() {
        URL logoURL = instantiateURL("https://static.vecteezy.com"
                + "/system/resources/previews/007/121/730/original/"
                + "weightlifting-icon-on-white-vector.jpg");
        ImageIcon logoImageIcon = new ImageIcon(logoURL);
        Image logoScaled = logoImageIcon.getImage().getScaledInstance(150,150, java.awt.Image.SCALE_SMOOTH);
        logoImageIcon = new ImageIcon(logoScaled);
        JLabel logoLabel = new JLabel(logoImageIcon); //might throw nullPointerException

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(logoLabel, gbc);
        logoLabel.setVisible(true);
    }

    public void addThanksText() {
        JLabel thanksText = new JLabel("Thanks for using WorkoutLogApp!");
        gbc.gridy = 1;
        add(Box.createVerticalStrut(50), gbc);
        gbc.gridy = 2;
        add(thanksText, gbc);
        thanksText.setVisible(true);
    }

    public void addLoadingText() {
        loadingText = new JLabel("Loading");
        gbc.gridy = 3;
        add(Box.createVerticalStrut(50), gbc);
        gbc.gridy = 4;

        add(loadingText, gbc);
        loadingText.setVisible(true);
    }

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

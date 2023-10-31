package ui;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashScreen extends JPanel {
    public SplashScreen() {
        super();
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        URL logoURL = instantiateURL("https://static.vecteezy.com"
                                            + "/system/resources/previews/007/121/730/original/"
                                            + "weightlifting-icon-on-white-vector.jpg");
        ImageIcon logoImageIcon = new ImageIcon(logoURL);
        Image logoScaled = logoImageIcon.getImage().getScaledInstance(150,150, java.awt.Image.SCALE_SMOOTH);
        logoImageIcon = new ImageIcon(logoScaled);
        JLabel logoLabel = new JLabel(logoImageIcon); //might throw nullPointerException
        JLabel thanksText = new JLabel("Thanks for using WorkoutLogApp!");

        //logoLabel.setBounds(10, 50, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(logoLabel, gbc);
        gbc.gridy = 1;
        add(thanksText, gbc);

        logoLabel.setVisible(true);
        thanksText.setVisible(true);
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
}

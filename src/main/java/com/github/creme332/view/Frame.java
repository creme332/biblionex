package com.github.creme332.view;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import com.github.creme332.controller.ScreenName;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.patron.Registration;

/**
 * Frame of the GUI application.
 */
public class Frame extends JFrame {
    // frame properties
    private int frameWidth = 1600;
    private int frameHeight = 1000;

    // variables for managing different screens
    private CardLayout cardLayout = new CardLayout(); // used to swap between screens
    private JPanel cardPanels = new JPanel(cardLayout); // a container for all screens

    // a map that maps a screen name to screen
    private Map<ScreenName, JPanel> screenMapper = new HashMap<ScreenName, JPanel>();

    public Frame() throws InvalidPathException {
        // set frame title
        this.setTitle("biblionex");

        // set frame size
        this.setSize(frameWidth, frameHeight);

        // make frame resizable
        this.setResizable(true);

        // add close button to frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set application icon
        this.setIconImage(new IconLoader().loadIcon("/icons/stack-of-books.png").getImage());

        // center frame on startup if frame is not maximized
        if (this.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            this.setLocationRelativeTo(null);
        }

        // setup screen mapper and create screens
        screenMapper.put(ScreenName.SPLASH_SCREEN, new SplashScreen());
        screenMapper.put(ScreenName.LOGIN_SCREEN, new Login());
        screenMapper.put(ScreenName.PATRON_REGISTRATION_SCREEN, new Registration());
        screenMapper.put(ScreenName.PATRON_DASHBOARD_SCREEN, new com.github.creme332.view.patron.Dashboard());
        screenMapper.put(ScreenName.LIBRARIAN_DASHBOARD_SCREEN, new com.github.creme332.view.librarian.Dashboard());
        // to add new screens to frame, add a new line here...

        // add screens to cardPanels
        for (ScreenName screenName : screenMapper.keySet()) {
            cardPanels.add(screenMapper.get(screenName), screenName.getScreenName());
        }

        // add cardPanels to frame
        this.add(cardPanels);

        // display frame
        this.setVisible(true);
    }

    public JPanel getPage(ScreenName name) {
        JPanel screen = screenMapper.get(name);
        if (screen == null) {
            System.out.println("Invalid screen: " + name.getScreenName());
            System.exit(0);
        }
        return screen;
    }

    public void switchToScreen(ScreenName screenName) {
        cardLayout.show(cardPanels, screenName.getScreenName());
    }
}
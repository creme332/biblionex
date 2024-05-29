package com.github.creme332.view;

import java.awt.*;
import javax.swing.*;

import com.github.creme332.controller.ScreenNames;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;

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

    // pages
    private SplashScreen splashScreen = new SplashScreen();
    private Login loginPage = new Login();
    private Registration registrationPage = new Registration();

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

        // setup screen container
        cardPanels.add(splashScreen, ScreenNames.SPLASH_SCREEN.getScreenName());
        cardPanels.add(loginPage, ScreenNames.LOGIN_SCREEN.getScreenName());
        cardPanels.add(registrationPage, ScreenNames.PATRON_REGISTRATION_SCREEN.getScreenName());

        this.add(cardPanels);

        // display frame
        this.setVisible(true);
    }

    public JPanel getPage(ScreenNames name) {
        switch (name) {
            case LOGIN_SCREEN:
                return loginPage;
            case SPLASH_SCREEN:
                return splashScreen;
            case PATRON_REGISTRATION_SCREEN:
                return registrationPage;
            default:
                break;
        }
        return registrationPage;
    }

    /**
     * Displays frame which is initially hidden.
     * 
     * Call this function once all components have been added to the frame
     * to ensure proper rendering.
     */
    public void showFrame() {
        this.setVisible(true);
    }

    public void switchToScreen(ScreenNames screenName) {
        cardLayout.show(cardPanels, screenName.getScreenName());
    }
}
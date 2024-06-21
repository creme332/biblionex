package com.github.creme332.view;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.*;

import com.github.creme332.controller.Screen;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.librarian.MaterialForm;
import com.github.creme332.view.librarian.RegistrationForm;
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
    private Map<Screen, JPanel> screenMapper = new EnumMap<>(Screen.class);

    public Frame() throws InvalidPathException {
        // set frame title
        this.setTitle("biblionex");

        // set frame size
        this.setSize(frameWidth, frameHeight);

        // make frame resizable
        this.setResizable(true);

        // add close button to frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set application icon
        this.setIconImage(new IconLoader().loadIcon("/icons/stack-of-books.png").getImage());

        // center frame on startup if frame is not maximized
        if (this.getExtendedState() != MAXIMIZED_BOTH) {
            this.setLocationRelativeTo(null);
        }

        // setup screen mapper and create screens
        screenMapper.put(Screen.SPLASH_SCREEN, new SplashScreen());
        screenMapper.put(Screen.LOGIN_SCREEN, new Login());
        screenMapper.put(Screen.PATRON_REGISTRATION_SCREEN, new Registration());
        screenMapper.put(Screen.PATRON_DASHBOARD_SCREEN, new com.github.creme332.view.patron.Dashboard());
        screenMapper.put(Screen.LIBRARIAN_DASHBOARD_SCREEN, new com.github.creme332.view.librarian.Dashboard());
        screenMapper.put(Screen.LIBRARIAN_REGISTRATION_SCREEN, new RegistrationForm());
        screenMapper.put(Screen.LIBRARIAN_MATERIAL_SCREEN, new MaterialForm());

        // to add new screens to frame, add a new line here...

        // add screens to cardPanels
        for (Map.Entry<Screen, JPanel> entry : screenMapper.entrySet()) {
            cardPanels.add(entry.getValue(), entry.getKey().getScreenName());
        }

        // add cardPanels to frame
        this.add(cardPanels);

        this.pack();

        // display frame
        this.setVisible(true);
    }

    public JPanel getPage(Screen name) {
        JPanel screen = screenMapper.get(name);
        if (screen == null) {
            System.out.println("Invalid screen: " + name.getScreenName());
            System.exit(0);
        }
        return screen;
    }

    public void switchToScreen(Screen screenName) {
        cardLayout.show(cardPanels, screenName.getScreenName());
    }
}
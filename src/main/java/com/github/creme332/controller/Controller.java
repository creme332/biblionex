package com.github.creme332.controller;

import java.util.Timer;
import java.util.TimerTask;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import com.github.creme332.controller.patron.RegisterController;
import com.github.creme332.model.AppState;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.*;
import com.github.creme332.view.patron.Registration;

/**
 * Main controller of application.
 */
public class Controller implements PropertyChangeListener {
    private AppState app = new AppState();

    private Frame frame; // frame of app

    // declare controller & view for registration
    private Registration patronRegistrationPage;
    private RegisterController registrationController;

    private Login loginPage;
    private LoginController loginController;

    /**
     * Displays splash screen temporarily
     */
    private void playStartAnimation() {
        Timer timer = new Timer();
        TimerTask showMainScreen;
        final long animationDuration = 800; // ms

        app.setCurrentScreen(ScreenName.SPLASH_SCREEN);

        // show login screen when timer has elapsed
        showMainScreen = new TimerTask() {
            @Override
            public void run() {
                app.setCurrentScreen(ScreenName.LOGIN_SCREEN);
                timer.cancel();
                timer.purge();
            }
        };
        timer.schedule(showMainScreen, animationDuration);
    }

    public Controller() {
        try {
            app.addPropertyChangeListener(this); // listen to changes to app state

            frame = new Frame();

            patronRegistrationPage = (Registration) frame.getPage(ScreenName.PATRON_REGISTRATION_SCREEN);
            registrationController = new RegisterController(app, patronRegistrationPage);

            loginPage = (Login) frame.getPage(ScreenName.LOGIN_SCREEN);
            loginController = new LoginController(app, loginPage);

        } catch (InvalidPathException e) {
            e.printStackTrace();
            System.exit(0);
        }

        playStartAnimation();
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();

        if ("currentScreen".equals(propertyName)) {
            frame.switchToScreen((ScreenName) e.getNewValue());
        }
    }

}
package com.github.creme332.controller;

import java.util.Timer;
import java.util.TimerTask;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import com.github.creme332.model.AppState;
import com.github.creme332.view.*;

public class FrameController implements PropertyChangeListener {
    private Frame frame;
    private AppState app;

    public FrameController(AppState app, Frame frame) {
        this.frame = frame;
        this.app = app;

        app.addPropertyChangeListener(this); // listen to changes to app state
    }

    /**
     * Show splash screen animation
     */
    public void playAnimation() {
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

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();

        if ("currentScreen".equals(propertyName)) {
            frame.switchToScreen((ScreenName) e.getNewValue());
        }
    }
}

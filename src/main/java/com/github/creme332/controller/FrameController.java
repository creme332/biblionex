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
        TimerTask showNextScreen;
        final long animationDuration = 800; // ms

        frame.switchToScreen(Screen.SPLASH_SCREEN);

        // show screen set by AppState when timer has elapsed
        showNextScreen = new TimerTask() {
            @Override
            public void run() {
                frame.switchToScreen(app.getCurrentScreen());
                timer.cancel();
                timer.purge();
            }
        };
        timer.schedule(showNextScreen, animationDuration);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("currentScreen".equals(propertyName)) {
            frame.switchToScreen((Screen) e.getNewValue());
        }
    }
}

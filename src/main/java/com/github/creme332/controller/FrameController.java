package com.github.creme332.controller;

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
        final long animationDuration = 800; // ms

        Thread th = new Thread() {
            @Override
            public void run() {
                frame.switchToScreen(Screen.SPLASH_SCREEN);

                try {
                    Thread.sleep(animationDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                frame.switchToScreen(app.getCurrentScreen());
            }
        };
        th.start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("currentScreen".equals(propertyName)) {
            frame.switchToScreen((Screen) e.getNewValue());
        }
    }
}

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

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("currentScreen".equals(propertyName)) {
            frame.switchToScreen((Screen) e.getNewValue());
        }
    }
}

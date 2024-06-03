package com.github.creme332.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.github.creme332.controller.ScreenName;

public class AppState {
    private ScreenName currentScreen;
    private PropertyChangeSupport support;

    public AppState() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("currentScreen", listener);
    }

    public ScreenName getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(ScreenName newScreen) {
        // System.out.println("Switching screens: " + currentScreen.getScreenName() + " -> " + newScreen.getScreenName());

        if (currentScreen == newScreen)
            return;
        support.firePropertyChange("currentScreen", currentScreen, newScreen);
        currentScreen = newScreen;
    }
}

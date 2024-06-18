package com.github.creme332.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.github.creme332.controller.Screen;

public class AppState {
    private Screen currentScreen = Screen.LIBRARIAN_REGISTRATION_SCREEN;
    private PropertyChangeSupport support;
    private User loggedInUser;

    public AppState() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("currentScreen", listener);
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen newScreen) {
        // System.out.println("Switching screens: " + currentScreen.getScreenName() +
        // "-> " + newScreen.getScreenName());
        support.firePropertyChange("currentScreen", currentScreen, newScreen);
        currentScreen = newScreen;
    }

    public void setLoggedInUser(User newUser) {
        loggedInUser = newUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}

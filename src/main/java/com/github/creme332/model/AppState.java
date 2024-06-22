package com.github.creme332.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.Date;

import com.github.creme332.controller.Screen;

public class AppState {
    private Screen currentScreen = Screen.LOGIN_SCREEN;
    private PropertyChangeSupport support;
    private User loggedInUser;
    /**
     * Determines whether to automatically login as librarian or patron or none.
     * This is meant to be used for debugging only.
     */
    private UserType autoLogin = null;

    public AppState() {
        support = new PropertyChangeSupport(this);
    }

    public UserType getAutoLogin() {
        return autoLogin;
    }

    /**
     * Automatically login as librarian or user. It creates a default account or
     * uses existing one.
     * Use this function only during debugging.
     * 
     * @param type
     */
    public void autoLogin(UserType type) {
        final String DEFAULT_PASSWORD = "1234";
        final Patron defaultPatron = new Patron("patron@biblionex.com", DEFAULT_PASSWORD, "Royal Road", "Patron",
                "Test",
                "54353534", "45325435354", new Date());
        final Librarian defaultLibrarian = new Librarian("librarian@biblionex.com", DEFAULT_PASSWORD,
                defaultPatron.getAddress(), "Admin", "Test", "4324532423", "Admin test");

        if (type == UserType.LIBRARIAN) {
            try {
                loggedInUser = Librarian.findByEmail(defaultLibrarian.getEmail());
                if (loggedInUser == null) {
                    Librarian.save(defaultLibrarian);
                    loggedInUser = Librarian.findByEmail(defaultLibrarian.getEmail());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(0);
            }
            setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN);
        }
        if (type == UserType.PATRON) {
            try {
                loggedInUser = Patron.findByEmail(defaultPatron.getEmail());
                if (loggedInUser == null) {
                    Patron.save(defaultPatron);
                    loggedInUser = Patron.findByEmail(defaultPatron.getEmail());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
            setCurrentScreen(Screen.PATRON_DASHBOARD_SCREEN);
        }
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

    public void logOut() {
        loggedInUser = null;
        setCurrentScreen(Screen.LOGIN_SCREEN);
    }
}

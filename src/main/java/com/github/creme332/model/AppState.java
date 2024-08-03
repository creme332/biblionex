package com.github.creme332.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.Date;

import com.github.creme332.controller.Screen;

public class AppState {
    private PropertyChangeSupport support;
    /**
     * Account of currently logged in user.
     */
    private User loggedInUser;
    /**
     * Last screen displayed.
     */
    private Screen previousScreen;

    /**
     * Current screen displayed. The initial value of this variable is the first
     * screen displayed after SPLASH_SCREEN.
     */
    private Screen currentScreen = Screen.LIBRARIAN_MATERIAL_SCREEN;

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
        /**
         * Password for default librarian and patron accounts. if you have changed the
         * password previously, this password may be inaccurate but auto login will
         * still succeed.
         */
        final String DEFAULT_PASSWORD = "1234";
        final Patron defaultPatron = new Patron("patron@biblionex.com", DEFAULT_PASSWORD, "Royal Road", "Patron",
                "Test",
                "54353534", "45325435354", new Date());
        final Librarian defaultLibrarian = new Librarian("librarian@biblionex.com", DEFAULT_PASSWORD,
                defaultPatron.getAddress(), "Admin", "Test", "4324532423", "Admin test");

        User user = null;
        if (type == UserType.LIBRARIAN) {
            try {
                user = Librarian.findByEmail(defaultLibrarian.getEmail());
                if (user == null) {
                    // account does not exist yet so create it
                    Librarian.save(defaultLibrarian);
                    user = Librarian.findByEmail(defaultLibrarian.getEmail());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(0);
            }
            setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN);
        }

        if (type == UserType.PATRON) {
            try {
                user = Patron.findByEmail(defaultPatron.getEmail());
                if (user == null) {
                    // account does not exist yet so create it
                    Patron.save(defaultPatron);
                    user = Patron.findByEmail(defaultPatron.getEmail());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
            setCurrentScreen(Screen.PATRON_DASHBOARD_SCREEN);
        }

        setLoggedInUser(user);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener("currentScreen", listener);
        support.addPropertyChangeListener("loggedInUser", listener);
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public Screen getPreviousScreen() {
        return previousScreen;
    }

    public void setCurrentScreen(Screen newScreen) {
        if (newScreen == null) {
            System.out.println(
                    "New screen should not be null. If you clicked on back button, ensure that you have previously visited the previous page.");
            return;
        }
        System.out.println("Switching screens: " + currentScreen.name() + "-> " + newScreen.name());
        support.firePropertyChange("currentScreen", currentScreen, newScreen);
        previousScreen = currentScreen;
        currentScreen = newScreen;
    }

    public void setLoggedInUser(User newUser) {
        support.firePropertyChange("loggedInUser", loggedInUser, newUser);
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

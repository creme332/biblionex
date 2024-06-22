package com.github.creme332.controller;

/**
 * Name of screen that can be displayed on frame.
 */
public enum Screen {
    // screens common to both patron and librarian
    SPLASH_SCREEN("splashScreen"),
    LOGIN_SCREEN("loginScreen"),

    // patron screens
    PATRON_REGISTRATION_SCREEN("patronRegistrationScreen"),
    PATRON_DASHBOARD_SCREEN("patronDashboardScreen"),

    // librarian screens
    LIBRARIAN_MATERIAL_SCREEN("librarianMaterialScreen"),
    LIBRARIAN_DASHBOARD_SCREEN("librarianDashboardScreen"),
    LIBRARIAN_LIST_SCREEN("PatronListScreen"),
    LIBRARIAN_REGISTRATION_SCREEN("librarianRegistrationScreen");

    private final String screenName;

    Screen(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenName() {
        return screenName;
    }
}
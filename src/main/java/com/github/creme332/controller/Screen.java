package com.github.creme332.controller;

/**
 * Name of screen that can be displayed on frame.
 */
public enum Screen {
    // screens common to both patron and librarian
    SPLASH_SCREEN("splashScreen"),
    LOGIN_SCREEN("loginScreen"),
    FORGET_PASSWORD("forgetPassword"),

    // patron screens
    PATRON_REGISTRATION_SCREEN("patronRegistrationScreen"),
    PATRON_DASHBOARD_SCREEN("patronDashboardScreen"),
    PATRON_LOAN_SCREEN("patronLoanScreen"),

    // librarian screens
    LIBRARIAN_DASHBOARD_SCREEN("librarianDashboardScreen"),
    LIBRARIAN_VENDOR_SCREEN("librarianVendorScreen"),
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
package com.github.creme332.controller;

public enum ScreenNames {
    SPLASH_SCREEN("splashScreen"),
    HOME_SCREEN("homeScreen"),
    LOGIN_SCREEN("loginScreen"),
    PATRON_REGISTRATION_SCREEN("patronRegistrationScreen"),
    PATRON_DASHBOARD_SCREEN("patronDashboardScreen"),
    SETTINGS_SCREEN("settingsScreen");

    private final String screenName;

    ScreenNames(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenName() {
        return screenName;
    }
}
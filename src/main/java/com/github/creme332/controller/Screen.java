package com.github.creme332.controller;

/**
 * Name of screen that can be displayed on frame.
 */
public enum Screen {
    // screens common to both patron and librarian
    SPLASH_SCREEN,
    LOGIN_SCREEN,
    FORGET_PASSWORD,

    // patron screens
    PATRON_REGISTRATION_SCREEN,
    PATRON_DASHBOARD_SCREEN,
    PATRON_ACCOUNT_SCREEN,
    PATRON_CATALOG_SCREEN,

    // librarian screens
    LIBRARIAN_DASHBOARD_SCREEN,
    LIBRARIAN_LIBRARIAN_LIST_SCREEN,
    LIBRARIAN_PATRON_LIST_SCREEN,
    LIBRARIAN_REGISTRATION_SCREEN;
}
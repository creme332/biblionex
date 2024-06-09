package com.github.creme332.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Librarian;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;
import com.github.creme332.view.Login;

/**
 * Controller for login page
 */
public class LoginController {
    private Login loginPage;
    private AppState app;
    private String userType = "librarian";

    public LoginController(AppState app, Login loginPage) {
        this.loginPage = loginPage;
        this.app = app;

        // Add action listener to login button
        loginPage.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginPage.getEmail();

                // assuming is a patron, find his account
                User user = Patron.findByEmail(email);

                // if no patron account found, assume that user is librarian
                if (user == null) {
                    user = Librarian.findByEmail(email);
                }

                // if email invalid, show error
                if (user == null) {
                    loginPage.showError();
                    return;
                }

                // validate password
                if (!user.authenticate(email, loginPage.getPassword())) {
                    loginPage.showError();
                    return;
                }

                if (user.getUserType() == UserType.PATRON) {
                    app.setLoggedInUser(Patron.findByEmail(email));
                    app.setCurrentScreen(Screen.PATRON_DASHBOARD_SCREEN);
                } else {
                    app.setLoggedInUser(Librarian.findByEmail(email));
                    app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN);
                }

            }
        });

        loginPage.getRegisterButton().addActionListener(e -> app.setCurrentScreen(Screen.PATRON_REGISTRATION_SCREEN));
    }
}

package com.github.creme332.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Librarian;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
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

                if (authenticateUser(email, loginPage.getPassword())) {
                    loginPage.hideError();

                    if (userType.equals("patron")) {
                        app.setLoggedInUser(Patron.findByEmail(email));
                        app.setCurrentScreen(Screen.PATRON_DASHBOARD_SCREEN);
                    } else {
                        app.setLoggedInUser(Librarian.findByEmail(email));
                        app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN);
                    }

                } else {
                    // invalid credentials
                    loginPage.showError();
                }
            }
        });

        loginPage.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setCurrentScreen(Screen.PATRON_REGISTRATION_SCREEN);
            }
        });
    }

    public boolean authenticateUser(String email, char[] enteredPassword) {
        boolean isCorrect = true;

        // check if user is a valid librarian
        User user = Librarian.findByEmail(email);
        if (user == null) {
            // check if user is a patron
            user = Patron.findByEmail(email);
            if (user == null) {
                Arrays.fill(enteredPassword, '0');
                return false;
            }
            userType = "patron";
        }

        // verify passwords
        char[] correctPassword = user.getPassword().toCharArray();
        if (enteredPassword.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals(enteredPassword, correctPassword);
        }

        // Zero out the password for security purposes.
        Arrays.fill(correctPassword, '0');
        Arrays.fill(enteredPassword, '0');

        return isCorrect;
    }
}

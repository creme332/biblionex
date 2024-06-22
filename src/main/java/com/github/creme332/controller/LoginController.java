package com.github.creme332.controller;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Librarian;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;
import com.github.creme332.view.Frame;
import com.github.creme332.view.Login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class LoginController {
    private Login loginPage;
    private AppState app;
    private Frame frame;

    public LoginController(AppState app, Login loginPage, Frame frame) {
        this.loginPage = loginPage;
        this.app = app;
        this.frame = frame;

        // Create a common ActionListener for login logic
        ActionListener loginAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginPage.getEmail();

                // assuming is a patron, find his account
                User user;
                try {
                    user = Patron.findByEmail(email);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                // if no patron account found, assume that user is librarian
                if (user == null) {
                    try {
                        user = Librarian.findByEmail(email);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        return;
                    }
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

                // erase entered data on form
                loginPage.resetFields();

                if (user.getUserType() == UserType.PATRON) {
                    try {
                        Patron patron = Patron.findByEmail(email);
                        app.setLoggedInUser(patron);
                        frame.addPatronSideBar(patron);
                        app.setCurrentScreen(Screen.PATRON_DASHBOARD_SCREEN);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        return;
                    }
                } else {
                    try {
                        app.setLoggedInUser(Librarian.findByEmail(email));
                        app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        return;
                    }
                }
            }
        };

        // Add action listener to login button
        loginPage.getLoginButton().addActionListener(loginAction);

        // Add key bindings to emailField and passwordField
        Action loginSubmitAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAction.actionPerformed(e);
            }
        };

        loginPage.getEmailField().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "submit");
        loginPage.getEmailField().getActionMap().put("submit", loginSubmitAction);

        loginPage.getPasswordField().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"),
                "submit");
        loginPage.getPasswordField().getActionMap().put("submit", loginSubmitAction);

        // Add action listener to register button
        loginPage.getRegisterButton().addActionListener(e -> app.setCurrentScreen(Screen.PATRON_REGISTRATION_SCREEN));
    }
}

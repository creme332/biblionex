package com.github.creme332.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.creme332.model.AppState;
import static com.github.creme332.model.User.authenticate;
import com.github.creme332.view.Login;

/**
 * Controller for login page
 */
public class LoginController {
    private Login loginPage;
    private AppState app;

    public LoginController(AppState app, Login loginPage) {
        this.loginPage = loginPage;
        this.app = app;

        // Add action listener to login button
        loginPage.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (authenticate(loginPage.getName(), loginPage.getPassword())) {
                    // TODO: save user info to app
                    loginPage.hideError();
                    app.setCurrentScreen(ScreenName.PATRON_DASHBOARD_SCREEN);

                } else {
                    // invalid credentials
                    loginPage.showError();
                }
            }
        });

        loginPage.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setCurrentScreen(ScreenName.PATRON_REGISTRATION_SCREEN);
            }
        });
    }
}

package com.github.creme332.controller.patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.creme332.controller.ScreenName;
import com.github.creme332.model.AppState;
import com.github.creme332.view.patron.Registration;

/**
 * Controller for registration page
 */
public class RegisterController {
    Registration registrationPage;
    AppState app;

    public RegisterController(AppState app, Registration registrationPage) {
        this.registrationPage = registrationPage;
        this.app = app;

        // Add action listener to register button
        registrationPage.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] user = registrationPage.getEnteredUser();
                String password = new String(registrationPage.getEnteredText());

                // Hardcoded username and password for validation
                if (user.equals("admin") && password.equals("password")) {
                    System.out.println("Registration successful. Must switch to dashboard screen...");
                    app.setCurrentScreen(ScreenName.LOGIN_SCREEN);
                } else {
                    registrationPage.setErrorMessage("Invalid info!");
                }
            }
        });

        registrationPage.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setCurrentScreen(ScreenName.LOGIN_SCREEN);
            }
        });
    }
}

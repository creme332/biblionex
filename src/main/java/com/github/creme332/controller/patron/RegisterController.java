package com.github.creme332.controller.patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Patron;
import com.github.creme332.view.patron.Registration;

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
                String email = registrationPage.getEmail();
                char[] password = registrationPage.getPassword();
                char[] confirmPassword = registrationPage.getConfirmPassword();
                String firstName = registrationPage.getFirstName();
                String lastName = registrationPage.getLastName();
                String phone = registrationPage.getPhone();
                String address = registrationPage.getAddress();
                String creditCardNo = registrationPage.getCreditCardNo();
                String expiryDate = registrationPage.getExpiryDate();
                String securityCode = registrationPage.getSecurityCode();

                if (!new String(password).equals(new String(confirmPassword))) {
                    registrationPage.setErrorMessage("Passwords do not match!");
                    return;
                }

                if (email.isEmpty() || password.length == 0 || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    registrationPage.setErrorMessage("All fields must be filled out!");
                    return;
                }

                Patron patron = new Patron(email, new String(password), 0, address, firstName, lastName, phone, creditCardNo, null);
                Patron.save(patron);
                
                registrationPage.setSuccessMessage("Registration successful. Please log in.");
                app.setCurrentScreen(Screen.LOGIN_SCREEN);
            }
        });

        // Add action listener to back button
        registrationPage.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setCurrentScreen(Screen.LOGIN_SCREEN);
            }
        });
    }
}

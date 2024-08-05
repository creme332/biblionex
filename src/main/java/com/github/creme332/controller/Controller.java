package com.github.creme332.controller;

import com.github.creme332.controller.patron.RegisterController;
import com.github.creme332.model.AppState;
import com.github.creme332.utils.exception.InvalidPathException;

import com.github.creme332.view.ForgotPassword;
import com.github.creme332.view.Frame;
import com.github.creme332.view.Login;
import com.github.creme332.view.patron.Registration;

import javax.swing.JOptionPane;

/**
 * Main controller of application responsible for creating other controllers and app model.
 */
public class Controller {
    private AppState app = new AppState();

    public Controller() {
        Frame frame = null;
        FrameController frameController = null;
        try {
            frame = new Frame();
            frameController = new FrameController(app, frame);
        } catch (InvalidPathException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // declare controllers of pages accessible to anyone (unsigned users, librarian,
        // patron)
        new RegisterController(app, (Registration) frame.getPage(Screen.PATRON_REGISTRATION_SCREEN));
        new ForgotPasswordController(app, (ForgotPassword) frame.getPage(Screen.FORGET_PASSWORD));
        new LoginController(app, (Login) frame.getPage(Screen.LOGIN_SCREEN));

        // declare controllers for pages accessible to patron only
        new com.github.creme332.controller.patron.Controller(app, frame);

        // declare controllers for pages accessible to librarian only
        new com.github.creme332.controller.librarian.Controller(app, frame);

        if (app.getAutoLogin() != null) {
            // auto login enabled
            app.autoLogin(app.getAutoLogin());
        } else {
            // start application normally
            frameController.playAnimation();
        }
    }

    public boolean confirmLogout() {
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    public void handleLogout() {
        if (confirmLogout()) {
            app.logOut();
        }
    }
}

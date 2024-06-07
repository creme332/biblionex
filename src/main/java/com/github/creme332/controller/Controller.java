package com.github.creme332.controller;

import com.github.creme332.controller.patron.RegisterController;
import com.github.creme332.model.AppState;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.*;
import com.github.creme332.view.patron.Registration;

/**
 * Main controller of application.
 */
public class Controller {
    private AppState app = new AppState();

    public Controller() {
        Frame frame = null;
        
        try {
            frame = new Frame();
        } catch (InvalidPathException e) {
            e.printStackTrace();
            System.exit(0);
        }

        Registration patronRegistrationPage = (Registration) frame.getPage(ScreenName.PATRON_REGISTRATION_SCREEN);
        new RegisterController(app, patronRegistrationPage);

        Login loginPage = (Login) frame.getPage(ScreenName.LOGIN_SCREEN);
        new LoginController(app, loginPage);

        FrameController controller = new FrameController(app, frame);
        controller.playAnimation();
    }
}
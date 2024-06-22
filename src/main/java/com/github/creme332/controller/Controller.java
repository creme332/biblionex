package com.github.creme332.controller;

import com.github.creme332.controller.librarian.RegistrationController;
import com.github.creme332.controller.librarian.ListPageController;
import com.github.creme332.controller.patron.RegisterController;
import com.github.creme332.model.AppState;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.*;
import com.github.creme332.view.librarian.RegistrationForm;
import com.github.creme332.view.librarian.ListPage;
import com.github.creme332.view.patron.Registration;

/**
 * Main controller of application.
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

        Registration patronRegistrationPage = (Registration) frame.getPage(Screen.PATRON_REGISTRATION_SCREEN);
        new RegisterController(app, patronRegistrationPage);

        new RegistrationController(app, (RegistrationForm) frame.getPage(Screen.LIBRARIAN_REGISTRATION_SCREEN));

        new ListPageController(app, (ListPage) frame.getPage(Screen.LIBRARIAN_LIST_SCREEN));

        Login loginPage = (Login) frame.getPage(Screen.LOGIN_SCREEN);
        new LoginController(app, loginPage);

        new com.github.creme332.controller.patron.DashboardController(app,
                (com.github.creme332.view.patron.Dashboard) frame
                        .getPage(Screen.PATRON_DASHBOARD_SCREEN));

        frameController.playAnimation();
    }
}
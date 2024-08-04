package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;
import com.github.creme332.view.librarian.Dashboard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardController implements PropertyChangeListener {
    private AppState app;
    private Dashboard dashboard;

    public DashboardController(AppState app, Dashboard dashboard) {
        this.app = app;
        this.dashboard = dashboard;
        app.addPropertyChangeListener(this);

        dashboard.getLogOutButton().addActionListener(e -> app.logOut());

        dashboard.getCheckInButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_CHECKIN_SCREEN));

        dashboard.getMaterialsButton().addActionListener(e -> {
        });

        dashboard.getCatalogingButton()
                .addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_CATALOGING_SCREEN));

        dashboard.getCheckOutButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_CHECKOUT_SCREEN));

        dashboard.getPatronsButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_PATRON_LIST_SCREEN));

        dashboard.getOverduesButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_OVERDUE_LOANS_SCREEN));

        dashboard.getOverduesButton().addActionListener(e -> {
        });

        dashboard.getLibrariansButton()
                .addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_LIBRARIAN_LIST_SCREEN));
                

        dashboard.getAcquisitionsButton().addActionListener(e -> {
        });

        dashboard.getReportsButton().addActionListener(e -> {
        });
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("loggedInUser")) {

            // check if logged in user is a librarian
            User newUser = (User) evt.getNewValue();
            if (newUser == null
                    || newUser.getUserType() != UserType.LIBRARIAN)
                return;

            // update welcome message
            dashboard.updateWelcomeMessage(newUser.getFirstName(), newUser.getLastName());
        }
    }
}

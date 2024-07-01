package com.github.creme332.controller.librarian;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.view.librarian.Dashboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardController {
    private AppState app;
    private Dashboard dashboard;

    public DashboardController(AppState app, Dashboard dashboard) {
        this.app = app;
        this.dashboard = dashboard;

        dashboard.getLogOutButton().addActionListener(e -> app.logOut());

        dashboard.getCheckInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Check in button click
            }
        });

        dashboard.getMaterialsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Materials button click
            }
        });

        dashboard.getCatalogingButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Cataloging button click
            }
        });

        dashboard.getCheckOutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Check out button click
            }
        });

        dashboard.getPatronsButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_PATRON_LIST_SCREEN));

        dashboard.getOverduesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Overdues button click
            }
        });

        dashboard.getRenewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Renew button click
            }
        });

        dashboard.getLibrariansButton()
                .addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_LIBRARIAN_LIST_SCREEN));

        dashboard.getAcquisitionsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Acquisitions button click
            }
        });

        dashboard.getReportsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Reports button click
            }
        });
    }
}

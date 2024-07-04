package com.github.creme332.view.librarian;

import javax.swing.*;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.*;

public class Dashboard extends JPanel {
    private JButton logOutButton;
    private JButton checkInButton;
    private JButton materialsButton;
    private JButton catalogingButton;
    private JButton checkOutButton;
    private JButton patronsButton;
    private JButton overduesButton;
    private JButton renewButton;
    private JButton librariansButton;
    private JButton acquisitionsButton;
    private JButton reportsButton;

    private JLabel titleLabel = new JLabel("Admin Dashboard", javax.swing.SwingConstants.CENTER);

    public Dashboard() {
        setLayout(new BorderLayout());

        // Header panel with back button and title
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Create log out button
        FontIcon logoutIcon = FontIcon.of(BootstrapIcons.BOX_ARROW_IN_LEFT, 40);
        logoutIcon.setIconColor(Color.white);
        logOutButton = new JButton("Log out", logoutIcon);
        logOutButton.setBackground(Color.red);

        // update style of title
        titleLabel.putClientProperty("FlatLaf.style", "font: bold $large.font");

        headerPanel.add(logOutButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Main panel with buttons
        JPanel mainPanel = new JPanel(new GridLayout(4, 3, 10, 10));

        checkInButton = new JButton("Check in");
        materialsButton = new JButton("Materials");
        catalogingButton = new JButton("Cataloging");
        checkOutButton = new JButton("Check out");
        patronsButton = new JButton("Patrons");
        overduesButton = new JButton("Overdues");
        renewButton = new JButton("Renew");
        librariansButton = new JButton("Librarians");
        acquisitionsButton = new JButton("Acquisitions");
        reportsButton = new JButton("Reports");

        mainPanel.add(checkInButton);
        mainPanel.add(materialsButton);
        mainPanel.add(catalogingButton);
        mainPanel.add(checkOutButton);
        mainPanel.add(patronsButton);
        mainPanel.add(overduesButton);
        mainPanel.add(renewButton);
        mainPanel.add(librariansButton);
        mainPanel.add(acquisitionsButton);
        mainPanel.add(new JLabel()); // Empty cell to center the last button
        mainPanel.add(reportsButton);
        mainPanel.add(new JLabel()); // Empty cell to center the last button

        // Scroll pane for the main panel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Add panels to the main layout
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateWelcomeMessage(String firstName, String lastName) {
        titleLabel.setText(String.format("Welcome %s %s (Admin)", firstName, lastName));
    }

    public JButton getLogOutButton() {
        return logOutButton;
    }

    public JButton getCheckInButton() {
        return checkInButton;
    }

    public JButton getMaterialsButton() {
        return materialsButton;
    }

    public JButton getCatalogingButton() {
        return catalogingButton;
    }

    public JButton getCheckOutButton() {
        return checkOutButton;
    }

    public JButton getPatronsButton() {
        return patronsButton;
    }

    public JButton getOverduesButton() {
        return overduesButton;
    }

    public JButton getRenewButton() {
        return renewButton;
    }

    public JButton getLibrariansButton() {
        return librariansButton;
    }

    public JButton getAcquisitionsButton() {
        return acquisitionsButton;
    }

    public JButton getReportsButton() {
        return reportsButton;
    }
}

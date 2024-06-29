package com.github.creme332.view.librarian;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {
    private JButton logOutButton;
    private JButton checkInButton, materialsButton, catalogingButton;
    private JButton checkOutButton, patronsButton, overduesButton;
    private JButton renewButton, librariansButton, acquisitionsButton;
    private JButton reportsButton;

    public Dashboard() {
        setLayout(new BorderLayout());

        // Header panel with back button and title
        JPanel headerPanel = new JPanel(new BorderLayout());
        logOutButton = new JButton("Log out");
        JLabel titleLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Add panels to the main layout
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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

package com.github.creme332.view.librarian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        JPanel mainPanel = new JPanel(new GridLayout(3, 3, 40, 40));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        checkInButton = new JButton("Check in");
        checkInButton.setIcon(FontIcon.of(BootstrapIcons.ARROW_90DEG_RIGHT, 40, Color.white));
        checkInButton.setIconTextGap(10);

        materialsButton = new JButton("Materials");
        materialsButton.setIcon(FontIcon.of(BootstrapIcons.CARD_LIST, 40, Color.white));
        materialsButton.setIconTextGap(10);

        catalogingButton = new JButton("Cataloging");
        catalogingButton.setIcon(FontIcon.of(BootstrapIcons.BOOKMARK, 40, Color.white));
        catalogingButton.setIconTextGap(10);

        checkOutButton = new JButton("Check out");
        checkOutButton.setIcon(FontIcon.of(BootstrapIcons.ARROW_90DEG_LEFT, 40, Color.white));
        checkOutButton.setIconTextGap(10);

        patronsButton = new JButton("Patrons");
        patronsButton.setIcon(FontIcon.of(BootstrapIcons.PERSON_BADGE, 40, Color.white));
        patronsButton.setIconTextGap(10);

        overduesButton = new JButton("Overdues");
        overduesButton.setIcon(FontIcon.of(BootstrapIcons.CASH, 40, Color.white));
        overduesButton.setIconTextGap(10);

        librariansButton = new JButton("Librarians");
        librariansButton.setIcon(FontIcon.of(BootstrapIcons.PERSON_BADGE, 40, Color.white));
        librariansButton.setIconTextGap(10);

        acquisitionsButton = new JButton("Acquisitions");
        acquisitionsButton.setIcon(FontIcon.of(BootstrapIcons.CART, 40, Color.white));
        acquisitionsButton.setIconTextGap(10);

        reportsButton = new JButton("Reports");
        reportsButton.setIcon(FontIcon.of(BootstrapIcons.CLIPBOARD_DATA, 40, Color.white));
        reportsButton.setIconTextGap(10);

        mainPanel.add(checkInButton);
        mainPanel.add(materialsButton);
        mainPanel.add(catalogingButton);
        mainPanel.add(checkOutButton);
        mainPanel.add(patronsButton);
        mainPanel.add(overduesButton);
        mainPanel.add(librariansButton);
        mainPanel.add(acquisitionsButton);
        mainPanel.add(reportsButton);

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

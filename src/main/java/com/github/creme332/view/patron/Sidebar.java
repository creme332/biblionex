package com.github.creme332.view.patron;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

public class Sidebar extends JPanel {
    private JButton dashboardButton;
    private JButton loansButton;
    private JButton catalogButton;
    private JButton accountButton;
    private JButton logOutButton;
    private JLabel nameLabel;
    private JLabel roleLabel;
    private JLabel iconLabel;

    JButton activeButton;

    Color sidebarBackgroundColor;

    public Sidebar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, getHeight()));
        setBorder(new MatteBorder(0, 0, 0, 2, Color.white));

        // User Info Section
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sidebarBackgroundColor = userInfoPanel.getBackground();

        iconLabel = new JLabel();
        FontIcon icon = FontIcon.of(BootstrapIcons.PERSON_CIRCLE, 150);
        icon.setIconColor(Color.WHITE);
        iconLabel.setIcon(icon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameLabel = new JLabel();
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.putClientProperty("FlatLaf.style", "font: $large.font");

        roleLabel = new JLabel("Patron");
        roleLabel.putClientProperty("FlatLaf.style", "font: $small.font");
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInfoPanel.add(iconLabel);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(roleLabel);

        add(userInfoPanel);

        // Buttons Section
        dashboardButton = createButton("Dashboard");
        loansButton = createButton("Loans");
        catalogButton = createButton("Catalog");
        accountButton = createButton("Account");
        logOutButton = createButton("");
        FontIcon logoutIcon = FontIcon.of(BootstrapIcons.BOX_ARROW_IN_LEFT, 40);
        logoutIcon.setIconColor(Color.white);
        logOutButton.setIcon(logoutIcon);

        add(createButtonPanel(dashboardButton));
        add(createButtonPanel(loansButton));
        add(createButtonPanel(catalogButton));
        add(createButtonPanel(accountButton));

        add(Box.createVerticalGlue());

        add(createButtonPanel(logOutButton));
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.putClientProperty("FlatLaf.style", "font: $h3.font");
        button.setMaximumSize(new Dimension(getWidth(), 30));
        button.setBackground(sidebarBackgroundColor);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        return button;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);
        return panel;
    }

    public void setPatronDetails(String firstName, String lastName) {
        nameLabel.setText(firstName + " " + lastName);
    }

    public JButton getDashboardButton() {
        return dashboardButton;
    }

    public JButton getLoansButton() {
        return loansButton;
    }

    public JButton getCatalogButton() {
        return catalogButton;
    }

    public JButton getAccountButton() {
        return accountButton;
    }

    public JButton getLogOutButton() {
        return logOutButton;
    }

    public void highlightButton(JButton button) {
        // reset style of current active button
        if (activeButton != null) {
            activeButton.setBackground(getBackground());
            activeButton.setForeground(Color.white);
        }

        // update style of new active button
        button.setBackground(new Color(187, 134, 252));
        button.setForeground(Color.black);

        activeButton = button;
    }
}

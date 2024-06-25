package com.github.creme332.view.patron;

import javax.swing.*;
import java.awt.*;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

public class SideBar extends JPanel {
    private JButton dashboardButton;
    private JButton loansButton;
    private JButton catalogButton;
    private JButton accountButton;
    private JButton logOutButton;
    private JLabel nameLabel;
    private JLabel roleLabel;
    private JLabel iconLabel;

    public SideBar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        setPreferredSize(new Dimension(400, getHeight()));

        // User Info Section
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        userInfoPanel.setBackground(Color.white);

        iconLabel = new JLabel();
        FontIcon icon = FontIcon.of(BootstrapIcons.PERSON_CIRCLE, 150);
        iconLabel.setIcon(icon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameLabel = new JLabel();
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setForeground(Color.black);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));

        roleLabel = new JLabel("Patron");
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roleLabel.setForeground(Color.black);
        roleLabel.setFont(roleLabel.getFont().deriveFont(Font.BOLD));

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
        logOutButton = createButton("Log out");

        add(createButtonPanel(dashboardButton));
        add(createButtonPanel(loansButton));
        add(createButtonPanel(catalogButton));
        add(createButtonPanel(accountButton));

        add(Box.createVerticalGlue());

        add(createButtonPanel(logOutButton));
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        button.setBackground(Color.white);
        button.setForeground(Color.black); // Set text color to black
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
        dashboardButton.setBackground(Color.white);
        dashboardButton.setForeground(Color.black);
        loansButton.setBackground(Color.white);
        loansButton.setForeground(Color.black);
        catalogButton.setBackground(Color.white);
        catalogButton.setForeground(Color.black);
        accountButton.setBackground(Color.white);
        accountButton.setForeground(Color.black);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.white);
    }
}

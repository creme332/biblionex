package com.github.creme332.view.patron;

import javax.swing.*;
import java.awt.*;
import com.github.creme332.model.Patron;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

public class SideBar extends JPanel {
    private JButton dashboardButton;
    private JButton loansButton;
    private JButton catalogButton;
    private JButton accountButton;
    private JButton logOutButton;
    private JLabel nameLabel;

    public SideBar(Patron patron) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // User Info Section
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel iconLabel = new JLabel();
        FontIcon icon = FontIcon.of(BootstrapIcons.PERSON_CIRCLE, 50);
        iconLabel.setIcon(icon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameLabel = new JLabel(patron.getFirstName() + " " + patron.getLastName());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel("Patron");
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInfoPanel.add(iconLabel);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(roleLabel);

        add(userInfoPanel);

        // Buttons Section
        dashboardButton = new JButton("Dashboard");
        loansButton = new JButton("Loans");
        catalogButton = new JButton("Catalog");
        accountButton = new JButton("Account");
        logOutButton = new JButton("Log out");

        add(createButtonPanel(dashboardButton));
        add(createButtonPanel(loansButton));
        add(createButtonPanel(catalogButton));
        add(createButtonPanel(accountButton));

        add(Box.createVerticalGlue());

        add(createButtonPanel(logOutButton));
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.add(button, BorderLayout.CENTER);
        return panel;
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
        dashboardButton.setBackground(null);
        loansButton.setBackground(null);
        catalogButton.setBackground(null);
        accountButton.setBackground(null);
        button.setBackground(Color.BLUE);
    }
}

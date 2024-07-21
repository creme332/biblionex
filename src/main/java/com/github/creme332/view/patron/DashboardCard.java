package com.github.creme332.view.patron;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DashboardCard extends JPanel {
    private JLabel titleLabel = new JLabel();
    private JLabel descriptionLabel = new JLabel();

    public DashboardCard(String title, String description) {
        this.setLayout(new GridLayout(2, 1));
        this.setPreferredSize(new Dimension(200, 100));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.putClientProperty("FlatLaf.style", "arc: 10; background: @accentColor");

        titleLabel.setText(title);
        titleLabel.putClientProperty("FlatLaf.style", "font: bold $h2.font");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        descriptionLabel.setText(description);
        descriptionLabel.putClientProperty("FlatLaf.style", "font: $h4.font");
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(descriptionLabel);
    }

    public void setDescription(String text) {
        descriptionLabel.setText(text);
    }

    public void setTitle(String text) {
        titleLabel.setText(text);
    }
}

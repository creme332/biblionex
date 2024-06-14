package com.github.creme332.view;

import java.awt.*;
import javax.swing.*;

import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

public class SplashScreen extends JPanel {
    JLabel text = new JLabel("biblionex");
    GridBagConstraints gbc = new GridBagConstraints();

    SplashScreen() {
        setLayout(new GridBagLayout());

        text.putClientProperty("FlatLaf.style", "font: 260% $large.font");
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(text, gbc);

        FontIcon icon = FontIcon.of(BootstrapIcons.BOOK, 120);
        icon.setIconColor(Color.white);
        JLabel x = new JLabel();
        x.setIcon(icon);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(x, gbc);
    }
}
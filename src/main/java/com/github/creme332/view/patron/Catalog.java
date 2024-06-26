package com.github.creme332.view.patron;

import com.github.creme332.utils.IconLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Catalog extends JPanel {
    private static final Dimension ITEM_DIMENSION = new Dimension(200, 150);

    public Catalog() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setupUI();
    }

    public void addCatalogItem(String title, String iconPath, MouseAdapter mouseAdapter) {
        try {
            IconLoader iconLoader = new IconLoader();
            Icon icon = iconLoader.loadIcon(iconPath, 100, 150);
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setPreferredSize(ITEM_DIMENSION);
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
            itemPanel.addMouseListener(mouseAdapter);

            // Create image container
            JPanel imageContainer = new JPanel(new BorderLayout());
            JLabel image = new JLabel();
            image.setIcon(icon);
            imageContainer.add(image, BorderLayout.CENTER);

            // Create title container
            JPanel titleContainer = new JPanel();
            JLabel titleLabel = new JLabel(title);
            titleContainer.add(titleLabel);
            titleLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add components to the item panel
            itemPanel.add(imageContainer);
            itemPanel.add(titleContainer);

            // Add item panel to the catalog
            add(itemPanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupUI() {
        JFrame frame = new JFrame("Catalog Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Add the catalog to the center
        frame.add(new JScrollPane(this), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

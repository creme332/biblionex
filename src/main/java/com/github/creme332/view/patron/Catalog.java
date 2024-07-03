package com.github.creme332.view.patron;

import com.github.creme332.utils.IconLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Catalog extends JPanel {
    private static final Dimension ITEM_DIMENSION = new Dimension(200, 200);

    public Catalog() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
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
            JPanel titleContainer = new JPanel(new BorderLayout());

            // create a title label with word wrapping
            JLabel titleLabel = new JLabel("<html>" + title + "</html>");
            titleLabel.putClientProperty("FlatLaf.style", "font: $semibold.font");

            titleContainer.add(titleLabel, BorderLayout.NORTH);
            titleLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
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

    public JScrollPane createScrollableCatalog() {
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }
}

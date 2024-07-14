package com.github.creme332.view.patron;

import com.github.creme332.utils.IconLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Catalog extends JPanel {
    private static final Dimension ITEM_DIMENSION = new Dimension(200, 200);
    private JScrollPane scrollPane;
    private JPanel itemContainer;
    private int itemCounter = 0;

    public Catalog() {
        setLayout(new BorderLayout());
        itemContainer = new JPanel(new GridBagLayout());
        itemContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane = createScrollableCatalog();
        add(scrollPane, BorderLayout.CENTER);
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
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = itemCounter % 4;
            gbc.gridy = itemCounter / 4;
            gbc.insets = new Insets(5, 5, 5, 5);
            itemContainer.add(itemPanel, gbc);
            itemCounter++;
            itemContainer.revalidate();
            itemContainer.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JScrollPane createScrollableCatalog() {
        JScrollPane scrollPane = new JScrollPane(itemContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}

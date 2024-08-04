package com.github.creme332.view.patron;

import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.StringUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public class Catalog extends JPanel {
    private static final Dimension ITEM_DIMENSION = new Dimension(200, 200);
    private JScrollPane scrollPane;
    private JPanel itemContainer;
    private JTextField searchField;
    private JButton searchButton;
    private List<JPanel> itemPanels;
    private int itemCounter = 0;

    public Catalog() {
        setLayout(new BorderLayout());

        // Initialize the item container
        itemContainer = new JPanel(new GridBagLayout());
        itemContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize the search field
        searchField = new JTextField("Search by Material Title");
        searchField.setPreferredSize(new Dimension(400, 30));
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        searchField.setForeground(Color.GRAY);

        // Initialize the search button
        searchButton = new JButton("Search");

        // Create a panel for the search field and button
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Add search panel to the top of the panel
        add(searchPanel, BorderLayout.NORTH);

        // Initialize scroll pane
        scrollPane = createScrollableCatalog();
        add(scrollPane, BorderLayout.CENTER);

        // Initialize the list of item panels
        itemPanels = new ArrayList<>();
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

            // Create a title label with word wrapping
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

            // Add the panel to the list of item panels
            itemPanels.add(itemPanel);

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

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    // Expose the method to allow filtering from the controller
    public void filterItems(String searchText) {
        itemContainer.removeAll();
        itemCounter = 0;

        // Filter the items and update the catalog display
        for (JPanel itemPanel : itemPanels) {
            // Get the title label from the itemPanel
            JPanel titleContainer = (JPanel) itemPanel.getComponent(1);
            JLabel titleLabel = (JLabel) titleContainer.getComponent(0);
            String title = titleLabel.getText().replaceAll("<html>", "").replaceAll("</html>", "");

            if (searchText.isEmpty() || StringUtil.isSimilar(title.toLowerCase(), searchText.toLowerCase())) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = itemCounter % 4;
                gbc.gridy = itemCounter / 4;
                gbc.insets = new Insets(5, 5, 5, 5);
                itemContainer.add(itemPanel, gbc);
                itemCounter++;
            }
        }

        itemContainer.revalidate();
        itemContainer.repaint();
    }
}
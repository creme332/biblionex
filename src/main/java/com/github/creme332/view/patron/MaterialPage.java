package com.github.creme332.view.patron;

import com.github.creme332.model.Material;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.controller.Screen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MaterialPage extends JPanel {
    private static final Dimension IMAGE_DIMENSION = new Dimension(150, 200);
    private JLabel titleLabel;
    private JLabel imageLabel;
    private JTable materialTable;
    private JScrollPane scrollPane;
    private JButton backButton;

    public MaterialPage(ActionListener backAction) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Back button
        backButton = new JButton("Back");
        backButton.addActionListener(backAction);
        add(backButton, BorderLayout.NORTH);

        // Title label
        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.putClientProperty("FlatLaf.style", "font: $semibold.font");
        add(titleLabel, BorderLayout.NORTH);

        // Image container
        JPanel imageContainer = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(IMAGE_DIMENSION);
        imageContainer.add(imageLabel, BorderLayout.CENTER);
        add(imageContainer, BorderLayout.CENTER);

        // Material details table
        String[] columnNames = {"Attribute", "Value"};
        Object[][] data = new Object[6][2]; // Placeholder data, will be replaced in loadMaterial()
        materialTable = new JTable(data, columnNames);
        scrollPane = new JScrollPane(materialTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.SOUTH);
    }

    public void loadMaterial(Material material) {
        titleLabel.setText(material.getTitle());

        try {
            IconLoader iconLoader = new IconLoader();
            Icon icon = iconLoader.loadIcon(material.getImageUrl(), IMAGE_DIMENSION.width, IMAGE_DIMENSION.height);
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
            imageLabel.setIcon(null); // In case of error, set no image
        }

        Object[][] data = {
                {"Material ID", material.getMaterialId()},
                {"Publisher ID", material.getPublisherId()},
                {"Description", material.getDescription()},
                {"Image URL", material.getImageUrl()},
                {"Age Restriction", material.getAgeRestriction()},
                {"Type", material.getType().toString()}
        };

        materialTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Attribute", "Value"}));
    }
}

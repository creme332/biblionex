package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Material;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.patron.Catalog;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogController {
    Catalog catalog;
    List<Material> allMaterials = new ArrayList<>();

    public CatalogController(AppState app, Catalog catalog) {
        this.catalog = catalog;

        // Add document listener for real-time filtering
        catalog.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterMaterials();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterMaterials();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterMaterials();
            }
        });

        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    allMaterials = Material.findAllMaterials();
                    catalog.displayMaterials(allMaterials);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        };
        th.start();
    }

    private void filterMaterials() {
        String searchText = catalog.getSearchField().getText().trim();

        // Display all materials if the search field is empty
        if (searchText.isEmpty()) {
            catalog.displayMaterials(allMaterials);
            return;
        }
        List<Material> filteredMaterials = new ArrayList<>();

        for (Material material : allMaterials) {
            if (StringUtil.isSimilar(material.getTitle(), searchText)) {
                filteredMaterials.add(material);
            }
        }
        catalog.displayMaterials(filteredMaterials);
    }
}

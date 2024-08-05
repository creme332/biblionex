package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Material;
import com.github.creme332.view.patron.Catalog;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.sql.SQLException;
import java.util.List;

public class CatalogController {
    public CatalogController(AppState app, Catalog catalog) {
        // Add document listener for real-time filtering
        catalog.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        Thread th = new Thread() {
            @Override
            public void run() {
                List<Material> allMaterials;
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
}

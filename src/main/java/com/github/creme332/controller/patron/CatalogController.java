package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Material;
import com.github.creme332.view.patron.Catalog;

import java.sql.SQLException;
import java.util.List;

public class CatalogController {

    public CatalogController(AppState app, Catalog catalog) {

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

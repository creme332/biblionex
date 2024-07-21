package com.github.creme332.controller.patron;

import com.github.creme332.model.*;
import com.github.creme332.controller.Screen;
import com.github.creme332.view.patron.Catalog;
import com.github.creme332.view.patron.MaterialPage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatalogController {
    private AppState app;
    private Catalog catalog;
    private MaterialPage materialPage;

    public CatalogController(AppState app, Catalog catalog) {
        this.app = app;
        this.catalog = catalog;

        // Initialize MaterialPage with back button action listener
        materialPage = new MaterialPage(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setCurrentScreen(Screen.PATRON_CATALOG_SCREEN);
            }
        });

        Thread th = new Thread() {
            @Override
            public void run() {
                initCatalogItems();
            }
        };
        th.start();
    }

    private void initCatalogItems() {
        try {
            // Load books from the database
            List<Book> books = Book.findAll();
            for (Book book : books) {
                addItemToCatalog(book);
            }

            // Load videos from the database
            List<Video> videos = Video.findAll();
            for (Video video : videos) {
                addItemToCatalog(video);
            }

            // Load journals from the database
            List<Journal> journals = Journal.findAll();
            for (Journal journal : journals) {
                addItemToCatalog(journal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, possibly by showing an error message to the user
        }
    }

    private void addItemToCatalog(Material material) {
        catalog.addCatalogItem(material.getTitle(), "/catalog/" + material.getType().toString().toLowerCase() + ".png", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleItemClick(material);
            }
        });
    }

    private void handleItemClick(Material material) {
        materialPage.loadMaterial(material);
        app.setCurrentScreen(Screen.MATERIAL_PAGE);
    }
}

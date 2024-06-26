package com.github.creme332.controller.patron;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.view.patron.Catalog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CatalogController {
    private AppState app;
    private Catalog catalog;

    public CatalogController(AppState app, Catalog catalog) {
        this.app = app;
        this.catalog = catalog;
        initCatalogItems();
    }

    private void initCatalogItems() {
        addItemToCatalog("Book", "/catalog/book.png");
        addItemToCatalog("Video", "/catalog/video.png");
        addItemToCatalog("Journal", "/catalog/journal.png");
    }

    private void addItemToCatalog(String title, String iconPath) {
        catalog.addCatalogItem(title, iconPath, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleItemClick(title);
            }
        });
    }

    private void handleItemClick(String title) {
        switch (title) {
            case "Book":
                // app.setCurrentScreen(Screen.BOOK);
                break;
            case "Video":
                // app.setCurrentScreen(Screen.VIDEO);
                break;
            case "Journal":
                // app.setCurrentScreen(Screen.JOURNAL);
                break;
        }
    }
}

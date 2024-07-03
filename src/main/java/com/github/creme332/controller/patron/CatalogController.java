package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.Video;
import com.github.creme332.view.patron.Catalog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class CatalogController {
    private AppState app;
    private Catalog catalog;

    public CatalogController(AppState app, Catalog catalog) {
        this.app = app;
        this.catalog = catalog;
        initCatalogItems();
    }

    private void initCatalogItems() {
        try {
            // Load books from the database
            List<Book> books = Book.findAll();
            for (Book book : books) {
                addItemToCatalog(book.getTitle(), "/catalog/book.png");  
            }

            // Load videos from the database
            List<Video> videos = Video.findAll();
            for (Video video : videos) {
                addItemToCatalog(video.getTitle(), "/catalog/video.png");  
            }

            // Load journals from the database
            List<Journal> journals = Journal.findAll();
            for (Journal journal : journals) {
                addItemToCatalog(journal.getTitle(), "/catalog/journal.png");  
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, possibly by showing an error message to the user
        }
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

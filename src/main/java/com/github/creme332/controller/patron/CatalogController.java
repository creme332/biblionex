package com.github.creme332.controller.patron;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.Video;
import com.github.creme332.view.patron.Catalog;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class CatalogController {
    private AppState app;
    private Catalog catalog;
    private boolean updatingSearchField = false; // Flag to disable document listener temporarily

    public CatalogController(AppState app, Catalog catalog) {
        this.app = app;
        this.catalog = catalog;

        // Add focus listeners for placeholder text
        catalog.getSearchField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (catalog.getSearchField().getText().equals("Search by Material Title")) {
                    updatingSearchField = true;
                    catalog.getSearchField().setText("");
                    catalog.getSearchField().setForeground(Color.WHITE);
                    updatingSearchField = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (catalog.getSearchField().getText().isEmpty()) {
                    updatingSearchField = true;
                    catalog.getSearchField().setText("Search by Material Title");
                    catalog.getSearchField().setForeground(Color.GRAY);
                    updatingSearchField = false;
                    searchMaterials(); // Trigger search to show all items when the field is empty
                }
            }
        });

        // Add action listeners for search functionality
        catalog.getSearchField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMaterials();
            }
        });

        catalog.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMaterials();
            }
        });

        // Add document listener for real-time filtering
        catalog.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!updatingSearchField) {
                    searchMaterials();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!updatingSearchField) {
                    searchMaterials();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!updatingSearchField) {
                    searchMaterials();
                }
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

    // Implement search functionality in the controller
    private void searchMaterials() {
        String searchText = catalog.getSearchField().getText().trim();
        if (searchText.isEmpty() || searchText.equals("Search by Material Title")) {
            searchText = "";
        }
        catalog.filterItems(searchText);
    }
}
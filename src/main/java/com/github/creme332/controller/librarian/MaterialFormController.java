package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.view.librarian.MaterialForm;
import com.github.creme332.controller.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaterialFormController {
    MaterialForm materialForm;
    AppState app;

    public MaterialFormController(AppState app, MaterialForm materialForm) {
        this.materialForm = materialForm;
        this.app = app;
        initController();
    }

    private void initController() {
        materialForm.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) materialForm.getMaterialTypeDropdown().getSelectedItem();
                switch (selectedType) {
                    case "Book":
                        handleBookSubmission();
                        break;
                    case "Journal":
                        handleJournalSubmission();
                        break;
                    case "Video":
                        handleVideoSubmission();
                        break;
                }
            }
        });

        materialForm.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));

        materialForm.getMaterialTypeDropdown().addActionListener(e -> {
            CardLayout cl = (CardLayout) (materialForm.getFormPanel().getLayout());
            String selectedItem = (String) materialForm.getMaterialTypeDropdown().getSelectedItem();
            cl.show(materialForm.getFormPanel(), selectedItem);
        });
    }

    private void handleBookSubmission() {
        // Implement the logic to handle book submission
        System.out.println("Book submitted");
    }

    private void handleJournalSubmission() {
        // Implement the logic to handle journal submission
        System.out.println("Journal submitted");
    }

    private void handleVideoSubmission() {
        // Implement the logic to handle video submission
        System.out.println("Video submitted");
    }
}

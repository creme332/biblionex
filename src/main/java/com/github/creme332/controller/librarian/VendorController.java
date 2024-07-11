package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Vendor;
import com.github.creme332.view.librarian.VendorForm;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VendorController {
    private VendorForm vendorForm;
    AppState app;

    public VendorController(AppState app, VendorForm vendorForm) {
        this.vendorForm = vendorForm;
        this.app = app;

        this.vendorForm.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveVendor();
            }
        });

        this.vendorForm.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // back button functionality
            }
        });
    }

    private void saveVendor() {
        String email = vendorForm.getEmailField().getText();
        String name = vendorForm.getNameField().getText();
        String contactPerson = vendorForm.getContactPersonField().getText();

        Vendor vendor = new Vendor(0, email, name, contactPerson);
        try {
            Vendor.save(vendor);
            JOptionPane.showMessageDialog(vendorForm, "Vendor saved successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            vendorForm.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vendorForm, "Failed to save vendor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

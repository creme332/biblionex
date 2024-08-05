package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.model.Loan;
import com.github.creme332.utils.StringUtil;
import com.github.creme332.view.librarian.OverdueLoansPage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OverdueController {
    private OverdueLoansPage view;
    private List<Loan> allOverdueLoans = new ArrayList<>();

    public OverdueController(AppState app, OverdueLoansPage view) {
        this.view = view;

        // Handle back button action
        view.getBackButton().addActionListener(e -> app.setCurrentScreen(app.getPreviousScreen()));

        view.getSearchButton().addActionListener(e -> {
            view.updateTableModel(filterByBarcode());
        });

        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    allOverdueLoans = Loan.findAllOverdue();
                    view.updateTableModel(allOverdueLoans);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        };
        th.start();
    }

    private List<Loan> filterByBarcode() {
        String barcodeText = view.getBarcode();

        if (barcodeText.isEmpty()) {
            view.updateTableModel(allOverdueLoans);
            // Reload all overdue loans if the search field is empty
            return allOverdueLoans;
        }

        List<Loan> filtered = new ArrayList<>();
        for (Loan loan : allOverdueLoans) {
            int similarityIndex = StringUtil.levenshteinDistance(barcodeText, "" + loan.getLoanId());
            if (similarityIndex < 5)
                filtered.add(loan);
        }

        return filtered;
    }

}

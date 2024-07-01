package com.github.creme332.controller.patron;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import com.github.creme332.view.patron.Loan;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Fine;
import com.github.creme332.model.Fine.LoanFineData;

public class LoanController {
    private Loan loan;
    private AppState app;

    public LoanController(AppState app, Loan loan) {
        this.loan = loan;
        this.app = app;
        loan.addLoanActionListener(e -> handlePayAction(Integer.parseInt(e.getActionCommand())));
        loadData();
    }

    public void loadData() {
        try {
            List<LoanFineData> records = Fine.findAllLoanFineRecords();
            loan.setLoanFineRecords(records);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loan, "Error loading data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handlePayAction(int loanId) {
        try {
            Fine.deleteByLoanId(loanId);
            loadData(); // Reload the data to refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loan, "Error processing payment", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

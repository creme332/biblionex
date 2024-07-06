package com.github.creme332.controller.patron;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import com.github.creme332.view.patron.LoanPage;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Loan;
import com.github.creme332.model.Patron;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;

/**
 * Controller for patron loan page.
 */
public class LoanController implements PropertyChangeListener {
    private LoanPage loanView;
    /**
     * Logged in patron. It is initialized when the loggedInUser in AppState
     * changes.
     */
    Patron patron;

    public LoanController(AppState app, LoanPage loanView) {
        this.loanView = loanView;
        app.addPropertyChangeListener(this);
        loanView.addLoanActionListener(e -> handlePayment(Integer.parseInt(e.getActionCommand())));
    }

    public void displayLoans() {
        List<Loan> loans;
        try {
            loans = patron.getLoans();
            loanView.updateTableModel(loans);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loanView,
                    "Unable to fetch active loans from database. Please try again later.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void handlePayment(int loanId) {
        Loan currentLoan = null;

        // Fetch loan details
        try {
            currentLoan = Loan.findById(loanId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // Check if payment is needed
        if (currentLoan.getAmountDue() <= 0) {
            JOptionPane.showMessageDialog(loanView, "No payment required for this loan.", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean paymentSuccessful = patron.payFine(currentLoan);
        if (paymentSuccessful) {
            JOptionPane.showMessageDialog(loanView, "Payment Successful.", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(loanView, "Payment is not possible yet.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("loggedInUser")) {

            // ignore event if logged in user is not a patron
            User newUser = (User) evt.getNewValue();
            if (newUser == null
                    || newUser.getUserType() != UserType.PATRON)
                return;

            // initialize patron
            patron = (Patron) newUser;

            // display his loans
            displayLoans();
        }
    }
}

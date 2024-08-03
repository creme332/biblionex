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
import com.github.creme332.utils.exception.UserVisibleException;

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

        loanView.getPayButtonEditor().handlePayment(e -> handlePayment(loanView.getSelectedLoanID()));

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
            if (currentLoan == null) {
                JOptionPane.showMessageDialog(loanView, "Loan not found.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
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

        try {
            patron.payFine(currentLoan);
            JOptionPane.showMessageDialog(loanView, "Payment Successful.", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (UserVisibleException e) {
            JOptionPane.showMessageDialog(loanView, e.getMessage(), "Payment rejected",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loanView, "An error occurred during payment.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Refresh the loan view to update the status and button
        displayLoans();
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

package com.github.creme332.controller.patron;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import com.github.creme332.view.patron.LoanPage;
import com.github.creme332.controller.Screen;
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

    AppState app;

    public LoanController(AppState app, LoanPage loanView) {
        this.app = app;
        this.loanView = loanView;
        app.addPropertyChangeListener(this);

        loanView.getPayButtonEditor().handlePayment(e -> handlePayment(loanView.getSelectedLoanID()));

    }

    public void refreshLoanTable() {
        try {
            List<Loan> loans = patron.getLoans();
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
                JOptionPane.showMessageDialog(loanView, String.format(
                        "Loan ID %d not found. Ensure that you have properly selected a row before clicking on the pay button.",
                        loanId), "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // Check if payment is needed
        if (currentLoan.getAmountDue() <= 0) {
            JOptionPane.showMessageDialog(loanView, "No payment required for loan " + loanId, "Message",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            patron.payFine(currentLoan);
            JOptionPane.showMessageDialog(loanView, "Payment Successful.", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
            refreshLoanTable();
        } catch (UserVisibleException e) {
            JOptionPane.showMessageDialog(loanView, e.getMessage(), "Payment rejected",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loanView, "An error occurred during payment.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        // update identity of patron when log status changes
        if (propertyName.equals("loggedInUser")) {

            // ignore event if logged in user is not a patron
            User newUser = app.getLoggedInUser();
            if (newUser == null
                    || newUser.getUserType() != UserType.PATRON)
                return;

            // update patron
            patron = (Patron) newUser;
        }

        // refresh loan table each time screen whenever page is accessed
        if (propertyName.equals("currentScreen") && (Screen) evt.getNewValue() == Screen.PATRON_LOAN_SCREEN) {
            refreshLoanTable();
        }
    }
}

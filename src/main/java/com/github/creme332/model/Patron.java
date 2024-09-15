package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.creme332.utils.DatabaseConnection;
import com.github.creme332.utils.PasswordAuthentication;
import com.github.creme332.utils.exception.UserVisibleException;

public class Patron extends User {
    private Date registrationDate;
    private String creditCardNo;
    private Date birthDate;

    public Patron(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo, String creditCardNo, Date birthDate, Date registrationDate) {
        super(email, password, userId, address, firstName, lastName, phoneNo);
        userType = UserType.PATRON;
        this.creditCardNo = creditCardNo;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

    public Patron(String email, String password, String address, String firstName, String lastName,
            String phoneNo, String creditCardNo, Date birthDate) {
        super(email, password, address, firstName, lastName, phoneNo);
        userType = UserType.PATRON;
        this.creditCardNo = creditCardNo;
        this.birthDate = birthDate;
        this.registrationDate = new Date();
    }

    /**
     * Performs payment for a particular loan. Payment amount is determined by
     * getAmountDue() in the loan class.
     * 
     * @param loan
     * @return Newly created fine if payment was successful.
     * @throws SQLException
     */
    public Fine payFine(Loan loan) throws UserVisibleException, SQLException {
        // check if a fine has already been paid
        if (loan.getFinesPaid() > 0) {
            throw new UserVisibleException("A fine has already been paid for this loan.");
        }

        // create a new fine
        Fine newFine = new Fine(userId, loan.getLoanId(), new Date(), loan.getAmountDue());
        try {
            Fine.save(newFine);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserVisibleException("Database error");
        }
        return newFine;
    }

    /**
     * 
     * @return A list of active loans for patron. A loan is active if the material
     *         loaned has not been returned yet.
     * @throws SQLException
     */
    public List<Loan> getActiveLoans() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Loan> activeLoans = new ArrayList<>();

        String query = """
                SELECT * from loan
                WHERE patron_id = ?
                AND return_date is NULL
                """;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("checkout_librarian_id"),
                        resultSet.getInt("checkin_librarian_id"),
                        resultSet.getTimestamp("issue_date"),
                        resultSet.getTimestamp("return_date"),
                        resultSet.getTimestamp("due_date"),
                        resultSet.getInt("renewal_count"));
                activeLoans.add(loan);
            }
        }
        return activeLoans;
    }

    /**
     * 
     * @return A list of all loans for patron sorted in chronological order of issue
     *         date.
     * @throws SQLException
     */
    public List<Loan> getLoans() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Loan> allLoans = new ArrayList<>();

        String query = """
                SELECT * from loan
                WHERE patron_id = ?
                ORDER BY issue_date DESC
                """;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("checkout_librarian_id"),
                        resultSet.getInt("checkin_librarian_id"),
                        resultSet.getTimestamp("issue_date"),
                        resultSet.getTimestamp("return_date"),
                        resultSet.getTimestamp("due_date"),
                        resultSet.getInt("renewal_count"));
                allLoans.add(loan);
            }
        }
        return allLoans;
    }

    /**
     * 
     * @return A list of overdue loans for patron. A loan is overdue if the material
     *         loaned has not been returned yet and the due date is less than the
     *         current date.
     */
    public List<Loan> getOverdueLoans() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Loan> overdueLoans = new ArrayList<>();
    
        String query = """
                SELECT * from loan
                WHERE patron_id = ?
                AND return_date is NULL
                AND due_date < CURRENT_DATE
                """;
    
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("loan_id"),
                        resultSet.getInt("patron_id"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("checkout_librarian_id"),
                        resultSet.getInt("checkin_librarian_id"),
                        resultSet.getTimestamp("issue_date"),
                        resultSet.getTimestamp("return_date"),
                        resultSet.getTimestamp("due_date"),
                        resultSet.getInt("renewal_count"));
                overdueLoans.add(loan);
            }
        }
        return overdueLoans;
    }
    
    /**
     * Saves a patron to database. patron ID and registration date are automatically
     * set by database.
     * 
     * @param patron
     * @throws SQLException
     * @throws UserVisibleException
     */
    public static void save(Patron patron) throws SQLException, UserVisibleException {

        if (patron.getEmail().isEmpty()) {
            throw new UserVisibleException("Email cannot be empty");
        }
        if (patron.getFirstName().isEmpty()) {
            throw new UserVisibleException("First name cannot be empty");
        }
        if (patron.getLastName().isEmpty()) {
            throw new UserVisibleException("Last name cannot be empty");
        }
        if (patron.getPhoneNo().isEmpty()) {
            throw new UserVisibleException("Phone number cannot be empty");
        }
        if (patron.getAddress().isEmpty()) {
            throw new UserVisibleException("Address cannot be empty");
        }
        if (patron.getPassword().isEmpty()) {
            throw new UserVisibleException("Password cannot be empty");
        }

        if (!User.validateEmail(patron.getEmail())) {
            throw new UserVisibleException("Email already exists");
        }

        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                INSERT INTO patron (
                    address,
                    password,
                    last_name,
                    first_name,
                    phone_no,
                    email,
                    credit_card_no,
                    birth_date
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                 """;

        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String hashedPassword = passwordAuthentication.hash(patron.getPassword().toCharArray());

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, patron.getAddress());
            preparedStatement.setString(2, hashedPassword); // Save hashed password
            preparedStatement.setString(3, patron.getLastName());
            preparedStatement.setString(4, patron.getFirstName());
            preparedStatement.setString(5, patron.getPhoneNo());
            preparedStatement.setString(6, patron.getEmail());
            preparedStatement.setString(7, patron.getCreditCardNo());
            if (patron.getBirthDate() != null) {
                preparedStatement.setDate(8, new java.sql.Date(patron.getBirthDate().getTime()));
            } else {
                preparedStatement.setNull(8, java.sql.Types.DATE);
            }
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Updates all patron attributes except patron ID, password, registration date.
     * 
     * @throws SQLException
     */
    public static void update(Patron patron) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                UPDATE patron
                SET address = ?,
                    last_name = ?,
                    first_name = ?,
                    phone_no = ?,
                    email = ?,
                    birth_date = ?,
                    credit_card_no = ?
                WHERE patron_id = ?
                """;

        try (PreparedStatement updatePatron = conn.prepareStatement(query)) {
            updatePatron.setString(1, patron.getAddress());
            updatePatron.setString(2, patron.getLastName());
            updatePatron.setString(3, patron.getFirstName());
            updatePatron.setString(4, patron.getPhoneNo());
            updatePatron.setString(5, patron.getEmail());
            if (patron.getBirthDate() != null) {
                updatePatron.setDate(6, new java.sql.Date(patron.getBirthDate().getTime()));
            } else {
                updatePatron.setNull(6, java.sql.Types.DATE);
            }
            updatePatron.setString(7, patron.getCreditCardNo());
            updatePatron.setInt(8, patron.getUserId());
            updatePatron.executeUpdate();
        }
    }

    public static void delete(int patronId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM patron WHERE patron_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, patronId);
            preparedStatement.executeUpdate();
        }
    }

    public static Patron findById(int patronId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        Patron patron = null;
        String query = "SELECT * FROM patron WHERE patron_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, patronId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patron = new Patron(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("patron_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("credit_card_no"),
                        resultSet.getDate("birth_date"),
                        resultSet.getTimestamp("registration_date"));
            }
        }
        return patron;
    }

    public static Patron findByEmail(String email) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();

        Patron patron = null;
        String query = "SELECT * FROM patron WHERE email = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patron = new Patron(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("patron_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("credit_card_no"),
                        resultSet.getDate("birth_date"),
                        resultSet.getTimestamp("registration_date"));
            }
        }
        return patron;
    }

    public static List<Patron> findAll() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        List<Patron> patrons = new ArrayList<>();
        String query = "SELECT * FROM patron";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Patron patron = new Patron(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("patron_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("credit_card_no"),
                        resultSet.getDate("birth_date"),
                        resultSet.getTimestamp("registration_date"));
                patrons.add(patron);
            }
        }
        return patrons;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setRegistrationDate(Date newDate) {
        this.registrationDate = newDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "Patron{" +
                "patron_id=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", last_name='" + lastName + '\'' +
                ", first_name='" + firstName + '\'' +
                ", phone_no='" + phoneNo + '\'' +
                ", address='" + address + '\'' +
                ", birth_date='" + birthDate + '\'' +
                ", registration_date='" + registrationDate + '\'' +
                ", credit_card_no='" + creditCardNo + '\'' +
                '}';
    }

    public double getTotalFinePaid() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT SUM(amount) as total FROM fine where patron_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }
        }
        return 0;
    }
}

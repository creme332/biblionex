package com.github.creme332.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import com.github.creme332.utils.DatabaseConnection;
import com.github.creme332.utils.PasswordAuthentication;
import com.github.creme332.utils.exception.UserVisibleException;

public class Librarian extends User {
    private String role;

    public Librarian(String email, String password, int userId, String address, String firstName, String lastName,
            String phoneNo, String role) {
        super(email, password, userId, address, firstName, lastName, phoneNo);
        this.role = role;
        this.userType = UserType.LIBRARIAN;
    }

    /**
     * Constructor for creating a new librarian when ID is unknown.
     * 
     * @param email
     * @param password
     * @param address
     * @param firstName
     * @param lastName
     * @param phoneNo
     * @param role
     */
    public Librarian(String email, String password, String address, String firstName, String lastName,
            String phoneNo, String role) {
        super(email, password, address, firstName, lastName, phoneNo);
        this.role = role;
        this.userType = UserType.LIBRARIAN;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void renewLoan(Loan loan) throws SQLException {
        if (loan.getRenewalCount() >= Loan.RENEWAL_LIMIT) {
            throw new IllegalArgumentException("This loan has reached the maximum renewal limit.");
        }

        Date newDueDate = new Date(loan.getDueDate().getTime() + (7L * 24 * 60 * 60 * 1000)); // Add 7 days
        loan.setDueDate(newDueDate);
        loan.setRenewalCount(loan.getRenewalCount() + 1);

        // Update the loan in the database
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE loan SET due_date = ?, renewal_count = ? WHERE loan_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(loan.getDueDate().getTime()));
            preparedStatement.setInt(2, loan.getRenewalCount());
            preparedStatement.setInt(3, loan.getLoanId());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Performs checkout of a material by creating a new loan and saving it to
     * database.
     * 
     * @param patronId ID of patron
     * @param barcode  Barcode of material being checked out
     * @return Newly created loan
     * @throws SQLException
     */
    public Loan checkOut(int patronId, int barcode) throws UserVisibleException, SQLException {
        // Check if patronId and barcode are valid
        if (Patron.findById(patronId) == null) {
            throw new UserVisibleException("Patron does not exist.");
        }

        MaterialCopy material = MaterialCopy.findById(barcode);
        if (material == null) {
            throw new UserVisibleException("Barcode not found.");
        }

        // Check if material is not already being loaned
        if (material.onLoan()) {
            throw new UserVisibleException("Cannot checkout a material that is currently being loaned to another patron.");
        }

        // Get the current date
        Date currentDate = new Date();

        // Create a Calendar object and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Add 2 months to the current date
        calendar.add(Calendar.MONTH, 2);

        // Get the updated date
        Date dueDate = calendar.getTime();

        // create a new loan object
        Loan loan = new Loan(patronId, barcode, userId, dueDate);

        // save loan to database
        final Connection conn = DatabaseConnection.getConnection();
        String query = """
                INSERT INTO loan (patron_id, barcode, checkout_librarian_id,
                                    issue_date, due_date)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement createLoan = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createLoan.setInt(1, loan.getPatronId());
            createLoan.setInt(2, loan.getBarcode());
            createLoan.setInt(3, loan.getCheckoutLibrarianId());
            createLoan.setTimestamp(4, new java.sql.Timestamp(loan.getIssueDate().getTime()));
            createLoan.setDate(5, new java.sql.Date(loan.getDueDate().getTime()));
            int rowsAffected = createLoan.executeUpdate();

            if (rowsAffected == 0) {
                throw new UserVisibleException("Could not insert loan" + loan.toString());
            }

            // update loan ID of object
            ResultSet rs = createLoan.getGeneratedKeys();
            if (rs.next()) {
                loan.setLoanId(rs.getInt(1));
            }
        }

        return loan;
    }

    public void checkIn(Loan loan) throws SQLException {
        loan.setReturnDate(new Date()); // Set the return date to the current date
        loan.setCheckinLibrarianId(this.getUserId());

        // Update the loan in the database
        final Connection conn = DatabaseConnection.getConnection();
        String query = "UPDATE loan SET return_date = ?, checkin_librarian_id = ? WHERE loan_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(loan.getReturnDate().getTime()));
            preparedStatement.setInt(2, loan.getCheckinLibrarianId());
            preparedStatement.setInt(3, loan.getLoanId());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Save a new record for a librarian. Librarian ID is generated by database.
     * 
     * @param librarian
     * @throws SQLException
     */
    public static void save(Librarian librarian) throws SQLException {
        if (!User.validateEmail(librarian.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        final Connection conn = DatabaseConnection.getConnection();
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String hashedPassword = passwordAuthentication.hash(librarian.getPassword().toCharArray());

        String query = "INSERT INTO librarian (address, password, last_name, first_name, phone_no, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, librarian.getAddress());
            preparedStatement.setString(2, hashedPassword); // Save hashed password
            preparedStatement.setString(3, librarian.getLastName());
            preparedStatement.setString(4, librarian.getFirstName());
            preparedStatement.setString(5, librarian.getPhoneNo());
            preparedStatement.setString(6, librarian.getEmail());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted != 1) {
                throw new SQLException("Librarian record was not inserted.");
            }
        }
    }

    /**
     * Updates all attributes except password and librarian ID.
     * 
     * @param librarian
     * @throws SQLException
     */
    public static void update(Librarian librarian) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();

        String query = "UPDATE librarian SET address = ?, last_name = ?, first_name = ?, phone_no = ?, email = ?, role = ? WHERE librarian_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, librarian.getAddress());
            preparedStatement.setString(2, librarian.getLastName());
            preparedStatement.setString(3, librarian.getFirstName());
            preparedStatement.setString(4, librarian.getPhoneNo());
            preparedStatement.setString(5, librarian.getEmail());
            preparedStatement.setString(6, librarian.getRole());
            preparedStatement.setInt(7, librarian.getUserId());
            preparedStatement.executeUpdate();
        }
    }

    public static void delete(int librarianId) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();

        String query = "DELETE FROM librarian WHERE librarian_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, librarianId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted != 1) {
                throw new SQLException("Librarian record was not deleted.");
            }
        }
    }

    public static Librarian findById(int id) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();

        Librarian librarian = null;
        String query = "SELECT * FROM librarian WHERE librarian_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                librarian = new Librarian(resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("librarian_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("role"));
            }
        }
        return librarian;
    }

    public static List<Librarian> findAll() throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();

        List<Librarian> librarians = new ArrayList<>();
        String query = "SELECT * FROM librarian";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Librarian librarian = new Librarian(resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("librarian_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("role"));

                librarians.add(librarian);
            }
        }

        return librarians;
    }

    public static Librarian findByEmail(String email) throws SQLException {
        final Connection conn = DatabaseConnection.getConnection();

        Librarian librarian = null;
        String query = "SELECT * FROM librarian WHERE email = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                librarian = new Librarian(resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("librarian_id"),
                        resultSet.getString("address"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_no"),
                        resultSet.getString("role"));
            }
        }

        return librarian;
    }

    @Override
    public String toString() {
        return "Librarian{" +

                "librarian_id=" + userId +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", last_name='" + lastName + '\'' +
                ", first_name='" + firstName + '\'' +
                ", phone_no='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

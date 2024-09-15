package com.github.creme332.model;

import com.github.creme332.utils.DatabaseConnection;
import com.github.creme332.utils.exception.UserVisibleException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journal extends Material {
    private String issn;
    private String website;
    private JournalFrequency frequency;
    private Date startDate;

    /**
     * Constructs a new Journal.
     *
     * @param materialId     the ID of the material
     * @param publisherId    the ID of the publisher
     * @param description    the description of the journal
     * @param imageUrl       the URL of the journal's image
     * @param ageRestriction the age restriction of the journal
     * @param title          the title of the journal
     * @param issn           the ISSN of the journal
     * @param website        the website of the journal
     * @param frequency      the publication frequency of the journal
     * @param startDate      the start date of the journal
     */
    public Journal(int materialId, int publisherId, String description, String imageUrl, int ageRestriction,
            String title, String issn, String website, JournalFrequency frequency, Date startDate) {
        super(materialId, publisherId, description, imageUrl, ageRestriction, MaterialType.JOURNAL, title);
        this.issn = issn;
        this.website = website;
        this.frequency = frequency;
        this.startDate = startDate;
    }

    /**
     * Constructs a new Journal without a material ID.
     *
     * @param publisherId    the ID of the publisher
     * @param description    the description of the journal
     * @param imageUrl       the URL of the journal's image
     * @param ageRestriction the age restriction of the journal
     * @param title          the title of the journal
     * @param issn           the ISSN of the journal
     * @param website        the website of the journal
     * @param frequency      the publication frequency of the journal
     * @param startDate      the start date of the journal
     */
    public Journal(int publisherId, String description, String imageUrl, int ageRestriction, String title,
            String issn, String website, JournalFrequency frequency, Date startDate) {
        super(publisherId, description, imageUrl, ageRestriction, MaterialType.JOURNAL, title);
        this.issn = issn;
        this.website = website;
        this.frequency = frequency;
        this.startDate = startDate;
    }

    @Override
    public void validate() throws UserVisibleException {
        super.validate();
        if (issn == null || issn.isBlank()) {
            throw new UserVisibleException("ISSN cannot be empty");
        }
        if (website == null || website.isBlank()) {
            throw new UserVisibleException("Website cannot be empty");
        }
        if (frequency == null) {
            throw new UserVisibleException("Journal frequency cannot be empty");
        }
        if (startDate == null) {
            throw new UserVisibleException("Start date cannot be empty");
        }
    }

    /**
     * Saves a new journal to the database.
     *
     * @param journal the journal to be saved
     * @throws SQLException if a database access error occurs
     */
    public static void save(Journal journal) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String materialQuery = """
                INSERT INTO material (publisher_id, description, image_url, age_restriction, type, title)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        connection.setAutoCommit(false);

        try (PreparedStatement createMaterial = connection.prepareStatement(materialQuery,
                Statement.RETURN_GENERATED_KEYS)) {

            createMaterial.setInt(1, journal.getPublisherId());
            createMaterial.setString(2, journal.getDescription());
            createMaterial.setString(3, journal.getImageUrl());
            createMaterial.setInt(4, journal.getAgeRestriction());
            createMaterial.setString(5, journal.getType().toString());
            createMaterial.setString(6, journal.getTitle());
            createMaterial.executeUpdate();

            try (ResultSet generatedKeys = createMaterial.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    journal.setMaterialId(generatedKeys.getInt(1));
                }
            }
        }

        String journalQuery = """
                INSERT INTO journal (material_id, issn, website, frequency, start_date)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement createJournal = connection.prepareStatement(journalQuery)) {
            createJournal.setInt(1, journal.getMaterialId());
            createJournal.setString(2, journal.getIssn());
            createJournal.setString(3, journal.getWebsite());
            createJournal.setString(4, journal.getFrequency().toString());
            createJournal.setDate(5, new java.sql.Date(journal.getStartDate().getTime()));
            createJournal.executeUpdate();
        }

        connection.commit();
    }

    /**
     * Retrieves a journal by its ID.
     *
     * @param id the ID of the journal
     * @return the journal, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public static Journal findById(int id) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = """
                SELECT m.*, j.issn, j.website, j.frequency, j.start_date
                FROM material m
                JOIN journal j ON m.material_id = j.material_id
                WHERE m.material_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Journal(
                            resultSet.getInt("material_id"),
                            resultSet.getInt("publisher_id"),
                            resultSet.getString("description"),
                            resultSet.getString("image_url"),
                            resultSet.getInt("age_restriction"),
                            resultSet.getString("title"),
                            resultSet.getString("issn"),
                            resultSet.getString("website"),
                            JournalFrequency.valueOf(resultSet.getString("frequency")),
                            resultSet.getDate("start_date"));
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all journals from the database.
     *
     * @return a list of all journals
     * @throws SQLException if a database access error occurs
     */
    public static List<Journal> findAll() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = """
                SELECT m.*, j.issn, j.website, j.frequency, j.start_date
                FROM material m
                JOIN journal j ON m.material_id = j.material_id
                """;

        List<Journal> journals = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                journals.add(new Journal(
                        resultSet.getInt("material_id"),
                        resultSet.getInt("publisher_id"),
                        resultSet.getString("description"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("age_restriction"),
                        resultSet.getString("title"),
                        resultSet.getString("issn"),
                        resultSet.getString("website"),
                        JournalFrequency.fromString(resultSet.getString("frequency")),
                        resultSet.getDate("start_date")));
            }
        }
        return journals;
    }

    /**
     * Updates an existing journal in the database.
     *
     * @param journal the journal to be updated
     * @throws SQLException if a database access error occurs
     */
    public static void update(Journal journal) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String materialQuery = """
                UPDATE material SET publisher_id = ?, description = ?, image_url = ?, age_restriction = ?, type = ?, title = ?
                WHERE material_id = ?
                """;

        connection.setAutoCommit(false);

        try (PreparedStatement updateMaterial = connection.prepareStatement(materialQuery)) {
            updateMaterial.setInt(1, journal.getPublisherId());
            updateMaterial.setString(2, journal.getDescription());
            updateMaterial.setString(3, journal.getImageUrl());
            updateMaterial.setInt(4, journal.getAgeRestriction());
            updateMaterial.setString(5, journal.getType().toString());
            updateMaterial.setString(6, journal.getTitle());
            updateMaterial.setInt(7, journal.getMaterialId());
            updateMaterial.executeUpdate();
        }

        String journalQuery = """
                UPDATE journal SET issn = ?, website = ?, frequency = ?, start_date = ?
                WHERE material_id = ?
                """;

        try (PreparedStatement updateJournal = connection.prepareStatement(journalQuery)) {
            updateJournal.setString(1, journal.getIssn());
            updateJournal.setString(2, journal.getWebsite());
            updateJournal.setString(3, journal.getFrequency().toString());
            updateJournal.setDate(4, new java.sql.Date(journal.getStartDate().getTime()));
            updateJournal.setInt(5, journal.getMaterialId());
            updateJournal.executeUpdate();
        }

        connection.commit();
    }

    /**
     * Deletes a journal from the database by its ID.
     *
     * @param id the ID of the journal to be deleted
     * @throws SQLException if a database access error occurs
     */
    public static void delete(Journal journal) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String journalQuery = "DELETE FROM journal WHERE material_id = ?";
        String materialQuery = "DELETE FROM material WHERE material_id = ?";

        connection.setAutoCommit(false);

        try (PreparedStatement deleteJournal = connection.prepareStatement(journalQuery);
                PreparedStatement deleteMaterial = connection.prepareStatement(materialQuery)) {
            deleteJournal.setInt(1, journal.getMaterialId());
            deleteJournal.executeUpdate();

            deleteMaterial.setInt(1, journal.getMaterialId());
            deleteMaterial.executeUpdate();
        }

        connection.commit();
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public JournalFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(JournalFrequency frequency) {
        this.frequency = frequency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "materialId=" + getMaterialId() +
                ", publisherId=" + getPublisherId() +
                ", description='" + getDescription() + '\'' +
                ", imageUrl='" + getImageUrl() + '\'' +
                ", ageRestriction=" + getAgeRestriction() +
                ", title='" + getTitle() + '\'' +
                ", issn='" + issn + '\'' +
                ", website='" + website + '\'' +
                ", frequency=" + frequency +
                ", startDate=" + startDate +
                '}';
    }
}
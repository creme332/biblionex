package com.github.creme332.model;

import com.github.creme332.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a video material in the library system.
 */
public class Video extends Material {
    private String language;
    private int duration;
    private int rating;
    private String format;

    /**
     * Constructs a new Video.
     *
     * @param materialId     the ID of the material
     * @param publisherId    the ID of the publisher
     * @param description    the description of the video
     * @param imageUrl       the URL of the video's image
     * @param ageRestriction the age restriction of the video
     * @param title          the title of the video
     * @param rating         the rating of the video
     * @param duration       the duration of the video in minutes
     * @param language       the language of the video
     * @param format         the format of the video
     */
    public Video(int materialId, int publisherId, String description, String imageUrl, int ageRestriction,
            String title, String language, int duration, int rating, String format) {
        super(materialId, publisherId, description, imageUrl, ageRestriction, MaterialType.VIDEO, title);
        this.language = language;
        this.duration = duration;
        this.rating = rating;
        this.format = format;
    }

    /**
     * Constructs a new Video without a material ID.
     *
     * @param publisherId    the ID of the publisher
     * @param description    the description of the video
     * @param imageUrl       the URL of the video's image
     * @param ageRestriction the age restriction of the video
     * @param title          the title of the video
     * @param rating         the rating of the video
     * @param duration       the duration of the video in minutes
     * @param language       the language of the video
     * @param format         the format of the video
     */
    public Video(int publisherId, String description, String imageUrl, int ageRestriction, String title,
            String language, int duration, int rating, String format) {
        super(publisherId, description, imageUrl, ageRestriction, MaterialType.VIDEO, title);
        this.language = language;
        this.duration = duration;
        this.rating = rating;
        this.format = format;
    }

    /**
     * Saves a new video to the database.
     *
     * @param video the video to be saved
     * @throws SQLException if a database access error occurs
     */
    public static void save(Video video) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String materialQuery = """
                INSERT INTO material (publisher_id, description, image_url, age_restriction, type, title)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        connection.setAutoCommit(false);

        try (PreparedStatement createMaterial = connection.prepareStatement(materialQuery,
                Statement.RETURN_GENERATED_KEYS)) {

            createMaterial.setInt(1, video.getPublisherId());
            createMaterial.setString(2, video.getDescription());
            createMaterial.setString(3, video.getImageUrl());
            createMaterial.setInt(4, video.getAgeRestriction());
            createMaterial.setString(5, video.getType().toString());
            createMaterial.setString(6, video.getTitle());
            createMaterial.executeUpdate();

            try (ResultSet generatedKeys = createMaterial.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    video.setMaterialId(generatedKeys.getInt(1));
                }
            }
        }

        String videoQuery = """
                INSERT INTO video (material_id, language, duration, rating, format)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement createVideo = connection.prepareStatement(videoQuery)) {
            createVideo.setInt(1, video.getMaterialId());
            createVideo.setString(2, video.getLanguage());
            createVideo.setInt(3, video.getDuration());
            createVideo.setInt(4, video.getRating());
            createVideo.setString(5, video.getFormat());
            createVideo.executeUpdate();
        }

        connection.commit();
    }

    /**
     * Retrieves a video by its ID.
     *
     * @param id the ID of the video
     * @return the video, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public static Video findByID(int id) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = """
                SELECT m.*, v.language, v.duration, v.rating, v.format
                FROM material m
                JOIN video v ON m.material_id = v.material_id
                WHERE m.material_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Video(
                            resultSet.getInt("material_id"),
                            resultSet.getInt("publisher_id"),
                            resultSet.getString("description"),
                            resultSet.getString("image_url"),
                            resultSet.getInt("age_restriction"),
                            resultSet.getString("title"),
                            resultSet.getString("language"),
                            resultSet.getInt("duration"),
                            resultSet.getInt("rating"),
                            resultSet.getString("format"));
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all videos from the database.
     *
     * @return a list of all videos
     * @throws SQLException if a database access error occurs
     */
    public static List<Video> findAll() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = """
                SELECT m.*, v.language, v.duration, v.rating, v.format
                FROM material m
                JOIN video v ON m.material_id = v.material_id
                """;

        List<Video> videos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                videos.add(new Video(
                        resultSet.getInt("material_id"),
                        resultSet.getInt("publisher_id"),
                        resultSet.getString("description"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("age_restriction"),
                        resultSet.getString("title"),
                        resultSet.getString("language"),
                        resultSet.getInt("duration"),
                        resultSet.getInt("rating"),
                        resultSet.getString("format")));
            }
        }
        return videos;
    }

    /**
     * Updates an existing video in the database.
     *
     * @param video the video to be updated
     * @throws SQLException if a database access error occurs
     */
    public static void update(Video video) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String materialQuery = """
                UPDATE material SET publisher_id = ?, description = ?, image_url = ?, age_restriction = ?, type = ?, title = ?
                WHERE material_id = ?
                """;

        connection.setAutoCommit(false);

        try (PreparedStatement updateMaterial = connection.prepareStatement(materialQuery)) {
            updateMaterial.setInt(1, video.getPublisherId());
            updateMaterial.setString(2, video.getDescription());
            updateMaterial.setString(3, video.getImageUrl());
            updateMaterial.setInt(4, video.getAgeRestriction());
            updateMaterial.setString(5, video.getType().toString());
            updateMaterial.setString(6, video.getTitle());
            updateMaterial.setInt(7, video.getMaterialId());
            updateMaterial.executeUpdate();
        }

        String videoQuery = """
                UPDATE video SET language = ?, duration = ?, rating = ?, format = ?
                WHERE material_id = ?
                """;

        try (PreparedStatement updateVideo = connection.prepareStatement(videoQuery)) {
            updateVideo.setString(1, video.getLanguage());
            updateVideo.setInt(2, video.getDuration());
            updateVideo.setInt(3, video.getRating());
            updateVideo.setString(4, video.getFormat());
            updateVideo.setInt(5, video.getMaterialId());
            updateVideo.executeUpdate();
        }

        connection.commit();
    }

    /**
     * Deletes a video from the database by its ID.
     *
     * @param id the ID of the video to be deleted
     * @throws SQLException if a database access error occurs
     */
    public static void delete(Video video) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String videoQuery = "DELETE FROM video WHERE material_id = ?";
        String materialQuery = "DELETE FROM material WHERE material_id = ?";

        connection.setAutoCommit(false);

        try (PreparedStatement deleteVideo = connection.prepareStatement(videoQuery);
                PreparedStatement deleteMaterial = connection.prepareStatement(materialQuery)) {
            deleteVideo.setInt(1, video.getMaterialId());
            deleteVideo.executeUpdate();

            deleteMaterial.setInt(1, video.getMaterialId());
            deleteMaterial.executeUpdate();
        }

        connection.commit();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "Video{" +
                "materialId=" + getMaterialId() +
                ", publisherId=" + getPublisherId() +
                ", description='" + getDescription() + '\'' +
                ", imageUrl='" + getImageUrl() + '\'' +
                ", ageRestriction=" + getAgeRestriction() +
                ", title='" + getTitle() + '\'' +
                ", rating='" + rating + '\'' +
                ", duration=" + duration +
                ", language='" + language + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
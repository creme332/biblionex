package com.github.creme332.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.creme332.utils.exception.UserVisibleException;

/**
 * Stores information about a material (video, book, journal).
 */
public abstract class Material {
    protected int materialId;
    protected int publisherId;
    protected String description;
    protected String imageUrl;
    protected int ageRestriction;
    protected MaterialType type;
    protected String title;

    protected Material(int materialId, int publisherId, String description, String imageUrl, int ageRestriction,
            MaterialType type, String title) {
        this.materialId = materialId;
        this.publisherId = publisherId;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ageRestriction = ageRestriction;
        this.type = type;
        this.title = title;
    }

    /**
     * Constructor for creating a new material. Material ID is unknown and will be
     * set by database.
     * 
     * @param materialId
     * @param publisherId
     * @param description
     * @param imageUrl
     * @param ageRestriction
     * @param type
     * @param title
     */
    protected Material(int publisherId, String description, String imageUrl, int ageRestriction,
            MaterialType type, String title) {
        this.publisherId = publisherId;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ageRestriction = ageRestriction;
        this.type = type;
        this.title = title;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void validate() throws UserVisibleException {
        // Validate description
        if (description == null || description.isBlank()) {
            throw new UserVisibleException("Description cannot be empty");
        }

        // Validate publisherId
        if (publisherId <= 0) {
            throw new UserVisibleException("Publisher ID must be a positive integer");
        }

        // Validate imageUrl (optional validation)
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new UserVisibleException("Image URL cannot be empty");
        }

        // Validate ageRestriction (assuming age must be a valid range)
        if (ageRestriction < 0) {
            throw new UserVisibleException("Age restriction cannot be negative");
        }

        // Validate type (assuming type cannot be null)
        if (type == null) {
            throw new UserVisibleException("Material type must be specified");
        }

        // Validate title
        if (title == null || title.isBlank()) {
            throw new UserVisibleException("Title cannot be empty");
        }
    }

    /**
     * 
     * @return File name of image representing material
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 
     * @param imageUrl New file name of image representing material
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 
     * @return Project-root-relative path to image representing material
     */
    public String getRelativeImgPath() {
        if (type == MaterialType.BOOK) {
            return "/catalog/book.png";
        }
        if (type == MaterialType.JOURNAL) {
            return "/catalog/journal.png";
        }
        return "/catalog/video.png";
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return A list of all materials stored in database
     * @throws SQLException
     */
    public static List<Material> findAllMaterials() throws SQLException {
        List<Material> allMaterials = new ArrayList<>();

        for (Material material : Book.findAll()) {
            allMaterials.add(material);
        }
        for (Material material : Journal.findAll()) {
            allMaterials.add(material);
        }
        for (Material material : Video.findAll()) {
            allMaterials.add(material);
        }

        return allMaterials;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialId=" + materialId +
                ", publisherId=" + publisherId +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", ageRestriction=" + ageRestriction +
                ", type=" + type +
                ", title='" + title + '\'' +
                '}';
    }
}
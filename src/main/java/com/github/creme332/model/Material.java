package com.github.creme332.model;

public class Material {
    protected int materialId;
    protected int publisherId;
    protected String description;
    protected String imageUrl;
    protected int ageRestriction;
    protected MaterialType type;
    protected String title;

    public Material(int materialId, int publisherId, String description, String imageUrl, int ageRestriction,
            MaterialType type, String title) {
        this.materialId = materialId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    // Utility Methods
    @Override
    public String toString() {
        return "Material{" +
                "materialId=" + materialId +
                ", publisherId=" + publisherId +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", ageRestriction=" + ageRestriction +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

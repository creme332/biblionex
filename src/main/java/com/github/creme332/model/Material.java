package com.github.creme332.model;

public class Material {
    private int materialId;
    private int publisherId;
    private String description;
    private String imageUrl;
    private int ageRestriction;
    private String type;
    private String title;

    public Material(int materialId, int publisherId, String description, String imageUrl, int ageRestriction, String type, String title) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

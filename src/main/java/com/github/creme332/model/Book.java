package com.github.creme332.model;

public class Book extends Material {
    private int pageCount;
    private String isbn;

    public Book(int materialId, int publisherId, String description, String imageUrl, int ageRestriction, String type, String title, int pageCount, String isbn) {
        super(materialId, publisherId, description, imageUrl, ageRestriction, type, title);
        this.pageCount = pageCount;
        this.isbn = isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "pageCount=" + pageCount +
                ", isbn='" + isbn + '\'' +
                ", materialId=" + getMaterialId() +
                ", publisherId=" + getPublisherId() +
                ", description='" + getDescription() + '\'' +
                ", imageUrl='" + getImageUrl() + '\'' +
                ", ageRestriction=" + getAgeRestriction() +
                ", type='" + getType() + '\'' +
                ", title='" + getTitle() + '\'' +
                '}';
    }
}


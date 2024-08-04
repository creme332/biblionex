package com.github.creme332.model;

/**
 * Location in library where a material can be stored. Multiple materials can be
 * stored at the same location.
 */
public class MaterialLocation {
    private int shelfNo;
    private int aisleNo;
    private int sectionNo;

    public MaterialLocation(int shelfNo, int aisleNo, int sectionNo) {
        this.shelfNo = shelfNo;
        this.aisleNo = aisleNo;
        this.sectionNo = sectionNo;
    }

    public int getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(int shelfNo) {
        this.shelfNo = shelfNo;
    }

    public int getAisleNo() {
        return aisleNo;
    }

    public void setAisleNo(int aisleNo) {
        this.aisleNo = aisleNo;
    }

    public int getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(int sectionNo) {
        this.sectionNo = sectionNo;
    }

    @Override
    public String toString() {
        return "MaterialLocation{" +
                "shelfNo=" + shelfNo +
                ", aisleNo=" + aisleNo +
                ", sectionNo=" + sectionNo +
                '}';
    }
}

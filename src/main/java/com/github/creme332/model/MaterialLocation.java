package com.github.creme332.model;

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
}

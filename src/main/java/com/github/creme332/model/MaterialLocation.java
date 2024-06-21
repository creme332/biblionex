package com.github.creme332.model;

public class MaterialLocation {

    private int barcode;
    private int materialId;
    private int shelfNo;
    private int aisleNo;
    private int sectionNo;

    public MaterialLocation(int barcode, int materialId, int shelfNo, int aisleNo, int sectionNo) {
        this.barcode = barcode;
        this.materialId = materialId;
        this.shelfNo = shelfNo;
        this.aisleNo = aisleNo;
        this.sectionNo = sectionNo;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
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

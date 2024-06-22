package com.github.creme332.model;

public enum MaterialType {
  VIDEO("video"),
  JOURNAL("journal"),
  BOOK("book");

  String stringInDB;

  /**
   * 
   * @param stringRep string representation of enum as in database
   */
  MaterialType(String stringRep) {
    this.stringInDB = stringRep;
  }

  @Override
  public String toString() {
    return stringInDB;
  }

  public static MaterialType fromString(String str) {
    for (MaterialType value : MaterialType.values()) {
      if (value.toString().equals(str)) {
        return value;
      }
    }
    return MaterialType.valueOf(str);
  }
}

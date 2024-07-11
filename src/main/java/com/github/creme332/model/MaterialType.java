package com.github.creme332.model;

public enum MaterialType {
  VIDEO("Video"),
  JOURNAL("Journal"),
  BOOK("Book");

  /**
   * String representation of enum as in database
   */
  private String stringInDB;

  MaterialType(String stringRep) {
    this.stringInDB = stringRep;
  }

  @Override
  public String toString() {
    return stringInDB;
  }

  /**
   * Converts a string to a MaterialCondition enum. Use this function over
   * valueOf() if strings comes from database.
   * 
   * @param str
   * @return
   */
  public static MaterialType fromString(String str) {
    for (MaterialType value : MaterialType.values()) {
      if (value.toString().equals(str)) {
        return value;
      }
    }
    return MaterialType.valueOf(str);
  }
}

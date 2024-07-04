package com.github.creme332.model;

public enum MaterialCondition {
  NEW("New"),
  USED_LIKE_NEW("Used - Like New"),
  USED_GOOD("Used - Good"),
  USED_ACCEPTABLE("Used - Acceptable"),
  UNACCEPTABLE("Unacceptable");

  private String stringInDB;

  /**
   * 
   * @param stringRep string representation of enum as in database
   */
  MaterialCondition(String stringRep) {
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
  public static MaterialCondition fromString(String str) {
    for (MaterialCondition value : MaterialCondition.values()) {
      if (value.toString().equals(str)) {
        return value;
      }
    }
    return MaterialCondition.valueOf(str);
  }
}
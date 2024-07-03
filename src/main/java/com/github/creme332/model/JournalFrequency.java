package com.github.creme332.model;

public enum JournalFrequency {
  WEEKLY("Weekly"),
  MONTHLY("Monthly"),
  QUARTERLY("Quarterly"),
  ANNUALLY("Annually"),
  BIANNUALLY("Biannually"),
  DAILY("Daily");

  /**
   * String representation of enum as in database
   */
  private String stringInDB;

  JournalFrequency(String stringRep) {
    this.stringInDB = stringRep;
  }

  @Override
  public String toString() {
    return stringInDB;
  }

  /**
   * Converts a string to a JournalFrequency enum. Use this function over
   * valueOf() if strings comes from database.
   * 
   * @param str
   * @return
   */
  public static JournalFrequency fromString(String str) {
    for (JournalFrequency value : JournalFrequency.values()) {
      if (value.toString().equals(str)) {
        return value;
      }
    }
    return JournalFrequency.valueOf(str);
  }
}

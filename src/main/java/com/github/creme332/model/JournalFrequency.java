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

  public static JournalFrequency fromString(String str) {
    for (JournalFrequency value : JournalFrequency.values()) {
      if (value.toString().equals(str)) {
        return value;
      }
    }
    return JournalFrequency.valueOf(str);
  }
}

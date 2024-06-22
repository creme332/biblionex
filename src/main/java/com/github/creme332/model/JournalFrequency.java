package com.github.creme332.model;

public enum JournalFrequency {
  WEEKLY("weekly"),
  MONTHLY("monthly"),
  QUARTERLY("quarterly"),
  ANNUALLY("annually"),
  BIANNUALLY("biannually"),
  DAILY("daily");

  private String stringInDB;

  /**
   * 
   * @param stringRep string representation of enum as in database
   */
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

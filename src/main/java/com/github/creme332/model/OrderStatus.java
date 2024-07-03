package com.github.creme332.model;

public enum OrderStatus {
  PENDING("Pending"),
  PROCESSING("Processing"),
  SHIPPED("Shipped"),
  DELIVERED("Delivered"),
  COMPLETED("Completed"),
  CANCELLED("Cancelled"),
  REFUNDED("Refunded"),
  ON_HOLD("On Hold"),
  FAILED("Failed"),
  RETURNED("Returned");

  String stringInDB;

  /**
   * 
   * @param stringRep string representation of enum as in database
   */
  OrderStatus(String stringRep) {
    this.stringInDB = stringRep;
  }

  @Override
  public String toString() {
    return stringInDB;
  }

  public static OrderStatus fromString(String str) {
    for (OrderStatus value : OrderStatus.values()) {
      if (value.toString().equals(str)) {
        return value;
      }
    }
    return OrderStatus.valueOf(str);
  }
}
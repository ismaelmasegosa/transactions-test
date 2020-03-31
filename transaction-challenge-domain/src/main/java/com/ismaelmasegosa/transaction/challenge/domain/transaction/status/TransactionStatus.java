package com.ismaelmasegosa.transaction.challenge.domain.transaction.status;

import java.util.Objects;

public class TransactionStatus {

  private String reference;

  private String status;

  private double amount;

  private double fee;

  public TransactionStatus(String reference, String status, double amount, double fee) {
    this.reference = reference;
    this.status = status;
    this.amount = amount;
    this.fee = fee;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getFee() {
    return fee;
  }

  public void setFee(double fee) {
    this.fee = fee;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionStatus that = (TransactionStatus) o;
    return Double.compare(that.amount, amount) == 0 && Double.compare(that.fee, fee) == 0 && Objects.equals(reference, that.reference)
        && Objects.equals(status, that.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, status, amount, fee);
  }

  @Override
  public String toString() {
    return "TransactionStatus{" + "reference='" + reference + '\'' + ", status='" + status + '\'' + ", amount=" + amount + ", fee=" + fee
        + '}';
  }
}

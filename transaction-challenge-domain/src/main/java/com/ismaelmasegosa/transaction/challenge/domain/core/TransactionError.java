package com.ismaelmasegosa.transaction.challenge.domain.core;

import java.util.Objects;

public class TransactionError {

  private String reference;

  private final static String STATUS = "INVALID";

  public TransactionError(String reference) {
    this.reference = reference;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getStatus() {
    return STATUS;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionError that = (TransactionError) o;
    return Objects.equals(reference, that.reference);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference);
  }

  @Override
  public String toString() {
    return "TransactionError{" + "reference='" + reference + '\'' + '}';
  }
}

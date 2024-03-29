package com.ismaelmasegosa.transaction.challenge.usecases.params;

import static com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParamsValidation.fullParametersValidations;

import com.ismaelmasegosa.transaction.challenge.domain.core.Validation;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCaseParams;
import com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult;
import java.util.Objects;

public class CreateTransactionParams implements UseCaseParams {

  private String reference;

  private String accountIban;

  private long date;

  private double amount;

  private double fee;

  private String description;

  public CreateTransactionParams(String reference, String accountIban, long date, double amount, double fee, String description) {
    this.reference = reference;
    this.accountIban = accountIban;
    this.date = date;
    this.amount = amount;
    this.fee = fee;
    this.description = description;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getAccountIban() {
    return accountIban;
  }

  public void setAccountIban(String accountIban) {
    this.accountIban = accountIban;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public Validation validate() {
    final Validation validation = new Validation();
    final ValidationResult result = fullParametersValidations().apply(this);
    result.getReasons().ifPresent(validation::addErrors);
    return validation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTransactionParams that = (CreateTransactionParams) o;
    return Double.compare(that.amount, amount) == 0 && Double.compare(that.fee, fee) == 0 && Objects.equals(reference, that.reference)
        && Objects.equals(accountIban, that.accountIban) && Objects.equals(date, that.date) && Objects
        .equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, accountIban, date, amount, fee, description);
  }

  @Override
  public String toString() {
    return "CreateTransactionParams{" + "reference='" + reference + '\'' + ", accountIban='" + accountIban + '\'' + ", date=" + date
        + ", amount=" + amount + ", fee=" + fee + ", description='" + description + '\'' + '}';
  }
}

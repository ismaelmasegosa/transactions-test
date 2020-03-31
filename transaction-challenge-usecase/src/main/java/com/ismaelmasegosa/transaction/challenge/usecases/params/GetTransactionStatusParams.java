package com.ismaelmasegosa.transaction.challenge.usecases.params;

import static com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParamsValidation.fullParametersValidations;

import com.ismaelmasegosa.transaction.challenge.domain.core.Validation;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCaseParams;
import com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult;
import java.util.Objects;

public class GetTransactionStatusParams implements UseCaseParams {

  private String reference;

  private String channel;

  public GetTransactionStatusParams(String reference, String channel) {
    this.reference = reference;
    this.channel = channel;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
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
    GetTransactionStatusParams that = (GetTransactionStatusParams) o;
    return Objects.equals(reference, that.reference) && Objects.equals(channel, that.channel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, channel);
  }

  @Override
  public String toString() {
    return "GetTransactionStatusParams{" + "reference='" + reference + '\'' + ", channel='" + channel + '\'' + '}';
  }
}

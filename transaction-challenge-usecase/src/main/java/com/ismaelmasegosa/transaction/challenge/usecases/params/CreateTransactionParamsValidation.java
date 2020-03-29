package com.ismaelmasegosa.transaction.challenge.usecases.params;

import static com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult.invalid;
import static com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult.valid;
import static com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationSupport.zip;

import com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult;
import java.util.function.Function;
import java.util.function.Predicate;

public interface CreateTransactionParamsValidation extends Function<CreateTransactionParams, ValidationResult> {

  static CreateTransactionParamsValidation accountIbanIsValid() {
    return holds(params -> !isEmpty(params.getAccountIban()), "Account IBAN can not be empty");
  }

  static CreateTransactionParamsValidation amountIsValid() {
    return holds(params -> params.getAmount() != 0.0, "Amount can not be zero");
  }

  static CreateTransactionParamsValidation feeIsValid() {
    return holds(params -> params.getFee() >= 0.0, "Fee must be greater that zero");
  }

  static CreateTransactionParamsValidation holds(Predicate<CreateTransactionParams> p, String message) {
    return params -> p.test(params) ? valid() : invalid(message);
  }

  static CreateTransactionParamsValidation fullParametersValidations() {
    return accountIbanIsValid().and(amountIsValid()).and(feeIsValid());
  }

  default CreateTransactionParamsValidation and(CreateTransactionParamsValidation other) {
    return params -> zip(this.apply(params), other.apply(params));
  }

  static boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }
}

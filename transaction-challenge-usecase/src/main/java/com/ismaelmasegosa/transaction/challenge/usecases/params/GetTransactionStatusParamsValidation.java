package com.ismaelmasegosa.transaction.challenge.usecases.params;

import static com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult.invalid;
import static com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult.valid;
import static com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationSupport.zip;
import static java.util.Arrays.asList;

import com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface GetTransactionStatusParamsValidation extends Function<GetTransactionStatusParams, ValidationResult> {

  static List<String> CHANNELS = asList("CLIENT", "ATM", "INTERNAL");

  static GetTransactionStatusParamsValidation accountIbanIsValid() {
    return holds(params -> CHANNELS.contains(params.getChannel()), "The channel must be CLIENT, ATM or INTERNAL");
  }

  static GetTransactionStatusParamsValidation holds(Predicate<GetTransactionStatusParams> p, String message) {
    return params -> p.test(params) ? valid() : invalid(message);
  }

  static GetTransactionStatusParamsValidation fullParametersValidations() {
    return accountIbanIsValid();
  }

  default GetTransactionStatusParamsValidation and(GetTransactionStatusParamsValidation other) {
    return params -> zip(this.apply(params), other.apply(params));
  }

  static boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }
}

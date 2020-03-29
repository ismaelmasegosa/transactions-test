package com.ismaelmasegosa.transaction.challenge.usecases.core.validation;

import static com.ismaelmasegosa.transaction.challenge.usecases.core.validation.ValidationResult.invalid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidationSupport {

  private static final ValidationResult valid = new ValidationResult() {
    public boolean isValid() {
      return true;
    }

    public Optional<List<String>> getReasons() {
      return Optional.empty();
    }
  };

  private ValidationSupport() {
  }

  static ValidationResult valid() {
    return valid;
  }

  public static ValidationResult zip(ValidationResult left, ValidationResult right) {
    if (left.isValid() && right.isValid()) {
      return valid();
    } else {
      return invalid(concat(left.getReasons(), right.getReasons()).flatMap(List::stream).collect(Collectors.toList()));
    }
  }

  public static Stream<List<String>> concat(Optional<List<String>> leftReasons, Optional<List<String>> rightReason) {
    return Stream.concat(toStream(leftReasons), toStream(rightReason));
  }

  public static <T> Stream<T> toStream(Optional<T> optional) {
    return optional.map(Stream::of).orElseGet(Stream::empty);
  }

}

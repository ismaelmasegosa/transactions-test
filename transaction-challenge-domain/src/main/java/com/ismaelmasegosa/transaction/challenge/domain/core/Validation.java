package com.ismaelmasegosa.transaction.challenge.domain.core;

import static com.ismaelmasegosa.transaction.challenge.domain.core.Either.left;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Validation {

  private static final Validation EMPTY = new Validation();

  private final List<String> errors;

  public Validation() {
    this.errors = new ArrayList<>();
  }

  public Validation(List<String> errors) {
    this.errors = errors;
  }

  public static Validation empty() {
    return EMPTY;
  }

  public List<String> getErrors() {
    return unmodifiableList(errors);
  }

  public void addError(String error) {
    this.errors.add(error);
  }

  public void addErrors(List<String> error) {
    this.errors.addAll(error);
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  public <T extends RuntimeException> T as(Function<List<String>, T> supplier) {
    return supplier.apply(getErrors());
  }

  public Validation or(Supplier<Validation> validation) {
    if (this.hasErrors()) {
      return this;
    } else {
      return validation.get();
    }
  }

  public <T extends RuntimeException> Validation orElseThrow(Supplier<Validation> validation, Function<List<String>, T> supplier) {
    if (this.hasErrors()) {
      throw supplier.apply(getErrors());
    } else {
      return validation.get();
    }
  }

  public <R> Either<Validation, R> asEither() {
    return left(this);
  }

  public static Validation of(String error) {
    final Validation validation = new Validation();
    validation.addError(error);
    return validation;
  }

  @Override
  public String toString() {
    return "Validation{" + "errors=" + errors + '}';
  }
}

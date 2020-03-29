package com.ismaelmasegosa.transaction.challenge.usecases.core;

@FunctionalInterface
public interface UseCase<T extends UseCaseParams, R> {

  R execute(T params);

}

package com.ismaelmasegosa.transaction.challenge.usecases;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Validation;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.SearchTransactionsParams;
import java.util.List;

public class SearchTransactions implements UseCase<SearchTransactionsParams, Either<Validation, List<Transaction>>> {

  @Override
  public Either<Validation, List<Transaction>> execute(SearchTransactionsParams params) {
    throw new UnsupportedOperationException("Not Implemented");
  }
}

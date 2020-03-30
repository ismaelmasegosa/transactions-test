package com.ismaelmasegosa.transaction.challenge.usecases;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.SearchTransactionsParams;
import java.util.List;

public class SearchTransactions implements UseCase<SearchTransactionsParams, Either<Error, List<Transaction>>> {

  @Override
  public Either<Error, List<Transaction>> execute(SearchTransactionsParams params) {
    throw new UnsupportedOperationException("Not Implemented");
  }
}

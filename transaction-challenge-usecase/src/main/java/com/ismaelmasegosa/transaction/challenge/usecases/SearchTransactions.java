package com.ismaelmasegosa.transaction.challenge.usecases;

import static com.ismaelmasegosa.transaction.challenge.domain.core.Either.right;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.SearchTransactionsParams;
import java.util.List;

public class SearchTransactions implements UseCase<SearchTransactionsParams, Either<Error, List<Transaction>>> {

  private final TransactionCollection transactionCollection;

  public SearchTransactions(TransactionCollection transactionCollection) {
    this.transactionCollection = transactionCollection;
  }

  @Override
  public Either<Error, List<Transaction>> execute(SearchTransactionsParams params) {
    if (!params.getSort().isEmpty()) {
      return right(transactionCollection.findOrderByAmount(params.getSort()));
    } else {
      return right(transactionCollection.findByAccountIban(params.getAccountIban()));
    }
  }
}

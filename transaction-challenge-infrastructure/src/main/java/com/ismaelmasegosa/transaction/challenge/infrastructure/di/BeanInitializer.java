package com.ismaelmasegosa.transaction.challenge.infrastructure.di;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import com.ismaelmasegosa.transaction.challenge.usecases.CreateTransaction;
import com.ismaelmasegosa.transaction.challenge.usecases.GetTransactionStatus;
import com.ismaelmasegosa.transaction.challenge.usecases.SearchTransactions;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParams;
import com.ismaelmasegosa.transaction.challenge.usecases.params.SearchTransactionsParams;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInitializer {

  @Bean
  @ConditionalOnMissingBean(name = "createTransaction")
  public UseCase<CreateTransactionParams, Either<Error, Transaction>> createTransaction(DomainEventPublisher eventPublisher,
      AccountBalanceProvider accountBalanceProvider, TransactionCollection transactionCollection) {
    return new CreateTransaction(eventPublisher, accountBalanceProvider, transactionCollection);
  }

  @Bean
  @ConditionalOnMissingBean(name = "searchTransactions")
  public UseCase<SearchTransactionsParams, Either<Error, List<Transaction>>> searchTransactions(
      TransactionCollection transactionCollection) {
    return new SearchTransactions(transactionCollection);
  }

  @Bean
  @ConditionalOnMissingBean(name = "getTransactionStatus")
  public UseCase<GetTransactionStatusParams, Either<Error, TransactionStatus>> getTransactionStatus(TransactionCollection transactionCollection) {
    return new GetTransactionStatus(transactionCollection);
  }
}

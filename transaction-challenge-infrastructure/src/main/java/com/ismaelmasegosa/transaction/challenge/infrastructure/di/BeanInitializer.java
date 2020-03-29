package com.ismaelmasegosa.transaction.challenge.infrastructure.di;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.usecases.CreateTransaction;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
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
}

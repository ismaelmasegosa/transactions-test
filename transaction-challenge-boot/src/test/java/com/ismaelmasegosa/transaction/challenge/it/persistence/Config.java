package com.ismaelmasegosa.transaction.challenge.it.persistence;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceRepository;
import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.it.stubs.DomainEventPublisherStub;
import com.ismaelmasegosa.transaction.challenge.it.stubs.InMemoryTransactionRepositoryStub;
import com.ismaelmasegosa.transaction.challenge.usecases.CreateTransaction;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  @ConditionalOnMissingBean(name = "transactionRepository")
  public TransactionRepository transactionRepository() {
    return new InMemoryTransactionRepositoryStub();
  }

  @Bean
  @ConditionalOnMissingBean(name = "eventPublisher")
  public DomainEventPublisherStub eventPublisher() {
    return new DomainEventPublisherStub();
  }

  @Bean
  @ConditionalOnMissingBean(name = "createTransaction")
  public UseCase<CreateTransactionParams, Either<Error, Transaction>> createTransaction(DomainEventPublisherStub eventPublisherStub,
      AccountBalanceProvider accountBalanceProvider, TransactionCollection transactionCollection) {
    return new CreateTransaction(eventPublisherStub, accountBalanceProvider, transactionCollection);
  }
}

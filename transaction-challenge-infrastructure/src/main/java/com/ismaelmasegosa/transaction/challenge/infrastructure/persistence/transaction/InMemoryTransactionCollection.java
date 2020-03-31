package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction;

import static java.util.Collections.emptyList;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters.Filter;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters.TransactionFilter;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters.TransactionsFilterByAccounIban;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters.TransactionsFilterByAccounIbanAndOrderByAmount;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters.TransactionsNonFilter;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters.TransactionsOrderByAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTransactionCollection implements TransactionCollection {

  private final TransactionRepository transactionRepository;

  private final Function<TransactionEntity, Transaction> entityToDomain;

  private final Function<Transaction, TransactionEntity> domainToEntity;

  public InMemoryTransactionCollection(TransactionRepository transactionRepository, Function<TransactionEntity, Transaction> entityToDomain,
      Function<Transaction, TransactionEntity> domainToEntity) {
    this.transactionRepository = transactionRepository;
    this.entityToDomain = entityToDomain;
    this.domainToEntity = domainToEntity;
  }

  @Override
  public Optional<Transaction> findByReference(String reference) {
    return transactionRepository.findByReference(reference).map(entityToDomain);
  }

  @Override
  public Transaction addTransaction(Transaction transaction) {
    TransactionEntity savedTransaction = transactionRepository.save(mapToEntity(transaction));
    return mapToDomain(savedTransaction);
  }

  @Override
  public List<Transaction> findByAccountIbanOrderByAmount(String accountIban, String sort) {

    List<TransactionFilter> criterias = new ArrayList<>();
    criterias.add(new TransactionsNonFilter(new Filter(accountIban, sort)));
    criterias.add(new TransactionsFilterByAccounIbanAndOrderByAmount(new Filter(accountIban, sort)));
    criterias.add(new TransactionsOrderByAmount(new Filter(accountIban, sort)));
    criterias.add(new TransactionsFilterByAccounIban(new Filter(accountIban, sort)));
    List<TransactionEntity> transactionEntities =
        criterias.stream().filter(TransactionFilter::condition).map(filter -> filter.action(transactionRepository)).findFirst()
            .orElse(emptyList());
    return transactionEntities.stream().map(entityToDomain).collect(Collectors.toList());
  }

  private Transaction mapToDomain(TransactionEntity transactionEntity) {
    return entityToDomain.apply(transactionEntity);
  }

  private TransactionEntity mapToEntity(Transaction transaction) {
    return domainToEntity.apply(transaction);
  }
}

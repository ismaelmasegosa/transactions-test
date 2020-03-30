package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction;

import static org.springframework.util.StringUtils.isEmpty;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
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
    List<TransactionEntity> transactions;
    if (isEmpty(accountIban) && isEmpty(sort)) {
      transactions = transactionRepository.findAll();
    } else {
      if (!isEmpty(accountIban) && isEmpty(sort)) {
        transactions = transactionRepository.findByAccountIban(accountIban);
      } else if (isEmpty(accountIban) && !isEmpty(sort)) {
        transactions = transactionRepository.findOrderByAmount(sort);
      } else {
        transactions = transactionRepository.findByAccountIbanOrderByAmount(accountIban, sort);
      }
    }
    return transactions.stream().map(entityToDomain).collect(Collectors.toList());
  }

  private Transaction mapToDomain(TransactionEntity transactionEntity) {
    return entityToDomain.apply(transactionEntity);
  }

  private TransactionEntity mapToEntity(Transaction transaction) {
    return domainToEntity.apply(transaction);
  }
}

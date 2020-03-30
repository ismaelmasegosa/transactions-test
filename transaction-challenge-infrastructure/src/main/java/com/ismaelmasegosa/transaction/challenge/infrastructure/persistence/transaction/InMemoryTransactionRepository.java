package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction;

import static java.util.Comparator.comparingLong;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

  private final Map<String, TransactionEntity> transactionRepository;

  public InMemoryTransactionRepository() {
    this.transactionRepository = new HashMap<>();
  }

  @Override
  public Optional<TransactionEntity> findByReference(String reference) {
    return Optional.ofNullable(transactionRepository.get(reference));
  }

  @Override
  public List<TransactionEntity> findAll() {
    return transactionRepository.values().stream().sorted(comparingLong(TransactionEntity::getDate).reversed())
        .collect(Collectors.toList());
  }

  @Override
  public List<TransactionEntity> findByAccountIban(String accountIban) {
    Predicate<TransactionEntity> predicate = transaction -> transaction.getAccountIban().equalsIgnoreCase(accountIban);
    return transactionRepository.values().stream().filter(predicate).sorted(comparingLong(TransactionEntity::getDate).reversed())
        .collect(Collectors.toList());
  }

  @Override
  public TransactionEntity save(TransactionEntity transactionEntity) {
    transactionRepository.put(transactionEntity.getReference(), transactionEntity);
    return transactionEntity;
  }

  @Override
  public void delete(String reference) {
    transactionRepository.remove(reference);
  }
}

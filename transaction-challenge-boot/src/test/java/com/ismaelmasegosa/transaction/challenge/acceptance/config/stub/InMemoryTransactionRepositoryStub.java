package com.ismaelmasegosa.transaction.challenge.acceptance.config.stub;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingLong;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTransactionRepositoryStub implements TransactionRepository {

  private final Map<String, TransactionEntity> transactionRepository;

  public InMemoryTransactionRepositoryStub() {
    this.transactionRepository = new HashMap<>();
  }

  @Override
  public Optional<TransactionEntity> findByReference(String reference) {
    return Optional.ofNullable(transactionRepository.get(reference));
  }

  @Override
  public TransactionEntity save(TransactionEntity transactionEntity) {
    transactionRepository.put(transactionEntity.getReference(), transactionEntity);
    return transactionEntity;
  }

  @Override
  public List<TransactionEntity> findByAccountIban(String accountIban) {
    Predicate<TransactionEntity> predicate = transaction -> transaction.getAccountIban().equalsIgnoreCase(accountIban);
    return transactionRepository.values().stream().filter(predicate).sorted(comparingLong(TransactionEntity::getDate).reversed())
        .collect(Collectors.toList());
  }

  @Override
  public List<TransactionEntity> findOrderByAmount(String sort) {
    List<TransactionEntity> transactionEntities;
    if (sort.equalsIgnoreCase("ascending")) {
      transactionEntities =
          transactionRepository.values().stream().sorted(comparingDouble(TransactionEntity::getAmount)).collect(Collectors.toList());
    } else if (sort.equalsIgnoreCase("descending")) {
      transactionEntities = transactionRepository.values().stream().sorted(comparingDouble(TransactionEntity::getAmount).reversed())
          .collect(Collectors.toList());
    } else {
      transactionEntities =
          transactionRepository.values().stream().sorted(comparingLong(TransactionEntity::getDate).reversed()).collect(Collectors.toList());
    }
    return transactionEntities;
  }

  @Override
  public List<TransactionEntity> findAll() {
    return transactionRepository.values().stream().sorted(comparingLong(TransactionEntity::getDate).reversed())
        .collect(Collectors.toList());
  }

  @Override
  public void delete(String reference) {
    transactionRepository.remove(reference);
  }
}

package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction;

import static java.util.Comparator.comparingDouble;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
  public List<TransactionEntity> findByAccountIbanOrderByAmount(String iban, int sort) {
    List<TransactionEntity> filterCollection =
        transactionRepository.values().stream().filter(transactionEntity -> transactionEntity.getAccountIban().equalsIgnoreCase(iban))
            .collect(Collectors.toList());
    List<TransactionEntity> sortCollection;
    if (sort == -1) {
      sortCollection =
          filterCollection.stream().sorted(comparingDouble(TransactionEntity::getAmount).reversed()).collect(Collectors.toList());
    } else {
      sortCollection = filterCollection.stream().sorted(comparingDouble(TransactionEntity::getAmount)).collect(Collectors.toList());
    }
    return sortCollection;
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

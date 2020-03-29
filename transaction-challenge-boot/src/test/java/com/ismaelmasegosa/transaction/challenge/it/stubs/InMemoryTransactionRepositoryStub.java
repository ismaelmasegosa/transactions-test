package com.ismaelmasegosa.transaction.challenge.it.stubs;

import static java.util.Comparator.comparingDouble;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
  public List<TransactionEntity> findByAccountIbanOrderByAmount(String iban, int sort) {
    List<TransactionEntity> filterCollection =
        transactionRepository.values().stream().filter(transactionEntity -> transactionEntity.getAccountIban().equalsIgnoreCase(iban))
            .collect(Collectors.toList());
    List<TransactionEntity> sortColletion;
    if (sort == -1) {
      sortColletion =
          filterCollection.stream().sorted(comparingDouble(TransactionEntity::getAmount).reversed()).collect(Collectors.toList());
    } else {
      sortColletion = filterCollection.stream().sorted(comparingDouble(TransactionEntity::getAmount)).collect(Collectors.toList());
    }
    return sortColletion;
  }

  @Override
  public void delete(String reference) {
    transactionRepository.remove(reference);
  }

}

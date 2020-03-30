package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    return new ArrayList<>(transactionRepository.values());
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

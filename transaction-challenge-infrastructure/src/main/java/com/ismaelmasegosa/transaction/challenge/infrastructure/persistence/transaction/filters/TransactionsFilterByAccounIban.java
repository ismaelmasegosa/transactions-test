package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters;

import static org.springframework.util.StringUtils.isEmpty;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.List;

public class TransactionsFilterByAccounIban implements TransactionFilter {

  private Filter filter;

  public TransactionsFilterByAccounIban(Filter filter) {
    this.filter = filter;
  }

  @Override
  public boolean condition() {
    return (!isEmpty(filter.getAccountIban()) && isEmpty(filter.getSort()));
  }

  @Override
  public List<TransactionEntity> action(TransactionRepository transactionRepository) {
    return transactionRepository.findByAccountIban(filter.getAccountIban());
  }

  public Filter getFilter() {
    return filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }
}

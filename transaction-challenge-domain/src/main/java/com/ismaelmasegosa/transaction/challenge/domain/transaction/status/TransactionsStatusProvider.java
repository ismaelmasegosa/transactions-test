package com.ismaelmasegosa.transaction.challenge.domain.transaction.status;

import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Status.INVALID;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.ClientChannelAndDateBeforeToday;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.StatusRules;
import java.util.HashSet;
import java.util.Set;

public class TransactionsStatusProvider {

  public TransactionStatus getTransactionStatus(Transaction transaction, String channel) {
    Set<StatusRules> criterias = new HashSet<>();
    criterias.add(new ClientChannelAndDateBeforeToday(transaction, channel));
    return criterias.stream().filter(StatusRules::condition).map(StatusRules::action).findFirst()
        .orElseGet(() -> createInvalidTransactionStatus(transaction));
  }

  private TransactionStatus createInvalidTransactionStatus(Transaction transaction) {
    return new TransactionStatus(transaction.getReference(), INVALID.name(), 0.0, 0.0);
  }
}

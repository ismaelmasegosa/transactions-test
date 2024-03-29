package com.ismaelmasegosa.transaction.challenge.domain.transaction.status;

import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Status.INVALID;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.AtmChannelAndDateGreaterToday;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.ClientChannelAndDateGreaterToday;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.ClientOrAtmChannelAndDateBeforeToday;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.ClientOrAtmChannelAndDateEqualsToday;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.InternalChannelAndDateBeforeToday;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.InternalChannelAndDateEqualToday;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.InternalChannelAndDateGreaterToday;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules.StatusRules;
import java.util.HashSet;
import java.util.Set;

public class TransactionsStatusProvider {

  public TransactionStatus getTransactionStatus(Transaction transaction, String channel) {
    Set<StatusRules> criterias = new HashSet<>();
    criterias.add(new ClientOrAtmChannelAndDateBeforeToday(transaction, channel));
    criterias.add(new InternalChannelAndDateBeforeToday(transaction, channel));
    criterias.add(new ClientOrAtmChannelAndDateEqualsToday(transaction, channel));
    criterias.add(new InternalChannelAndDateEqualToday(transaction, channel));
    criterias.add(new ClientChannelAndDateGreaterToday(transaction, channel));
    criterias.add(new AtmChannelAndDateGreaterToday(transaction, channel));
    criterias.add(new InternalChannelAndDateGreaterToday(transaction, channel));
    return criterias.stream().filter(StatusRules::condition).map(StatusRules::action).findFirst()
        .orElseGet(() -> createInvalidTransactionStatus(transaction));
  }

  private TransactionStatus createInvalidTransactionStatus(Transaction transaction) {
    return new TransactionStatus(transaction.getReference(), INVALID.name(), 0.0, 0.0);
  }
}

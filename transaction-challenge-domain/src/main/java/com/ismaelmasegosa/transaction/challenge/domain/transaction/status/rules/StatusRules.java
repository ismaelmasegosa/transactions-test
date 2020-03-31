package com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;

public interface StatusRules {

  boolean condition();

  TransactionStatus action();
}

package com.ismaelmasegosa.transaction.challenge.infrastructure.balance.listener;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.domain.account.events.UpdateAccountBalanceEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class UpdateBalanceEventListener implements ApplicationListener<PayloadApplicationEvent<UpdateAccountBalanceEvent>> {

  private final AccountBalanceProvider accountBalanceProvider;

  public UpdateBalanceEventListener(AccountBalanceProvider accountBalanceProvider) {
    this.accountBalanceProvider = accountBalanceProvider;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<UpdateAccountBalanceEvent> event) {
    UpdateAccountBalanceEvent payload = event.getPayload();
    accountBalanceProvider.updateAccountBalance(payload.getAccountIban(), payload.getBalance());
  }
}

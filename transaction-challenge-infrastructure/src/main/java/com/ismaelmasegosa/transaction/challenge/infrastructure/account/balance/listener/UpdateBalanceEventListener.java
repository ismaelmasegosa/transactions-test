package com.ismaelmasegosa.transaction.challenge.infrastructure.account.balance.listener;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceClient;
import com.ismaelmasegosa.transaction.challenge.domain.account.events.UpdateAccountBalanceEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class UpdateBalanceEventListener implements ApplicationListener<PayloadApplicationEvent<UpdateAccountBalanceEvent>> {

  private final AccountBalanceClient accountBalanceClient;

  public UpdateBalanceEventListener(AccountBalanceClient accountBalanceClient) {
    this.accountBalanceClient = accountBalanceClient;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<UpdateAccountBalanceEvent> event) {
    UpdateAccountBalanceEvent payload = event.getPayload();
    accountBalanceClient.updateAccountBalance(payload.getAccountIban(), payload.getBalance());
  }
}

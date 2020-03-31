package com.ismaelmasegosa.transaction.challenge.it;

import static org.junit.Assert.assertEquals;

import com.ismaelmasegosa.transaction.challenge.domain.account.events.UpdateAccountBalanceEvent;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import com.ismaelmasegosa.transaction.challenge.infrastructure.account.balance.provider.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.it.event.EventTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EventTest
@ActiveProfiles({"test", "event"})
public class UpdateAccountBalanceIT {

  @Autowired
  private AccountBalanceProvider accountBalanceProvider;

  @Autowired
  private DomainEventPublisher domainEventPublisher;

  @Test
  public void given_A_Update_Balance_Event_When_The_Event_Is_Listened_The_Balance_Should_Be_Updated() {
    // given
    String accountIban = "ES9820385778983000760236";
    double amount = 193.38;
    UpdateAccountBalanceEvent updateAccountBalanceEvent = new UpdateAccountBalanceEvent(accountIban, amount);
    double initialBalance = accountBalanceProvider.getAccountBalance(accountIban);

    // when
    domainEventPublisher.publish(updateAccountBalanceEvent);

    // then
    double finalAccountBalance = accountBalanceProvider.getAccountBalance(accountIban);
    double expectedFinalAccountBalance = initialBalance + amount;
    assertEquals(expectedFinalAccountBalance, finalAccountBalance,Double.NaN);
  }
}

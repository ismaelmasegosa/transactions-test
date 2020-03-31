package com.ismaelmasegosa.transaction.challenge.it.event;

import com.ismaelmasegosa.transaction.challenge.infrastructure.account.balance.provider.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.it.stubs.InMemoryAccountBalanceProviderStub;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

  @Bean
  @ConditionalOnMissingBean(name = "accountBalanceProvider")
  public AccountBalanceProvider accountBalanceProvider() {
    return new InMemoryAccountBalanceProviderStub();
  }
}

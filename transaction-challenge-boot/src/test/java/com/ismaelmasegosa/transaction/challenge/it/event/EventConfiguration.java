package com.ismaelmasegosa.transaction.challenge.it.event;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceRepository;
import com.ismaelmasegosa.transaction.challenge.it.stubs.AccountBalanceCollectionStub;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

  @Bean
  @ConditionalOnMissingBean(name = "accountBalanceCollection")
  public AccountBalanceRepository accountBalanceCollection() {
    return new AccountBalanceCollectionStub();
  }
}

package com.ismaelmasegosa.transaction.challenge.acceptance.config;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ismaelmasegosa.transaction.challenge.TransactionChallengeApplication;
import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceRepository;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.it.stubs.AccountBalanceCollectionStub;
import com.ismaelmasegosa.transaction.challenge.it.stubs.InMemoryTransactionRepositoryStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration(classes = TransactionChallengeApplication.class, loader = SpringBootContextLoader.class)
@Import(value = {AcceptanceConfiguration.CucumberConfiguration.class})
@Profile("acceptance")
public class AcceptanceConfiguration {

  @Configuration
  public static class CucumberConfiguration {

    @Autowired
    private WebApplicationContext context;

    @Bean
    public TransactionRepository transactionRepository() {
      return new InMemoryTransactionRepositoryStub();
    }

    @Bean
    public AccountBalanceRepository accountBalanceRepository() {
      return new AccountBalanceCollectionStub();
    }

    @Bean
    public World world() {
      return new World();
    }

    @Bean
    public MockMvc mockMvc() {
      return webAppContextSetup(context).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      return objectMapper;
    }
  }
}

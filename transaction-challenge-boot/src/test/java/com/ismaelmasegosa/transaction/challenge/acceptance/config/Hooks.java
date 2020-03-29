package com.ismaelmasegosa.transaction.challenge.acceptance.config;

import static com.ismaelmasegosa.transaction.challenge.acceptance.config.mothers.TransactionEntityMother.createTransactionEntity;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

  @Autowired
  TransactionRepository transactionRepository;

  @Before("@Create")
  public void before() {
    transactionRepository.save(createTransactionEntity("11111A", "ES9820385778983000760236", 120.98, 2.00, "Restaurant Payment"));
    transactionRepository.save(createTransactionEntity("22222A", "ES9820385778983000760236", 39.90, 2.00, "Parking Payment"));
    transactionRepository.save(createTransactionEntity("33333A", "ES9820385778983000760236", 1067.90, 2.00, "Car Payment"));
    transactionRepository.save(createTransactionEntity("44444A", "ES9820385778983000760236", 908.90, 2.00, "House Payment"));
  }

  @After("@Remove")
  public void after() {
    transactionRepository.delete("11111A");
    transactionRepository.delete("22222A");
    transactionRepository.delete("33333A");
    transactionRepository.delete("44444A");
  }
}

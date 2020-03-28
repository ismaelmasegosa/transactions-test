package com.ismaelmasegosa.transaction.challenge.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import com.ismaelmasegosa.transaction.challenge.it.persistence.RepositoryTest;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@RepositoryTest
@ActiveProfiles({"test", "it"})
public class CreateTransactionIT {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private UseCase<CreateTransactionParams, Either<Error, Transaction>> createTransaction;

  @Test
  public void given_Transaction_When_Save_Transaction_Is_Executed_Then_The_Transaction_Should_Be_Saved() {
    // given
    long date = System.currentTimeMillis();
    String reference = "12345A";
    String accountIban = "ES9820385778983000760236";
    double amount = 193.38;
    double fee = 3.18;
    String description = "Restaurant payment";
    CreateTransactionParams params = new CreateTransactionParams(reference, accountIban, date, amount, fee, description);

    // when
    createTransaction.execute(params);
    Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findByReference(reference);
    // then
    assertTrue(optionalTransactionEntity.isPresent());
    TransactionEntity transactionEntity = optionalTransactionEntity.get();
    assertEquals(reference, transactionEntity.getReference());
    assertEquals(accountIban, transactionEntity.getAccountIban());
    assertEquals(amount, transactionEntity.getAmount(), Double.POSITIVE_INFINITY);
    assertEquals(fee, transactionEntity.getFee(), Double.POSITIVE_INFINITY);
    assertEquals(date, transactionEntity.getDate());
    assertEquals(description, transactionEntity.getDescription());
  }
}

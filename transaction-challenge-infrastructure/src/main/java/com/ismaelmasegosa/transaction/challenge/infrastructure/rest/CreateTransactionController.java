package com.ismaelmasegosa.transaction.challenge.infrastructure.rest;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionDto;
import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionResponse;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.CreateTransactionParams;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateTransactionController {

  private final UseCase<CreateTransactionParams, Either<Error, Transaction>> createTransaction;

  private final Function<TransactionDto, CreateTransactionParams> dtoToParams;

  private final Function<Transaction, TransactionResponse> transactionToResponse;

  public CreateTransactionController(UseCase<CreateTransactionParams, Either<Error, Transaction>> createTransaction,
      Function<TransactionDto, CreateTransactionParams> dtoToParams, Function<Transaction, TransactionResponse> transactionToResponse) {
    this.createTransaction = createTransaction;
    this.dtoToParams = dtoToParams;
    this.transactionToResponse = transactionToResponse;
  }

  @PostMapping("/transaction")
  public ResponseEntity<?> createTransaction(@RequestBody TransactionDto transactionDto) {
    Either<Error, Transaction> response = createTransaction.execute(dtoToParams.apply(transactionDto));
    return created(transactionToResponse.apply(response.get()));
  }

  private ResponseEntity<TransactionResponse> created(TransactionResponse transactionResponse) {
    return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
  }
}

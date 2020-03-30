package com.ismaelmasegosa.transaction.challenge.infrastructure.rest;

import static com.ismaelmasegosa.transaction.challenge.infrastructure.rest.ErrorHandler.returnResponseEntity;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetTransactionStatusController {

  private final UseCase<GetTransactionStatusParams, Either<Error, Transaction>> getTransactionStatus;

  public GetTransactionStatusController(UseCase<GetTransactionStatusParams, Either<Error, Transaction>> getTransactionStatus) {
    this.getTransactionStatus = getTransactionStatus;
  }

  @GetMapping("/transaction/{reference}")
  public ResponseEntity<?> findTransactions(
      @PathVariable(name = "reference") String reference,
      @RequestParam(name = "channel") String channel) {
    return returnResponseEntity(getTransactionStatus.execute(toParams(reference,channel)).getLeft());
  }

  private GetTransactionStatusParams toParams(String reference, String channel) {
    return new GetTransactionStatusParams(reference, channel);
  }

}

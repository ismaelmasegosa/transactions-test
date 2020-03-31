package com.ismaelmasegosa.transaction.challenge.infrastructure.rest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionStatusResponse;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.GetTransactionStatusParams;
import java.util.function.Function;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetTransactionStatusController {

  private final UseCase<GetTransactionStatusParams, Either<Error, TransactionStatus>> getTransactionStatus;

  private final Function<TransactionStatus, TransactionStatusResponse> transactionStatusToTransactionStatusResponse;

  public GetTransactionStatusController(UseCase<GetTransactionStatusParams, Either<Error, TransactionStatus>> getTransactionStatus,
      Function<TransactionStatus, TransactionStatusResponse> transactionStatusToTransactionStatusResponse) {
    this.getTransactionStatus = getTransactionStatus;
    this.transactionStatusToTransactionStatusResponse = transactionStatusToTransactionStatusResponse;
  }

  @GetMapping("/transaction/{reference}")
  public ResponseEntity<?> findTransactions(@PathVariable(name = "reference") String reference,
      @RequestParam(name = "channel") String channel) {
    return getTransactionStatus.execute(toParams(reference, channel)).fold(ErrorHandler::returnResponseEntity, createdResponse());
  }

  private Function<TransactionStatus, ResponseEntity<TransactionStatusResponse>> createdResponse() {
    return this::toResponseEntity;
  }

  private ResponseEntity<TransactionStatusResponse> toResponseEntity(TransactionStatus transactionStatus) {
    return ResponseEntity.status(OK).contentType(APPLICATION_JSON).body(toResponse(transactionStatus));
  }

  private TransactionStatusResponse toResponse(TransactionStatus transactionStatus) {
    return transactionStatusToTransactionStatusResponse.apply(transactionStatus);
  }

  private GetTransactionStatusParams toParams(String reference, String channel) {
    return new GetTransactionStatusParams(reference, channel);
  }

}

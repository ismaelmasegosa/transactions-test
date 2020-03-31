package com.ismaelmasegosa.transaction.challenge.infrastructure.rest;

import com.ismaelmasegosa.transaction.challenge.domain.core.Either;
import com.ismaelmasegosa.transaction.challenge.domain.core.Error;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionResponse;
import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCase;
import com.ismaelmasegosa.transaction.challenge.usecases.params.SearchTransactionsParams;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchTransactionsController {

  private final UseCase<SearchTransactionsParams, Either<Error, List<Transaction>>> searchTransactions;

  private final Function<Transaction, TransactionResponse> transactionToResponse;

  public SearchTransactionsController(UseCase<SearchTransactionsParams, Either<Error, List<Transaction>>> searchTransactions,
      Function<Transaction, TransactionResponse> transactionToResponse) {
    this.searchTransactions = searchTransactions;
    this.transactionToResponse = transactionToResponse;
  }

  @GetMapping("/transactions")
  public ResponseEntity<List<TransactionResponse>> findTransactions(
      @RequestParam(name = "iban", required = false, defaultValue = "") String iban,
      @RequestParam(name = "sort", required = false, defaultValue = "") String sort) {
    return okResponse(searchTransactions.execute(toParams(iban, sort)).get());
  }

  private ResponseEntity<List<TransactionResponse>> okResponse(List<Transaction> transactions) {
    return ResponseEntity.status(HttpStatus.OK).body(toResponse(transactions));
  }

  private List<TransactionResponse> toResponse(List<Transaction> transactions) {
    return transactions.stream().map(transactionToResponse).collect(Collectors.toList());
  }

  private SearchTransactionsParams toParams(String iban, String sort) {
    return new SearchTransactionsParams(iban, sort);
  }

}

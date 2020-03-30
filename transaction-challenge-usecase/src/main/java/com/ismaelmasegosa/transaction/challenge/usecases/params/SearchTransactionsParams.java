package com.ismaelmasegosa.transaction.challenge.usecases.params;

import com.ismaelmasegosa.transaction.challenge.usecases.core.UseCaseParams;
import java.util.Objects;

public class SearchTransactionsParams implements UseCaseParams {

  private String accountIban;

  private String sort;

  public SearchTransactionsParams(String accountIban, String sort) {
    this.accountIban = accountIban;
    this.sort = sort;
  }

  public String getAccountIban() {
    return accountIban;
  }

  public void setAccountIban(String accountIban) {
    this.accountIban = accountIban;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchTransactionsParams that = (SearchTransactionsParams) o;
    return Objects.equals(accountIban, that.accountIban) && Objects.equals(sort, that.sort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountIban, sort);
  }

  @Override
  public String toString() {
    return "SearchTransactionsParams{" + "accountIban='" + accountIban + '\'' + ", sort='" + sort + '\'' + '}';
  }
}

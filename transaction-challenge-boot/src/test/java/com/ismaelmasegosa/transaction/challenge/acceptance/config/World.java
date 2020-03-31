package com.ismaelmasegosa.transaction.challenge.acceptance.config;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.time.LocalDateTime;
import org.springframework.test.web.servlet.ResultActions;

public class World {

  private String reference;

  private String accountIban;

  private LocalDateTime date;

  private double amount;

  private double fee;

  private String description;

  private ResultActions resultActions;

  private String sort;

  private String channel;

  private TransactionEntity transactionEntity;

  public void reset() {
    this.reference = "";
    this.accountIban = "";
    this.date = null;
    this.amount = 0.0;
    this.fee = 0.0;
    this.description = "";
    this.resultActions = null;
    this.sort = "";
    this.channel = "";
    this.transactionEntity = null;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getAccountIban() {
    return accountIban;
  }

  public void setAccountIban(String accountIban) {
    this.accountIban = accountIban;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getFee() {
    return fee;
  }

  public void setFee(double fee) {
    this.fee = fee;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ResultActions getResultActions() {
    return resultActions;
  }

  public void setResultActions(ResultActions resultActions) {
    this.resultActions = resultActions;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public TransactionEntity getTransactionEntity() {
    return transactionEntity;
  }

  public void setTransactionEntity(TransactionEntity transactionEntity) {
    this.transactionEntity = transactionEntity;
  }
}

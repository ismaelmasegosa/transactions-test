package com.ismaelmasegosa.transaction.challenge.acceptance.config;

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
}

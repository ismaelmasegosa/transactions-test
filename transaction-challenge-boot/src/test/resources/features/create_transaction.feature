Feature: Create a new transaction

  Scenario: Create a new transaction
    Given reference "12345A", account_iban "ES9820385778983000760236", date "2019-07-16T16:55:42.000Z", amount 193.38, fee 3.18 and description "Restaurant payment" are provided
    When a request of create a transaction is received
    Then the transaction is saved and returned

  Scenario: Create a new transaction without mandatory fields
    Given reference "12345A", date "2019-07-16T16:55:42.000Z", fee 3.18 and description "Restaurant payment" are provided
    When a request of create a transaction is received
    Then the validation error should be returned

  Scenario: Create a new transaction with an negative amount greater that account balance
    Given reference "12345A", account_iban "ES9820385778983000760236", date "2019-07-16T16:55:42.000Z", amount -1693.38, fee 3.18 and description "Restaurant payment" are provided
    When a request of create a transaction is received
    Then an uncreated transaction error should be returned

  Scenario: Create a new transaction with exist reference
    Given a exist reference "222222A", account_iban "ES9820385778983000760236", date "2019-07-16T16:55:42.000Z", amount 169.38, fee 3.18 and description "Restaurant payment" are provided
    When a request of create a transaction is received
    Then an existing transaction error should be returned
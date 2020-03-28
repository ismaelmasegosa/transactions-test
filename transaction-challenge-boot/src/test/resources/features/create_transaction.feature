Feature: Create a new transaction

  Scenario: Create a new transaction
    Given reference "12345A", account_iban "ES9820385778983000760236", date "2019-07-16T16:55:42.000Z", amount 193.38, fee 3.18 and description "Restaurant payment" are provided
    When a request of create a transaction is received
    Then the transaction is saved and returned
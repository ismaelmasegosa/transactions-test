Feature: Search transactions

  @Create @Remove
  Scenario: Search all transactions
    Given the account_iban and the sort are not provided
    When the list of transaction is requested
    Then the list of transaction is returned in descending order by date
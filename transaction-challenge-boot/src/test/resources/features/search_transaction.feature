Feature: Search transactions

  @Create @Remove
  Scenario: Search all transactions
    Given the account_iban and the sort are not provided
    When the list of transaction is requested
    Then the list of transaction is returned in descending order by date

  @Create @Remove
  Scenario: Search transactions filtered by account IBAN
    Given account_iban "ES9820385778983000760236" are provided
    When the list of transaction is requested
    Then the list of transaction is returned in descending order by date and filter by the account iban

  @Create @Remove
  Scenario: Search transactions sort by ascending amount
    Given sort "ascending" are provided
    When the list of transaction is requested
    Then the list of transaction is returned in ascending order by amount
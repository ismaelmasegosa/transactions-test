Feature: Search transactions

  @Create @Remove
  Scenario: Search transactions by account_iban sorted by ascending amount
    Given account_iban "ES9820385778983000760236" and sort "ascending" are provided
    When the list of transaction is requested
    Then the list of transaction is returned in ascending order by amount

  @Create @Remove
  Scenario: Search transactions by account_iban sorted by ascending amount
    Given account_iban "ES9820385778983000760236" and sort "descending" are provided
    When the list of transaction is requested
    Then the list of transaction is returned in descending order by amount
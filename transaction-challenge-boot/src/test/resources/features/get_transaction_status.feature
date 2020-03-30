Feature: Get transaction status

  @Create
  Scenario: Check status of non-existent transfer
    Given reference "234234A" and channel "CLIENT" are provided
    When a request of check transaction status is received
    Then "INVALID" status should be retrieve

  Scenario Outline: Check status of transfer from CLIENT channel
    Given valid reference <reference> and channel <channel> are provided
    When I check the status from CLIENT or ATM channel the transaction date is before today
    Then  The system returns the status SETTLED and the amount substracting the fee

    Examples:
      | channel | reference |
      | CLIENT  | 11111A   |
      | ATM     | 11111A   |
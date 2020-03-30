Feature: Get transaction status

  Scenario: Check status of non-existent transfer
    Given reference "234234A" and channel "CLIENT" are provided
    When a request of check transaction status is received
    Then "INVALID" status should be retrieve
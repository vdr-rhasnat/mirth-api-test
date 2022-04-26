Feature: Standard HL7 Return Message Test
  @dataFile:mirth_test_cases/standard_hl7_return_message_test_cases.xls
  Scenario: Standard HL7 Return Message From Receive Delivery Track Message
    Given message
      """
      ${Receive-delivery-track-message}
      """
    When user hit on server with the port ${port}
    And user should receive the HL7Message "${hl7-message}"
Feature: Nursing Home Flat File Generation Test
  @dataFile:mirth_test_cases/nursing_home_test_cases.xls
  Scenario: Nursing Home Flat file generation test for "${test-cases-title}"
    Given message
      """
      ${hl7-message}
      """
    When user hit the server with the IP address "${ip-address}" on port ${port}
    Then user should get the file from the filepath "${file-path}"
    And user should receive the patient medication details "${patient-medication-details}"
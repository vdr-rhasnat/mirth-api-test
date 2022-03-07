Feature: Mirth API Test
  @dataFile:mirth_test_cases/mirth_conversion_test_cases.xls
  Scenario: ${testCase}
    Given message
      """
      ${message}
      """
    When user hit the endpoint "${endpoint}"
    Then user should receive response code "${statusCode}"
    And user should receive expected JSON object
      """
      ${jsonObject}
      """
    And Value of Id should match value of BatchId
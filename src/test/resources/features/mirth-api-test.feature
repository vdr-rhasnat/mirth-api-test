Feature: Mirth API Test
  @dataFile:mirth_test_cases/mirth_conversion_test_cases.xls
  Scenario: ${testCase}
    Given message
      """
      ${message}
      """
    When I hit the mirth endpoint "${endpoint}"
    Then I should receive response code "${statusCode}"
    And I should receive expected JSON object
      """
      ${jsonObject}
      """
    And Value of Id should match value of BatchId
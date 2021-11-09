Feature: Mirth API Test
  @dataFile:mirth_test_cases/mirth_conversion_test_cases.xls
  Scenario: JSON object test from Mirth API response
    Given HL7 message
      """
      ${hl7Message}
      """
    When I hit the mirth endpoint "${endpoint}"
    Then I should receive response code "${statusCode}"
    And I should receive JSON object
      """
      ${jsonObject}
      """
    And Value of Id should match value of BatchId
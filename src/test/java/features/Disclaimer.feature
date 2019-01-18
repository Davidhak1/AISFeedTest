Feature: AISFeedDownload

  Background:
    Given Initialization
    Given Operations Initialization
    Given Disclaimer Initialization
    Given Get the id of the latest successful feedRun for AIS_CA

  @AIS
  @AIS-DISCLAIMER
  Scenario: Validating disclaimer API
    Given the server endpoint is https://incentives.homenetiol.com/v2.6/CA/GetPrograms
    When adding following headers
      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
      | Content-Type | application/json                     |
    When perform the get request
    Then the response code should be 200

  @AIS
  @AIS-DISCLAIMER-1
  Scenario: Validating that all programs from response exist in our db 1
    Given the server endpoint is https://incentives.homenetiol.com/v2.6/CA/GetPrograms
    When adding following headers
      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
      | Content-Type | application/json                     |
    When perform the get request
    When fetch all the programIds from the response
    Then There should be no duplicate programIds in the json response
    Then we should have all the programs in our db
#    Then we should have a programDescription for every program in db



  @AIS
  @AIS-DISCLAIMER
  Scenario: Validating disclaimer API 2
    Given the server endpoint is https://incentives.homenetiol.com/v2.6/CA/GetPrograms
    When adding following headers
      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
      | Content-Type | application/json                     |
    When perform the get request
    Then the response code should be 200
    Then we should have a programDescription for every program in db

    @AIS
    @AIS-DISCLAIMER
    Scenario: Validating if the data in our db matches the data of the response
      Given the server endpoint is https://incentives.homenetiol.com/v2.6/CA/GetPrograms
      When adding following headers
        | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
        | Content-Type | application/json                     |
      When perform the get request
      When extract 20 random programDescriptions from json response
      Then find the programId in programDescription table
      Then the compatibleProgramsString should be the same in the response and db
      Then the consumer should be the same in the response and db
      Then the dealer should be the same in the response and db
      Then the short-title should be the same in the response and db
      Then the title should be the same in the response and db
      Then the taxStatus should be the same in the response and db


#Feature: AISFeedDownload
#
#
#  #---------RECOMMENDED------To run the ais feed batch before running the test------------------------------------------
#
#  Background:
#    Given Initialization
#    Given Operations Initialization
#    Given Disclaimer Initialization
#    Given Get the id of the latest successful feedRun for AIS_CA
#
#  @AIS
#  @AIS-DISCLAIMER
#  Scenario: Validating disclaimer API
#    Given the server endpoint is https://incentives.homenetiol.com/v2.6/CA/GetPrograms
#    When adding following headers
#      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
#      | Content-Type | application/json                     |
#    When perform the get request
#    Then the response code should be 200
#
#  @AIS
#  @AIS-DISCLAIMER
#  Scenario: Validating that all programs from response exist in db 1
#    Given the server endpoint is https://incentives.homenetiol.com/v2.6/CA/GetPrograms
#    When adding following headers
#      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
#      | Content-Type | application/json                     |
#    When perform the get request
#    When fetch all the programIds from the response
#    Then There should be no duplicate programIds in the json response
##    Then we should have all the programs in our db
##    Then the number of programs in json response should be less or equal to the number of programLocals in db
##    Then we should have a programLocal for every program in db
##    Then we should have a programLocalDescription for every programLocal in db
#
#
#  @AIS
#  @AIS-DISCLAIMER
#  Scenario: Validating if the data in db matches the data of the response (IMPORTANT TEST)
#    Given the server endpoint is https://incentives.homenetiol.com/v2.6/CA/GetPrograms
#    When adding following headers
#      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
#      | Content-Type | application/json                     |
#    When perform the get request
#    Then the response code should be 200
#    When extract 30 random programs from json response
#    Then the according programs should exist in ais_incentives db programDescription table
#    Then the compatibleProgramsString field should contain all programs that are in json response
#    Then the consumer field should be the same in the response and db
#    Then the dealer field should be the same in the response and db
#    Then the short-title field should be the same in the response and db
#    Then the title field should be the same in the response and db
#    Then the taxStatus field should be the same in the response and db

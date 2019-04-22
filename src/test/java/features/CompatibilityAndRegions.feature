Feature: Compatibility & RegionId Postal Codes


  @AIS
  @Compatibility
  @Compatibility-initial
  Scenario: Loading processed file for compatibility check
    Given Operations Initialization
    When Get the id of the latest SUCCESS feedRun for AIS_CA
    Then load all ais processed files
    Then load all ais files


  @AIS
  @Compatibility
  @Compatibility1
  Scenario Outline: Validating that all the incentives having compatibility are type 1
    Given Get the names of all files processed from ais
    When getting the xml file with index <index>
    When initCompatibility
    When get all nodes having compatibleIncentives tag
    Then unqiueId parent nodes of the nodes should be type cash
    Then all the compatibility uniqueIds should be cash
    Examples:
      | index |
      | 0     |
      | 1     |
      | 2     |
      | 3     |
      | 4     |


  @AIS
  @Compatibility
  @Compatibility2
  Scenario Outline: Validating compatibility with the same model and year
    Given Get the names of all files processed from ais
    Given getting the xml file with index <index>
    Given initCompatibility
    When get all nodes having compatibleIncentives tag
    Then model and year of the compatible nodes should be the same as the ones of parent uniqueId node
    Then all the compatibility uniqueIds should be cash
    Examples:
      | index |
      | 0     |
      | 1     |
      | 2     |
      | 3     |
      | 4     |


  @AIS
  @Compatibility
  @Compatibility3
  Scenario Outline: Validate that compatibleIncenentive table contains the right ammount of mappings
    Given Get the names of all files processed from ais
    Given getting the xml file with index <index>
    Given initCompatibility
    Given Operations Initialization
    When get all incentive nodes of the nodes having compability
    When get the amount of compatibleIncentives for all incentives from both xml file
    When get the amount of compatibleIncentives for all incentives from IMDB
    Then the incentives in the IMDB should have equal or less compatible incentives than in the xml file
    Examples:
      | index |
      | 0     |
      | 1     |
      | 2     |
      | 3     |
      | 4     |


  @AIS
  @Region
  @RegionIdPostalCodes2
  Scenario: Incentives Services WebService API test (making sure we get all the postalCodes that exist in the db for a regionId)
    Given Initialization
    Given initCompatibility
    When adding following headers
      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
      | Content-Type | application/json                     |
    When make a call for each make and save all distinct regionIds with their postalCodes in a map
      | VOLVO         |
      | VOLKSWAGEN    |
      | TOYOTA        |
      | SUBARU        |
      | SMART         |
      | SCION         |
      | RAM           |
      | PORSCHE       |
      | NISSAN        |
      | MITSUBISHI    |
      | MINI          |
      | MERCEDES-BENZ |
      | MAZDA         |
      | LINCOLN       |
      | LEXUS         |
      | LAND ROVER    |
      | KIA           |
      | JEEP          |
      | JAGUAR        |
      | INFINITI      |
      | HYUNDAI       |
      | HONDA         |
      | GMC           |
      | GENESIS       |
      | FORD          |
      | FIAT          |
      | DODGE         |
      | CHRYSLER      |
      | CHEVROLET     |
      | cadillac      |
      | BUICK         |
      | BMW           |
      | AUDI          |
      | ALFA ROMEO    |
      | ACURA         |
    When get regionId postalCode pairs from RegionIdZipCodes.json file
    Then the postal codes of all regionIds in the saved file should be present in the postal codes of the same regionId in the AIS response



  @AIS
  @Region
  @RegionIdPostalCodes3
  Scenario Outline: IncentiveAccountMap test, testing that we make published incentives only with right regionId:zipCode/AccountId mapping
    Given Get the names of all files processed from ais
    Given getting the xml file with index <index>
    Given initCompatibility
    Given Operations Initialization
    When get all incentive nodes
    When save all thirdPartyIncentiveId regionId maps with limit 20
    When change thirdpartyIncentiveIds to incentiveId if exist
    When get regionId accountId pairs from RegionIdZipCodes.json file
    Then all the incentives should be mapped only to accounts that are in RegionIdZipCodes.json file new
    Examples:
      | index |
      | 0     |
      | 1     |
      | 2     |
      | 3     |
      | 4     |
      | 5     |
      | 6     |
      | 7     |
      | 8     |
      | 9     |
      | 10    |





#  @AIS
#  @RegionLoader
#  @FiveStarCheck
#  @ItsGonnaBeHard
#  Scenario Outline: Validate that the regionId of all incentives mapped to the account contain the zip code of the account
#    Given Get the names of all files processed from ais
#    Given getting the xml file with index <index>
#    Given initCompatibility
#    Given Operations Initialization
#    When get all incentive nodes
#    When save all thirdPartyIncentiveId regionId maps with limit 100
#    When change thirdpartyIncentiveIds to incentiveId if exist
#    When get the list of postal codes of the accounts mapped to those incentives
#    When save the regionId postalCode mapping in interanl memory
#    Then the regionId of an incentive should contain the postalCodes of the accounts mapped to the incentive
#
#    Examples:
#      | index |
#      | 0     |
#      | 1     |
#      | 2     |
#      | 3     |
#      | 4     |




#  @AIS
#  @Region
#  @RegionIdPostalCodes2
#  Scenario: Incentives Services WebService API test (making sure we get all the postalCodes that exist in the db for a regionId)
#    Given Initialization
#    Given initCompatibility
#    When get the distinct postalCodes with the amount of regionIDs from the db
#    Then make a call to IS with each regionId we should get the same number of postalCodes in the response as in the db
#
#




#  @AIS
#  @Region
#  @RegionIdPostalCodes1
#  Scenario: Getting the number of postal codes by regionId with make, distinct regionIds and total pairs assertions included
#    Given Initialization
#    Given initCompatibility
#    When adding following headers
#      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
#      | Content-Type | application/json                     |
#    Then make a call for each make and print the number of postalCodes per regionId for a make
#      | VOLVO         |
#      | VOLKSWAGEN    |
#      | TOYOTA        |
#      | SUBARU        |
#      | SMART         |
#      | SCION         |
#      | RAM           |
#      | PORSCHE       |
#      | NISSAN        |
#      | MITSUBISHI    |
#      | MINI          |
#      | MERCEDES-BENZ |
#      | MAZDA         |
#      | LINCOLN       |
#      | LEXUS         |
#      | LAND ROVER    |
#      | KIA           |
#      | JEEP          |
#      | JAGUAR        |
#      | INFINITI      |
#      | HYUNDAI       |
#      | HONDA         |
#      | GMC           |
#      | GENESIS       |
#      | FORD          |
#      | FIAT          |
#      | DODGE         |
#      | CHRYSLER      |
#      | CHEVROLET     |
#      | cadillac      |
#      | BUICK         |
#      | BMW           |
#      | AUDI          |
#      | ALFA ROMEO    |
#      | ACURA         |
#    Then There should be euqal or more items in the db than the total # of items in ais response
#    Then there should be equal or more distinct regionIds in the db than in the ais response
#    Then each regionId should have equal amount of postalCodes mapped to it
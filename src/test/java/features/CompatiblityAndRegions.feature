Feature: Compatibility


  @AIS
  @Compatibility1
  Scenario Outline: Validating that all the incentives having compatibility are type 1
    Given get the xml file <path>
    Given initCompatibility
    When get all nodes having compatibleIncentives tag
    Then unqiueId parent nodes of the nodes should be type cash
    Then all the compatibility uniqueIds should be cash
    Examples:
      | path                                    |
      | src/main/java/resources/AIS_CA-FORD.xml |

  @Compatibility2
  Scenario Outline: Validating that all the incentives having compatibility with the same model and year
    Given get the xml file <path>
    Given initCompatibility
    When get all nodes having compatibleIncentives tag
    Then model and year of the compatible nodes should be the same as the ones of parent uniqueId node
    Then all the compatibility uniqueIds should be cash
    Examples:
      | path                                    |
      | src/main/java/resources/AIS_CA-FORD.xml |

  @Compatibility3
  Scenario Outline: Validate that compatibleIncenentive table contains the right ammount of mappings
    Given get the xml file <path>
    Given initCompatibility
    Given Operations Initialization
    When get all incentive nodes of the nodes having compability
    #    When get all incentive nodes
    When get the amount of compatibleIncentives for all incentives from both xml file
    When get the amount of compatibleIncentives for all incentives from IMDB
    Then the incentives in the IMDB should have equal or less compatible incentives than in the xml file

    Examples:
      | path                                    |
      | src/main/java/resources/AIS_CA-FORD.xml |

   @Region
  @RegionIdPostalCodes1
  Scenario: Getting the number of postal codes by regionId with make, distinct regionIds and total pairs assertions included
    Given Initialization
    Given initCompatibility
    When adding following headers
      | AIS-ApiKey   | 85C88437-7536-48FE-8914-4383CED65BA2 |
      | Content-Type | application/json                     |
    Then make a call for each make and print the number of postalCodes per regionId for a make
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
    Then There should be euqal or more items in the db than the total # of items in ais response
    Then there should be equal or more distinct regionIds in the db than in the ais response
    Then each regionId should have equal amount of postalCodes mapped to it

  @Region
  @RegionIdPostalCodes2
  Scenario: Incentives Services WebService API test (making sure we get all the postalCodes that exist in the db for a regionId)
    Given Initialization
    Given initCompatibility
    When get the distinct postalCodes with the amount of regionIDs from the db
    Then make a call to IS with each regionId we should get the same number of postalCodes in the response as in the db

Feature: Vehicle (V2) Controller Test

  Background:
    Given Initialization
    And the server endpoint is https://vehicles-qa.inv-np.dealer.com


    @vehicle-controller
  @Rest_Assured
  @Controller_1
  Scenario Outline: Endpoints {/vehicles,/vehicles/listingconfig }  - Validating response with request parameters - uuid, vin
    When adding api path for get request <endpoint>
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    When adding following parameters
      | uuid | e8b798e70a0a000201fa7440aa022025 |
      | vin  | 1C4PJLDS1FW732409                |
    And perform the get request
    Then the response code should be 200
    Then I should see json response with pairs on the filtered content node
      | uuid      | e8b798e70a0a000201fa7440aa022025 |
      | vin       | 1C4PJLDS1FW732409                |
      | accountId | carlgregorycdjcolumbuscllc       |

    Examples:
      | endpoint                |
      | /vehicles               |
#      | /vehicles/listingconfig |

  @vehicle-controller
  @Rest_Assured
  @Controller_1.1
  Scenario Outline: Endpoints {/vehicles,/vehicles/listingconfig } - Validating response with request parameters - uuid, vin, accountId
    When adding api path for get request <endpoint>
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    When adding following parameters
      | uuid      | e8b798e70a0a000201fa7440aa022025 |
      | vin       | 1C4PJLDS1FW732409                |
      | accountId | carlgregorycdjcolumbuscllc       |
    And perform the get request
    Then the response code should be 200
    Then I should see json response with pairs on the filtered content node
      | uuid      | e8b798e70a0a000201fa7440aa022025 |
      | vin       | 1C4PJLDS1FW732409                |
      | accountId | carlgregorycdjcolumbuscllc       |
    Examples:
      | endpoint                |
      | /vehicles               |
#      | /vehicles/listingconfig |


  @vehicle-controller
  @Rest_Assured
  @Controller_1.2
  Scenario Outline: Endpoints {/vehicles,/vehicles/listingconfig } - Validating response with request parameters - uuid
    When adding api path for get request <endpoint>
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    When adding following parameters
      | uuid | e8b798e70a0a000201fa7440aa022025 |
    And perform the get request
    Then the response code should be 200
    Then I should see json response with pairs on the filtered content node
      | uuid      | e8b798e70a0a000201fa7440aa022025 |
      | vin       | 1C4PJLDS1FW732409                |
      | accountId | carlgregorycdjcolumbuscllc       |
    Examples:
      | endpoint                |
      | /vehicles               |
#      | /vehicles/listingconfig |


  @vehicle-controller
  @Rest_Assured
  @Controller_1.3
  Scenario Outline: Endpoints {/vehicles,/vehicles/listingconfig } - Validating response with request parameters - vin, accountId
    When adding api path for get request <endpoint>
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    When adding following parameters
      | vin       | 1C4PJLDS1FW732409          |
      | accountId | carlgregorycdjcolumbuscllc |
    And perform the get request
    Then the response code should be 200
    Then I should see json response with pairs on the filtered content node
      | uuid      | e8b798e70a0a000201fa7440aa022025 |
      | vin       | 1C4PJLDS1FW732409                |
      | accountId | carlgregorycdjcolumbuscllc       |
    Examples:
      | endpoint                |
      | /vehicles               |
#      | /vehicles/listingconfig |


  @vehicle-controller
  @Rest_Assured
  @Controller_1.4
  Scenario Outline: Endpoints {/vehicles,/vehicles/listingconfig } - Validating response with request parameters - vin
    When adding api path for get request <endpoint>
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    When adding following parameters
      | vin | 1C4PJLDS1FW732409 |
    And perform the get request
    Then the response code should be 200
    Then I should see json response with pairs on the filtered content node
      | vin | 1C4PJLDS1FW732409 |
    Examples:
      | endpoint                |
      | /vehicles               |
#      | /vehicles/listingconfig |


  @vehicle-controller
  @Rest_Assured
  @Controller_1.5
  Scenario Outline: Endpoints {/vehicles,/vehicles/listingconfig } - Validating response with request parameters - accountId
    When adding api path for get request <endpoint>
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    When adding following parameters
      | accountId | carlgregorycdjcolumbuscllc |
    And perform the get request
    Then the response code should be 200
    Then I should see json response with pairs on the filtered content node
      | accountId | carlgregorycdjcolumbuscllc |
    Examples:
      | endpoint                |
      | /vehicles               |
#      | /vehicles/listingconfig |


  @vehicle-controller
  @Rest_Assured
  @Controller_1.6
  Scenario: Endpoint /vehicles/id/{uuid} - Validating response with uuid
    When adding api path for get request /vehicles/id
    When adding api path for get request /e8b798e70a0a000201fa7440aa022025
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    And perform the get request
    Then the response code should be 200
    Then I should see json response with pairs on the filtered $ node
      | uuid      | e8b798e70a0a000201fa7440aa022025 |
      | vin       | 1C4PJLDS1FW732409                |
      | accountId | carlgregorycdjcolumbuscllc       |


  @vehicle-controller
  @Rest_Assured
  @Controller_1.7
  Scenario: Endpoint /vehicles/id/{uuid} - Validating response with wrong uuid
    When adding api path for get request /vehicles/id
    When adding api path for get request /e8b798e70a0a000201fa7440aa022123
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    And perform the get request
    Then the response code should be 404


  @vehicle-controller
  @Rest_Assured
  @Controller_2.1
  Scenario Outline:Endpoints {/vehicles,/vehicles/listingconfig } Negative username
    When adding api path for get request <endpoint>
    When adding basic authentication vcdaa WhatAPr0j3ctPassw0rd!
    When adding following parameters
      | uuid | e8b798e70a0a000201fa7440aa022025 |
    And perform the get request
    Then the response code should be 401
    Examples:
      | endpoint                |
      | /vehicles               |
      | /vehicles/listingconfig |


  @vehicle-controller
  @Rest_Assured
  @Controller_2.2
  Scenario Outline:Endpoints {/vehicles,/vehicles/listingconfig } Negative password
    When adding api path for get request <endpoint>
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!!
    When adding following parameters
      | uuid | e8b798e70a0a000201fa7440aa022025 |
    And perform the get request
    Then the response code should be 401
    Examples:
      | endpoint                |
      | /vehicles               |
      | /vehicles/listingconfig |


  @vehicle-controller
  @Rest_Assured
  @Controller_2.3
  Scenario Outline:Endpoints {/vehicles,/vehicles/listingconfig } Negative wrong uuid
    When adding api path for get request <endpoint>
    When adding basic authentication vcda WhatAPr0j3ctPassw0rd!
    When adding following parameters
      | uuid | e8b798e70a0a000201fa7440aa022123 |
    And perform the get request
    Then the response code should be 204
    Examples:
      | endpoint                |
      | /vehicles               |
#      | /vehicles/listingconfig |

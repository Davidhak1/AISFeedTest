Feature: AISFeedDownload

  Background:
    Given Initialization
    Given Operations Initialization
    When Get the id of the latest successful feedRun for AIS_CA

    @AIS  
     @DB-Response-validation
  Scenario Outline: Validating that the feed fetches all eligible vehicles from Nexus vehicle table
    When Get the number of ais CA eligible vehicles for <accountId> and <make>
    When Get the number of aisIncentives with the latest feedRunId <accountId> and <make>
#    Then The count of aisIncentives should be the same as the number of eligible vehicles
    Examples:
      | accountId            | make       |
      | afternoondelightdemo | Alfa Romeo |
      | pwbmw                | BMW        |
      | winegardmotorstc     | Ford       |


     @AIS  
     @DB-Response-validation
 Scenario Outline: Validating the response code for a call
    Given the server endpoint is https://incentives.homenetiol.com/US/FindVehicleGroupsByVehicleAndPostalcode/
    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
    When adding following headers
      | AIS-ApiKey | 85C88437-7536-48FE-8914-4383CED65BA2 |
    When adding to the api path the vin and <zip> of the vehicle
    When perform the get request
    Then the response code should be 200

    Examples:
      | accountId            | make       | zip   |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |


      @AIS  
     @DB-Response-validation
Scenario Outline: Validating the data of vehicleGroup table
    Given the server endpoint is https://incentives.homenetiol.com/US/FindVehicleGroupsByVehicleAndPostalcode/
    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
    When adding following headers
      | AIS-ApiKey | 85C88437-7536-48FE-8914-4383CED65BA2 |
    When adding to the api path the vin and <zip> of the vehicle
    When perform the get request
    Then the response code should be 200
    Then the amount of vehicleGroups should be the same in the response and db
    Then the values of aisVehicleGroupIds should be the same in the response and db
    Then the values of vehicleGroupIds should be the same in the response and db
    Then the values of hashcodes should be the same in the response and db
    Then the values of modelYear should be the same in the response and db
    Then the values of marketingYear should be the same in the response and db
    Then the values of vehicleGroupNames should be the same in the response and db
    Then the values of regionID should be the same in the response and db

    Examples:
      | accountId            | make       | zip   |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |

#      | winegardmotorstc     | Ford       |



     @AIS  
     @DB-Response-validation
Scenario Outline: Validating the data of vehicleCode table
    Given the server endpoint is https://incentives.homenetiol.com/US/FindVehicleGroupsByVehicleAndPostalcode/
    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
    When adding following headers
      | AIS-ApiKey | 85C88437-7536-48FE-8914-4383CED65BA2 |
    When adding to the api path the vin and <zip> of the vehicle
    When perform the get request
    Then the response code should be 200
    Then the amount of vehicleGroups should be the same in the response and db
    Then get number 0 vehicleGroup of the response
    Then the amount of vehicleCodes with those vehicleGroupId should be the same in the response and db
    Then the acodes should be the same in the response and db
    Then the modelCodes should be the same in the response and db
    Then the styleIds should be the same in the response and db

    Examples:
      | accountId            | make       | zip   |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |


      @AIS  
     @DB-Response-validation
Scenario Outline: Validating the data of vehicleGroupMatchDetail table
    Given the server endpoint is https://incentives.homenetiol.com/US/FindVehicleGroupsByVehicleAndPostalcode/
    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
    When adding following headers
      | AIS-ApiKey | 85C88437-7536-48FE-8914-4383CED65BA2 |
    When adding to the api path the vin and <zip> of the vehicle
    When perform the get request
    Then the response code should be 200
    Then the amount of vehicleGroups should be the same in the response and db
    Then get number 0 vehicleGroup of the response
    Then the amount of vehicleMatchDetails with those vehicleGroupId should be the same in the response and db
    Then the vehicleElements should be the same in the response and db
    Then the vehicleHints should be the same in the response and db
    Then the valuesInVehicleGroup should be the same in the response and db
    Then the vehicleHintSources should be the same in the response and db
    Then the vehicleMatchStatuses should be the same in the response and db

    Examples:
      | accountId            | make       | zip   |
      | pwbmw                | BMW        | 15213 |
      | pwbmw                | BMW        | 15213 |


      @AIS  
     @DB-Response-validation
Scenario Outline: Validating the data of cashIncentive table
    Given the server endpoint is https://incentives.homenetiol.com/US/FindVehicleGroupsByVehicleAndPostalcode/
    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
    When adding following headers
      | AIS-ApiKey | 85C88437-7536-48FE-8914-4383CED65BA2 |
    When adding to the api path the vin and <zip> of the vehicle
    When perform the get request
    Then the response code should be 200
    Then the amount of vehicleGroups should be the same in the response and db
    Then get number 0 vehicleGroup of the response
    Then the amount of cashIncentives not having programId should be the same in the response and db
    Then the dealScenarioTypeIds should be the same in the response and db
    Then the cashNames under consumer cash should be the same in the db and json
    Then the cashTotals under consumer cash should be the same in the db and json
    Examples:
      | accountId            | make       | zip   |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |

#FOR VehicleGroup Row 2
#FOR VehicleGroup Row 2
#FOR VehicleGroup Row 2
#FOR VehicleGroup Row 2
#FOR VehicleGroup Row 2
#FOR VehicleGroup Row 2
#FOR VehicleGroup Row 2



      @AIS  
     @DB-Response-validation
Scenario Outline: Validating the data of vehicleCode table
    Given the server endpoint is https://incentives.homenetiol.com/US/FindVehicleGroupsByVehicleAndPostalcode/
    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
    When adding following headers
      | AIS-ApiKey | 85C88437-7536-48FE-8914-4383CED65BA2 |
    When adding to the api path the vin and <zip> of the vehicle
    When perform the get request
    Then the response code should be 200
    Then the amount of vehicleGroups should be the same in the response and db
    Then get number 1 vehicleGroup of the response
    Then the amount of vehicleCodes with those vehicleGroupId should be the same in the response and db
    Then the acodes should be the same in the response and db
    Then the modelCodes should be the same in the response and db
    Then the styleIds should be the same in the response and db

    Examples:
      | accountId            | make       | zip   |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |


     @AIS  
     @DB-Response-validation
 Scenario Outline: Validating the data of vehicleGroupMatchDetail table
    Given the server endpoint is https://incentives.homenetiol.com/US/FindVehicleGroupsByVehicleAndPostalcode/
    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
    When adding following headers
      | AIS-ApiKey | 85C88437-7536-48FE-8914-4383CED65BA2 |
    When adding to the api path the vin and <zip> of the vehicle
    When perform the get request
    Then the response code should be 200
    Then the amount of vehicleGroups should be the same in the response and db
    Then get number 1 vehicleGroup of the response
    Then the amount of vehicleMatchDetails with those vehicleGroupId should be the same in the response and db
    Then the vehicleElements should be the same in the response and db
    Then the vehicleHints should be the same in the response and db
    Then the valuesInVehicleGroup should be the same in the response and db
    Then the vehicleHintSources should be the same in the response and db
    Then the vehicleMatchStatuses should be the same in the response and db

    Examples:
      | accountId            | make       | zip   |
      | pwbmw                | BMW        | 15213 |
#      | afternoondelightdemo | Alfa Romeo | 75240 |
      | pwbmw                | BMW        | 15213 |
#      | afternoondelightdemo | Alfa Romeo | 75240 |


     @AIS  
     @DB-Response-validation
 Scenario Outline: Validating the data of cashIncentive table
    Given the server endpoint is https://incentives.homenetiol.com/US/FindVehicleGroupsByVehicleAndPostalcode/
    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
    When adding following headers
      | AIS-ApiKey | 85C88437-7536-48FE-8914-4383CED65BA2 |
    When adding to the api path the vin and <zip> of the vehicle
    When perform the get request
    Then the response code should be 200
    Then the amount of vehicleGroups should be the same in the response and db
    Then get number 1 vehicleGroup of the response
    Then the amount of cashIncentives not having programId should be the same in the response and db
    Then the dealScenarioTypeIds should be the same in the response and db
    Then the cashNames under consumer cash should be the same in the db and json
    Then the cashTotals under consumer cash should be the same in the db and json
    Examples:
      | accountId            | make       | zip   |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |
      | pwbmw                | BMW        | 15213 |
      | afternoondelightdemo | Alfa Romeo | 75240 |


Feature: Download

  Background:
    Given Initialization
    Given Download Initialization
    Given Operations Initialization


  @AIS
  @DB-Download-validation
  Scenario: Load all ais files for the latest feedRunId and make sure we create a file for each subscribed make
    When Get the id of the latest ABORTED feedRun for AIS_CA
    When load all ais files
    When Get the names of all files downloaded from ais
    Then files with following names should be there
      | VinsWithMoreThanTwoIncentives.json |
      | VinsWithNoIncentives.json          |
      | VehicleHints.json                  |
      | ProgramDetails.json                |


  @AIS
  @DB_Download-validation
  Scenario:Test that we make a call to ais for all the eligible vehicles
    When Get the number of ais CA eligible vehicles for all subscribed accounts and makes from Nexus
    When Get the names of all make files downloaded from ais
    Then we should have a json file for each AIS subscribed make
    When Get the total number of vehicles from the files
    Then The difference between the totals of vehicles should be less than 25


  @AIS
  @DB_Download-validation
  Scenario:Test that vins in vinsWithNoVehicleGroups file return empty response from AIS
    When Get all vins from vinsWithNoIncentives.json
    When make a post request ready for AIS
    When Save vehicleHints for those vins with 100 pagination
    Then there should not be any vehicleGroups in the response


  @AIS
  @DB_Download-validation
  Scenario:Test that vins in VinsWithMoreThanTwoVehicleGroup file return more than two vehicleGroups from AIS
    When Get all vins from vinsWithMoreThanTwoIncentives.json
    When make a post request ready for AIS
    When Save vehicleHints for those vins with 100 pagination
    Then each vin should have more than 2 vehicleGroups


  @AIS
  @DB_Download-validation
  Scenario:Test that we don't save data that we are not going to use
    When Get the names of all make files downloaded from ais
    Then we should not have any data with the following fields
      | vehicleGroupMatchDetails |
      | tags                     |
      | maxCertificateQty        |
      | residuals                |
      | dealerSpecials           |
      | dealerCash               |
      | totalConsumerCash        |


  @AIS
  @DB_Download-validation
  Scenario:Test that we don't have any duplicate vehicleGroups in the files
    When Get the names of all make files downloaded from ais
    When Get the vehicleGroups from the files
    Then we should not have duplicate vehicleGroupIds in the files


  @AIS
  @DB_Download-validation
  Scenario:Test that vehicleGroupsVin and vinVehicleGroups fields contsin the same amount of mapping in each file
    When Get the names of all make files downloaded from ais
    When Get the number of unique mappings from vehicleGroupVins field
    When Get the number of unique mappings from vinVehicleGroups field
    Then the total Number of mappings should be equalsIgnoreCase

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
    When Get all vins from vinsWithNoVehicleFile
    When Save vehicleHints for thiose vins
    Then AIS should send empty response when we make a call with those vins and hints


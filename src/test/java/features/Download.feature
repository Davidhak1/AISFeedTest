Feature: Download

  Background:
    Given Initialization
    Given Download Initialization
    Given Operations Initialization


  @AIS
  @DB-Response-validation
  Scenario: Load all ais files for the latest feedRunId
    When Get the id of the latest successful feedRun for AIS_CA
    When load all ais files

   @AIS
   @DB_Response-validation
   Scenario:Test that we make a call to ais for all the eligible vehicles
     When Get the number of ais CA eligible vehicles for all subscribed accounts and makes from Nexus
     When Get the names of all make files downloaded from ais
     When Get the total number of vehicles from the files

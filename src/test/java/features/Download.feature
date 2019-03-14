Feature: Download

  Background:
    Given Initialization
    Given Operations Initialization

  @AIS
  @DB-Response-validation
  Scenario: Load all ais files for the latest feedRunId
    When Get the id of the latest successful feedRun for AIS_CA
    When load all ais files
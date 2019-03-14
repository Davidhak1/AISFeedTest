Feature: Download

  Background:
    Given Initialization
    Given Operations Initialization
    When Get the id of the latest successful feedRun for AIS_CA

  @AIS
  @DB-Response-validation
  Scenario Outline: Validating that the feed fetches all eligible vehicles from Nexus vehicle table
    When Get the number of ais CA eligible vehicles for <accountId> and <make>
    When Get the number of vehicles having vehicleGroup in DB with the latest feedRunId <accountId> and <make>
    Examples:
      | accountId            | make       |
      | afternoondelightdemo | Alfa Romeo |
      | pwbmw                | BMW        |
      | winegardmotorstc     | Ford       |

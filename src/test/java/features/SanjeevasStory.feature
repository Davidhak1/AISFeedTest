Feature: Endpoint for saving mapping of vehicle and incentives in IMDB '


  Background:
    Given Initialization
    Given Operations Initialization

  @AIS-Depreciated
  @Incentives-Services-API
  Scenario Outline: Validating if the endpoint is up and running
    Given the server endpoint is http://vtqainv-incentivessvc01.dealer.ddc:9620/incentives-services/rest/api/v1/AisIncentivesService/insert/
    When Get the id of the latest successful feedRun for AIS_CA
    When adding to the api path <feedRunId>
    When adding api body for post requst with below details
      | uuid         | u1               |
      | incentiveIds | ["i1","i2","i3"] |
    When perform the post request
    Then we should have a row with the uuid and feedRunId we sent
    Then we should have the incentives and the aisVehicleFeedId in aisVehicleThirdPartyIncenitves

    Then the response code should be 200

    Examples:
      | feedRunId |
      | fri123    |



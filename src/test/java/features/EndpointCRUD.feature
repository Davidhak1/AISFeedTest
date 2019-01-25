Feature: Endpoint for CRUD Operations


# --------------------------------------------------------------------DEPRECIATED--------------------------------------------------------------------



#  Background:
#    Given Initialization
#    Given Operations Initialization
#
#  @AIS-Depreciated
#  @Incentives-Services-API
#  Scenario Outline: Validating if the endpoint is up and running
#    Given the server endpoint is http://vtqainv-incentivessvc01.dealer.ddc:9620/incentives-services/rest/api/v1/aisRawIncentives/retrieveIncentivesByVin
#    When Get the id of the latest successful feedRun for AIS_CA
#    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
#    When adding to the api path the vin of the vehicle and the latestFeedRunId
#    When perform the get request
#    Then the response code should be 200
#
#    Examples:
#      | accountId            | make       |
#      | afternoondelightdemo | Alfa Romeo |
#      | pwbmw                | BMW        |
#
#  @AIS-Depreciated
#  @Incentives-Services-API
#  Scenario Outline: Validating if the endpoint is returning the right data
#    Given the server endpoint is http://vtqainv-incentivessvc01.dealer.ddc:9620/incentives-services/rest/api/v1/aisRawIncentives/retrieveIncentivesByVin
#    When Get the id of the latest successful feedRun for AIS_CA
#    When Get a random aisIncentive with the latest feedRunId <accountId> and <make>
#    When adding to the api path the vin of the vehicle and the latestFeedRunId
#    When perform the get request
#    Then the response code should be 200
#    Then the vin in the response should be the same as in the requested url
#
#    Examples:
#      | accountId            | make       |
#      | afternoondelightdemo | Alfa Romeo |
#      | pwbmw                | BMW        |


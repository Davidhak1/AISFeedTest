package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Ca;
import io.restassured.path.json.JsonPath;
import model.*;
import org.testng.Assert;
import resources.base;

import java.util.List;
import java.util.Random;

public class OperationsStepDef extends base {
//    ResponseHolder responseHolder;
//    Response response;
//    RequestSpecification request;
//    Map<String, Object> responseMap;
//    ArrayList<HashMap<String, String>> responseMapArray;
//    Map<String, String> body;
//    List<String> bodyLikeArray;
//    private String url;

    //    private String feedRunId;
//    private int eligibleCo"eligibleCo?vesCount/?/;
    private static int numberOfVehicleGroups;
    private static int numberOfVehicleCodes;
    private static int numberOfVehicleMatchDetails;
    private static int numberOfCashIncentives;

    private static VehicleGroup vehicleGroup;
    private static int number;


    @Given("^Oper Initialization$")
    public void operInitialization() {
        System.out.println("INSIDE OPERINIT ---------------");
        initBase();

    }


    @When("^Get the id of the latest successful feedRun for AIS_CA$")
    public void getTheIdOfTheLatestSuccessfulFeedRunForAIS_CA() {
        setFeedRunId(q_c.getTheLatestSuccessfulAISFeedRunIDThatHasRecordsInAISIncentiveTable());
        System.out.println("Latest Successfull feedRunID = " + getFeedRunId());
        System.out.println();

    }

    @When("^Get the number of ais CA eligible vehicles for (.+) and (.+)$")
    public void getTheNumberOfAisCAEligibleVehiclesForThirdPartyIdAndMake(String thirdPartyId, String make) {
        setEligibleCount(q_n.getNumberOfVehiclesWithAccountIdOemAndNewAndNotRemoved(thirdPartyId, make));
        System.out.println("eligibleCount = " + getEligibleCount());
        System.out.println();

    }

    @When("^Get the number of aisIncentives with the latest feedRunId (.+) and (.+)$")
    public void getTheNumberOfAisIncentivesWithTheLatestFeedRunIdAccountIdAndMake(String accountId, String make) {
        setAisIncentivesCount(q_a.numberOfAisIncentivesWithFeedRunIDAndAcccountIdAndMake(getFeedRunId(), accountId, make));
        System.out.println("aisIncentivesCount = " + getAisIncentivesCount());
        System.out.println();


    }

    @Then("^The count of aisIncentives should be the same as the number of eligible vehicles$")
    public void theCountOfAisIncentivesShouldBeTheSameAsTheNumberOfEligibleVehicles() {
        Assert.assertEquals(getEligibleCount(), getAisIncentivesCount(), String.format("The count of aisIncentives:%s is not equal to " +
                "the actual count of eligibleVehicles: %s", getAisIncentivesCount(), getEligibleCount()));
    }

    @When("^Get a random aisIncentive with the latest feedRunId (.+) and (.+)$")
    public void getARandomAisIncentiveWithTheLatestFeedRunIdAccountIdAndMake(String accountId, String make) {
        setAisIncentives(q_a.getAisIncentivesByFeedRunIdAccountIdAndMake(getFeedRunId(), accountId, make));

        System.out.printf("%d records found in the db matching the search 'feedRunId' = %s, 'accountId' = %s, 'make' = %s.%n", getAisIncentives().size(), getFeedRunId(), accountId, make);
        AISIncentive random =null;
        while(true) {
            random = getAisIncentives().get(new Random().nextInt(getAisIncentives().size()));
            System.out.println("Random aisIncentive chosen: " + random);
            if(q_a.getNumberOfVehicleGroupsByAISInentiveId(random.getId())>1)
                break;
            else
                System.out.println("IMPORTANT!!!!!! the vehicle above doesn't have vehicleGroups ---->>> SKIPPING ");
        }
        setAisIncentive(random);
//        setAisIncentive(q_a.getAisIncentivesById(506425));

    }

    @Then("^the amount of vehicleGroups should be the same in the response and db$")
    public void theNumberOfVehicleGroupsShouldBeTheSameInTheResponseAndDb() {
        int jsonLenght = responseHolder.lengthOfArray("response");
        int dbLength = q_a.getNumberOfVehicleGroupsByAISInentiveId(getAisIncentive().getId());

        System.out.println(jsonLenght + " = " + dbLength);
        Assert.assertEquals(dbLength, jsonLenght, String.format("The number of vehicleGroups for 'aisIncentiveId' = '%s' in db is not " +
                "equal the number in json response. DB: %s, Json: %s", getAisIncentive().getId(), dbLength, jsonLenght));
        numberOfVehicleGroups = jsonLenght;
    }

    @Then("^the amount of vehicleCodes with those vehicleGroupId should be the same in the response and db$")
    public void theAmountOfVehicleCodesWithThoseVehicleGroupIdShouldBeTheSameInTheResponseAndDb() {
        int jsonLenght = responseHolder.lengthOfArray(String.format("response[%d].vehicleCodes", number));
        int dbLength = q_a.getNumberOfVehicleCodesByVehicleGroupId(vehicleGroup.getId());

        System.out.println(jsonLenght + " = " + dbLength);
        Assert.assertEquals(dbLength, jsonLenght, String.format("The number of vehicleCodes for 'vehicleGroupid' = '%s' in db is not " +
                "equal the number in json response. DB: %s, Json: %s", vehicleGroup.getId(), dbLength, jsonLenght));
        numberOfVehicleCodes = jsonLenght;
    }

    @Then("^the amount of vehicleMatchDetails with those vehicleGroupId should be the same in the response and db$")
    public void theAmountOfVehicleMatchDetailsWithThoseVehicleGroupIdShouldBeTheSameInTheResponseAndDb() {
        int jsonLenght = responseHolder.lengthOfArray(String.format("response[%d].vehicleGroupMatchDetails", number));
        int dbLength = q_a.getNumberOfVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroup.getId());

        System.out.println(jsonLenght + " = " + dbLength);
        Assert.assertEquals(dbLength, jsonLenght, String.format("The number of VehicleGroupMatches for 'vehicleGroupid' = '%s' in db is not " +
                "equal the number in json response. DB: %s, Json: %s", vehicleGroup.getId(), dbLength, jsonLenght));
        numberOfVehicleMatchDetails = jsonLenght;
    }

    @Then("^the amount of cashIncentives not having programId should be the same in the response and db$")
    public void theAmountOfCashIncentivesProgramIdNullShouldBeTheSameInTheResponseAndDb() {
        int jsonLenght = responseHolder.lengthOfArray(String.format("response[%d].cashDealScenarios", number));
        int dbLength = q_a.getNumberOfCashIncentivesByVehicleGroupId(vehicleGroup.getId());

        System.out.println(jsonLenght + " = " + dbLength);
        Assert.assertEquals(dbLength, jsonLenght, String.format("The number of cashIncentives for 'vehicleGroupid' = '%s' in db is not " +
                "equal the number in json response. DB: %s, Json: %s", vehicleGroup.getId(), dbLength, jsonLenght));
        numberOfCashIncentives = jsonLenght;
    }

    @Then("^the values of aisVehicleGroupIds should be the same in the response and db$")
    public void theValuesOfAisVehicleGroupIdsShouldBeTheSameInTheResponseAndDb() {

        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveId(getAisIncentive().getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
        String jsonAisVehicleGroupId = null;
        for (int i = 0; i < numberOfVehicleGroups; i++) {
            boolean flag = false;
            for (VehicleGroup dbvg : dbVehicleGroups) {
                System.out.println("\n" + i + ") db vehicleGroupId:" + dbvg.getAisVehicleGroupId());
                jsonAisVehicleGroupId = responseJsonPath.get(String.format("response[%d].aisVehicleGroupID", i));
                System.out.println(i + ") js vehicleGroupId:" + jsonAisVehicleGroupId);
                if (dbvg.getAisVehicleGroupId().equalsIgnoreCase(jsonAisVehicleGroupId)) {
                    dbVehicleGroups.remove(dbvg);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the aisVehicleGroupIds (particularly:%s) in the json for aisIncentiveId = ", jsonAisVehicleGroupId, getAisIncentive().getId()));
            System.out.println();
        }
    }

    @Then("^the values of vehicleGroupIds should be the same in the response and db$")
    public void theValuesOfVehicleGroupIdsShouldBeTheSameInTheResponseAndDb() {

        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveId(getAisIncentive().getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
        int jsonVehicleGroupId = 0;
        for (int i = 0; i < numberOfVehicleGroups; i++) {
            boolean flag = false;
            for (VehicleGroup dbvg : dbVehicleGroups) {
                System.out.println("\n" + i + ") db vehicleGroupId:" + dbvg.getVehicleGroupId());
                jsonVehicleGroupId = responseJsonPath.get(String.format("response[%d].vehicleGroupID", i));
                System.out.println(i + ") js vehicleGroupId:" + jsonVehicleGroupId);
                if (dbvg.getVehicleGroupId() == jsonVehicleGroupId) {
                    dbVehicleGroups.remove(dbvg);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleGroupIds (particularly:%d) in the json for aisIncentiveId = ", jsonVehicleGroupId, getAisIncentive().getId()));
            System.out.println();
        }
    }

    @Then("^the values of vehicleGroupNames should be the same in the response and db$")
    public void theValuesOfvehicleGroupNamesShouldBeTheSameInTheResponseAndDb() {

        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveId(getAisIncentive().getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
        String jsonVehicleGroupName = null;
        for (int i = 0; i < numberOfVehicleGroups; i++) {
            boolean flag = false;
            for (VehicleGroup dbvg : dbVehicleGroups) {
                System.out.println("\n" + i + ") db vehicleGroupName:" + dbvg.getVehicleGroupName());
                jsonVehicleGroupName = responseJsonPath.get(String.format("response[%d].vehicleGroupName", i));
                System.out.println(i + ") js vehicleGroupName:" + jsonVehicleGroupName);
                if (dbvg.getVehicleGroupName().equalsIgnoreCase(jsonVehicleGroupName)) {
                    dbVehicleGroups.remove(dbvg);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleGroupNames (particularly:%s) in the json for aisIncentiveId = ", jsonVehicleGroupName, getAisIncentive().getId()));
            System.out.println();

        }
    }

    @Then("^the values of modelYear should be the same in the response and db$")
    public void theValuesOfModelYearsShouldBeTheSameInTheResponseAndDb() {

        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveId(getAisIncentive().getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
        int jsonModelYear = 0;
        for (int i = 0; i < numberOfVehicleGroups; i++) {
            boolean flag = false;
            for (VehicleGroup dbvg : dbVehicleGroups) {
                System.out.println("\n" + i + ") db modelYear:" + dbvg.getModelYear());
                jsonModelYear = responseJsonPath.get(String.format("response[%d].modelYear", i));
                System.out.println(i + ") js modelYear:" + jsonModelYear);
                if (dbvg.getModelYear() == jsonModelYear) {
                    dbVehicleGroups.remove(dbvg);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the modelYears (particularly:%d) in the json for aisIncentiveId = ", jsonModelYear, getAisIncentive().getId()));
            System.out.println();
        }
    }

    @Then("^the values of marketingYear should be the same in the response and db$")
    public void theValuesOfMarketingYearsShouldBeTheSameInTheResponseAndDb() {

        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveId(getAisIncentive().getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
        int jsonMarketingYear = 0;
        for (int i = 0; i < numberOfVehicleGroups; i++) {
            boolean flag = false;
            for (VehicleGroup dbvg : dbVehicleGroups) {
                System.out.println("\n" + i + ") db marketingYear:" + dbvg.getMarketingYear());
                jsonMarketingYear = Integer.parseInt(responseJsonPath.get(String.format("response[%d].marketingYear", i)));
                System.out.println(i + ") js marketingYear:" + jsonMarketingYear);
                if (dbvg.getMarketingYear() == jsonMarketingYear) {
                    dbVehicleGroups.remove(dbvg);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the marketingYears (particularly:%d) in the json for aisIncentiveId = ", jsonMarketingYear, getAisIncentive().getId()));
            System.out.println();
        }
    }

    @Then("^the values of regionID should be the same in the response and db$")
    public void theValuesOfRegionIDShouldBeTheSameInTheResponseAndDb() {

        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveId(getAisIncentive().getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
        int jsonRegionID = 0;
        for (int i = 0; i < numberOfVehicleGroups; i++) {
            boolean flag = false;
            for (VehicleGroup dbvg : dbVehicleGroups) {
                System.out.println("\n" + i + ") db regionID:" + dbvg.getRegionId());
                jsonRegionID = responseJsonPath.get(String.format("response[%d].regionID", i));
                System.out.println(i + ") js regionID:" + jsonRegionID);
                if (dbvg.getRegionId() == jsonRegionID) {
                    dbVehicleGroups.remove(dbvg);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the regionIDs (particularly:%d) in the json for aisIncentiveId = ", jsonRegionID, getAisIncentive().getId()));
            System.out.println();
        }
    }

    @Then("^the values of hashcodes should be the same in the response and db$")
    public void theValuesOfHashcodesShouldBeTheSameInTheResponseAndDb() {

        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveId(getAisIncentive().getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
        String jsonHashcode = null;
        for (int i = 0; i < numberOfVehicleGroups; i++) {
            boolean flag = false;
            for (VehicleGroup dbvg : dbVehicleGroups) {
                System.out.println("\n" + i + ") db hashcode:" + dbvg.getHash());
                jsonHashcode = responseJsonPath.get(String.format("response[%d].hashcode", i));
                System.out.println(i + ") js hashcode:" + jsonHashcode);
                if (dbvg.getHash().equalsIgnoreCase(jsonHashcode)) {
                    dbVehicleGroups.remove(dbvg);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the hashcodes (particularly:%s) in the json for aisIncentiveId = ", jsonHashcode, getAisIncentive().getId()));
            System.out.println();

        }
    }

    @Then("^the acodes should be the same in the response and db$")
    public void theAcodesShouldBeTheSameInTheResponseAndDb() {
        List<VehicleCode> dbVehicleCodes = q_a.getVehicleCodesByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nACode Check");
        String jsonAcode = null;
        for (int i = 0; i < numberOfVehicleCodes; i++) {
            boolean flag = false;
            for (VehicleCode dbvc : dbVehicleCodes) {
                System.out.println("\n" + i + ") db acode:" + dbvc.getAcode());
                jsonAcode = responseJsonPath.get(String.format("response[%d].vehicleCodes[%d].acode", number, i));
                System.out.println(i + ") js acode:" + jsonAcode);
                if (dbvc.getAcode().equalsIgnoreCase(jsonAcode)) {
                    dbVehicleCodes.remove(dbvc);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the acodes (particularly:%s) in the json for vehicleGroupId = %d ", jsonAcode, vehicleGroup.getId()));
            System.out.println();
        }

    }

    @Then("^the modelCodes should be the same in the response and db$")
    public void theModelCodesShouldBeTheSameInTheResponseAndDb() {
        List<VehicleCode> dbVehicleCodes = q_a.getVehicleCodesByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nModelCode Check");
        String jsonModelCode = null;
        for (int i = 0; i < numberOfVehicleCodes; i++) {
            boolean flag = false;
            for (VehicleCode dbvc : dbVehicleCodes) {
                System.out.println("\n" + i + ") db modelCode:" + dbvc.getModelCode());
                jsonModelCode = responseJsonPath.get(String.format("response[%d].vehicleCodes[%d].modelCode", number, i));
                System.out.println(i + ") js modelCode:" + jsonModelCode);
                if (dbvc.getModelCode().equalsIgnoreCase(jsonModelCode)) {
                    dbVehicleCodes.remove(dbvc);
                    flag = true;
                    break;
                }

            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the modelCodes (particularly:%s) in the json for vehicleGroupId = %d ", jsonModelCode, vehicleGroup.getId()));
            System.out.println();
        }

    }

    @Then("^the styleIds should be the same in the response and db$")
    public void theStyleIdsShouldBeTheSameInTheResponseAndDb() {
        List<VehicleCode> dbVehicleCodes = q_a.getVehicleCodesByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nStyleID Check");
        int jsonStyleId = 0;
        for (int i = 0; i < numberOfVehicleCodes; i++) {
            boolean flag = false;
            for (VehicleCode dbvc : dbVehicleCodes) {
                System.out.println("\n" + i + ") db styleID:" + dbvc.getStyleID());
                jsonStyleId = responseJsonPath.get(String.format("response[%d].vehicleCodes[%d].styleID", number, i));
                System.out.println(i + ") js styleID:" + jsonStyleId);
                if (dbvc.getStyleID() == jsonStyleId) {
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the styleIds (particularly:%d) in the json for vehicleGroupId = %d ", jsonStyleId, vehicleGroup.getId()));
            System.out.println();
        }

    }

    @Then("^the vehicleElements should be the same in the response and db$")
    public void theVehicleElementsShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nvehicleElement Check");
        String jsonVehicleElement = null;
        for (int i = 0; i < numberOfVehicleMatchDetails; i++) {
            boolean flag = false;
            for (VehicleGroupMatchDetail dbvgmd : dbVehicleGMD) {
                System.out.println("\n" + i + ") db vehicleElement:" + dbvgmd.getVehicleElement());
                jsonVehicleElement = responseJsonPath.get(String.format("response[%d].vehicleGroupMatchDetails[%d].vehicleElement", number, i));
                System.out.println(i + ") js vehicleElement:" + jsonVehicleElement);
                if (dbvgmd.getVehicleElement().equalsIgnoreCase(jsonVehicleElement)) {
                    dbVehicleGMD.remove(dbvgmd);
                    flag = true;
                    break;
                }

            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleElement (particularly:%s) in the json for vehicleGroupId = %d ", jsonVehicleElement, vehicleGroup.getId()));
            System.out.println();
        }

    }

    @Then("^the vehicleHints should be the same in the response and db$")
    public void theVehicleHintShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nvehicleHint Check");
        String jsonVehicleHint = null;
        for (int i = 0; i < numberOfVehicleMatchDetails; i++) {
            boolean flag = false;
            for (VehicleGroupMatchDetail dbvgmd : dbVehicleGMD) {
                System.out.println("\n" + i + ") db vehicleHint:" + dbvgmd.getVehicleHint());
                jsonVehicleHint = responseJsonPath.get(String.format("response[%d].vehicleGroupMatchDetails[%d].vehicleHint", number, i));
                System.out.println(i + ") js vehicleHint:" + jsonVehicleHint);
                if (dbvgmd.getVehicleHint().equalsIgnoreCase(jsonVehicleHint)) {
                    dbVehicleGMD.remove(dbvgmd);
                    flag = true;
                    break;
                }

            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleHint (particularly:%s) in the json for vehicleGroupId = %d ", jsonVehicleHint, vehicleGroup.getId()));
            System.out.println();
        }
    }

    @Then("^the valuesInVehicleGroup should be the same in the response and db$")
    public void theValuesInVehicleGroupShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nvaluesInVehicleGroup Check");
        String jsonValuesInVehicleGroup = null;
        for (int i = 0; i < numberOfVehicleMatchDetails; i++) {
            boolean flag = false;
            for (VehicleGroupMatchDetail dbvgmd : dbVehicleGMD) {
                System.out.println("\n" + i + ") db valuesInVehicleGroup:" + dbvgmd.getValuesVehicleGroup());
                jsonValuesInVehicleGroup = responseJsonPath.get(String.format("response[%d].vehicleGroupMatchDetails[%d].valuesInVehicleGroup", number, i));
                System.out.println(i + ") js valuesInVehicleGroup:" + jsonValuesInVehicleGroup);
                if (dbvgmd.getValuesVehicleGroup().equalsIgnoreCase(jsonValuesInVehicleGroup)) {
                    dbVehicleGMD.remove(dbvgmd);
                    flag = true;
                    break;
                }

            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the valuesInVehicleGroup (particularly:%s) in the json for vehicleGroupId = %d ", jsonValuesInVehicleGroup, vehicleGroup.getId()));
            System.out.println();
        }
    }

    @Then("^the vehicleHintSources should be the same in the response and db$")
    public void theVehicleHintSourcesShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nvehicleHintSource Check");
        String jsonVehicleHintSource = null;
        for (int i = 0; i < numberOfVehicleMatchDetails; i++) {
            boolean flag = false;
            for (VehicleGroupMatchDetail dbvgmd : dbVehicleGMD) {
                System.out.println("\n" + i + ") db vehicleHintSource:" + dbvgmd.getVehicleHintSourse());
                jsonVehicleHintSource = responseJsonPath.get(String.format("response[%d].vehicleGroupMatchDetails[%d].vehicleHintSource", number, i));
                System.out.println(i + ") js vehicleHintSource:" + jsonVehicleHintSource);
                if (dbvgmd.getVehicleHintSourse().equalsIgnoreCase(jsonVehicleHintSource)) {
                    dbVehicleGMD.remove(dbvgmd);
                    flag = true;
                    break;
                }

            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleHintSource (particularly:%s) in the json for vehicleGroupId = %d ", jsonVehicleHintSource, vehicleGroup.getId()));
            System.out.println();
        }
    }

    @Then("^the vehicleMatchStatuses should be the same in the response and db$")
    public void theVehicleMatchStatusesShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nmatchStatus Check");
        String jsonMatchStatus = null;
        for (int i = 0; i < numberOfVehicleMatchDetails; i++) {
            boolean flag = false;
            for (VehicleGroupMatchDetail dbvgmd : dbVehicleGMD) {
                System.out.println("\n" + i + ") db matchStatus:" + dbvgmd.getMatchStatus());
                jsonMatchStatus = responseJsonPath.get(String.format("response[%d].vehicleGroupMatchDetails[%d].matchStatus", number, i));
                System.out.println(i + ") js matchStatus:" + jsonMatchStatus);
                if (dbvgmd.getMatchStatus().equalsIgnoreCase(jsonMatchStatus)) {
                    dbVehicleGMD.remove(dbvgmd);
                    flag = true;
                    break;
                }

            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the matchStatuses (particularly:%s) in the json for vehicleGroupId = %d ", jsonMatchStatus, vehicleGroup.getId()));
            System.out.println();
        }
    }

    @Then("^the dealScenarioTypeIds should be the same in the response and db$")
    public void theDealScenarioTypeIdsShouldBeTheSameInTheResponseAndDb() {
        List<CashIncentive> dbCashIncentives = q_a.getCashIncentivesByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\ndealScenarioTypeId Check");
        int jsonDealScenarioTypeId = 0;
        for (int i = 0; i < numberOfCashIncentives; i++) {
            boolean flag = false;
            for (CashIncentive dbci : dbCashIncentives) {
                System.out.println("\n" + i + ") db dealScenarioTypeId:" + dbci.getDealScenarioTypeId());
                jsonDealScenarioTypeId = responseJsonPath.get(String.format("response[%d].cashDealScenarios[%d].dealScenarioTypeID", number, i));
                System.out.println(i + ") js dealScenarioTypeId:" + jsonDealScenarioTypeId);
                if (dbci.getDealScenarioTypeId() == jsonDealScenarioTypeId) {

                    dbCashIncentives.remove(dbci);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the dealScenarioTypeIds (particularly:%d) in the json for vehicleGroupId = %d ", jsonDealScenarioTypeId, vehicleGroup.getId()));
            System.out.println();
        }
    }

    @Then("^the cashNames under consumer cash should be the same in the db and json$")
    public void theCashNamesUnderConsumerCashShouldBeTheSameInTheDbAndJson() {
        List<CashIncentive> dbCashIncentives = q_a.getCashIncentivesByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\ncashName Check");
        String jsonCashName = null;
        for (int i = 0; i < numberOfCashIncentives; i++) {
            boolean flag = false;
            for (CashIncentive dbci : dbCashIncentives) {
                System.out.println("\n" + i + ") db cashName:" + dbci.getCashName());
                jsonCashName = responseJsonPath.get(String.format("response[%d].cashDealScenarios[%d].consumerCash.consumerCashName", number, i));
                System.out.println(i + ") js cashName:" + jsonCashName);
                if (dbci.getCashName().equalsIgnoreCase(jsonCashName)) {

                    dbCashIncentives.remove(dbci);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the cashNames (particularly:%s) in the json for vehicleGroupId = %s ", jsonCashName, vehicleGroup.getId()));
            System.out.println();
        }
    }

    @Then("^get number (\\d+) vehicleGroup of the response$")
    public void getTheFirstVehicleGroupIdOfTheResponse(int number) {
        OperationsStepDef.number = number;
        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveId(getAisIncentive().getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
        String jsonAisVehicleGroupId = responseJsonPath.get(String.format("response[%d].aisVehicleGroupID", number));
        for (VehicleGroup vgdb : dbVehicleGroups) {
            if (vgdb.getAisVehicleGroupId().equalsIgnoreCase(jsonAisVehicleGroupId)) {
                vehicleGroup = vgdb;
            }
        }
        if (vehicleGroup == null) {
            System.out.println(String.format("\n\n!IMPORTANT! No vehicleGroups found in db for 'aisIncentiveId' = %s " +
                    "and with aisVehicleGroupid = %s", getAisIncentive().getId(), jsonAisVehicleGroupId));

        }
        if (number == 1)
            System.out.println(String.format("%nnumber %sst vehicle group = ", number) + vehicleGroup);
        else
            System.out.println(String.format("%nnumber %snd vehicle group = ", number) + vehicleGroup);

        System.out.println();
    }

    @Then("^the cashTotals under consumer cash should be the same in the db and json$")
    public void theCashTotalsUnderConsumerCashShouldBeTheSameInTheDbAndJson() {
        List<CashIncentive> dbCashIncentives = q_a.getCashIncentivesByVehicleGroupId(vehicleGroup.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\ncashTotal Check");
        int jsonCashTotal = 0;
        for (int i = 0; i < numberOfCashIncentives; i++) {
            boolean flag = false;
            for (CashIncentive dbci : dbCashIncentives) {
                System.out.println("\n" + i + ") db cashTotal:" + dbci.getCashTotal());
                jsonCashTotal = responseJsonPath.get(String.format("response[%d].cashDealScenarios[%d].consumerCash.totalConsumerCash", number, i));
                System.out.println(i + ") js cashTotal:" + jsonCashTotal);
                if (dbci.getCashTotal() == jsonCashTotal) {

                    dbCashIncentives.remove(dbci);
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the cashTotals (particularly:%d) in the json for vehicleGroupId = %d ", jsonCashTotal, vehicleGroup.getId()));
            System.out.println();
        }
    }
}

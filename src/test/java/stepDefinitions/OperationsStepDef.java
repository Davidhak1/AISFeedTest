package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Ca;
import io.restassured.path.json.JsonPath;
import model.*;
import org.testng.Assert;
import resources.base;
import resources.compatibleBase;

import java.util.List;
import java.util.Random;

public class OperationsStepDef extends compatibleBase {

    private static int numberOfVehicleGroups;
    private static int numberOfVehicleCodes;
    private static int numberOfVehicleMatchDetails;
    private static int numberOfCashIncentives;

    private static VehicleGroup vehicleGroupTerm;
    private static int number;


    @Given("^Operations Initialization$")
    public void operInitialization() {
//        System.out.println("INSIDE OPERINIT ---------------");
        initBase();

    }

    @When("^Get the id of the latest (.+) feedRun for AIS_CA$")
    public void getTheIdOfTheLatestSFeedRunForAIS_CAWithStatus(String status) {
        setFeedRunId(q_c.getTheLatestAISFeedRunIDWithStatus(status));
        System.out.println("Latest " + status + " feedRunID = " + getFeedRunId());
        System.out.println();

    }

//    @When("^Get the number of ais CA eligible vehicles for (.+) and (.+)$")
//    public void getTheNumberOfAisCAEligibleVehiclesForThirdPartyIdAndMake(String thirdPartyId, String make) {
//        setEligibleCount(q_n.getNumberOfVehiclesWithAccountIdOemAndNewAndNotRemoved(thirdPartyId, make));
//        System.out.println(String.format("AccountId = '%s', make = '%s'",thirdPartyId,make));
//        System.out.println("eligibleCount = " + getEligibleCount());
//        System.out.println();
//
//    }

    @When("^Get the number of vehicles having vehicleGroup in DB with the latest feedRunId (.+) and (.+)$")
    public void getTheNumberOfAisIncentivesWithTheLatestFeedRunIdAccountIdAndMake(String accountId, String make) {
        setAisIncentivesCount(q_a.numberOfVehicleGroupsWithDistinctVinByFeedRunIdAccountIdAndMake(getFeedRunId(), accountId, make));
        System.out.println("vehicles having vehicleGroup in DB= " + getAisIncentivesCount());
        System.out.println();


    }

    @Then("^the amount of vehicleCodes with those vehicleGroupId should be the same in the response and db$")
    public void theAmountOfVehicleCodesWithThoseVehicleGroupIdShouldBeTheSameInTheResponseAndDb() {
        int jsonLenght = responseHolder.lengthOfArray(String.format("response[%d].vehicleCodes", number));
        int dbLength = q_a.getNumberOfVehicleCodesByVehicleGroupId(vehicleGroupTerm.getId());

        System.out.println(jsonLenght + " = " + dbLength);
        Assert.assertEquals(dbLength, jsonLenght, String.format("The number of vehicleCodes for 'vehicleGroupid' = '%s' in db is not " +
                "equal the number in json response. DB: %s, Json: %s", vehicleGroupTerm.getId(), dbLength, jsonLenght));
        numberOfVehicleCodes = jsonLenght;
    }

    @Then("^the amount of vehicleMatchDetails with those vehicleGroupId should be the same in the response and db$")
    public void theAmountOfVehicleMatchDetailsWithThoseVehicleGroupIdShouldBeTheSameInTheResponseAndDb() {
        int jsonLenght = responseHolder.lengthOfArray(String.format("response[%d].vehicleGroupMatchDetails", number));
        int dbLength = q_a.getNumberOfVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroupTerm.getId());

        System.out.println(jsonLenght + " = " + dbLength);
        Assert.assertEquals(dbLength, jsonLenght, String.format("The number of VehicleGroupMatches for 'vehicleGroupid' = '%s' in db is not " +
                "equal the number in json response. DB: %s, Json: %s", vehicleGroupTerm.getId(), dbLength, jsonLenght));
        numberOfVehicleMatchDetails = jsonLenght;
    }

    @Then("^the amount of cashIncentives not having programId should be the same in the response and db$")
    public void theAmountOfCashIncentivesProgramIdNullShouldBeTheSameInTheResponseAndDb() {
        int jsonLenght = responseHolder.lengthOfArray(String.format("response[%d].cashDealScenarios", number));
        int dbLength = q_a.getNumberOfCashIncentivesByVehicleGroupId(vehicleGroupTerm.getId());

        System.out.println(jsonLenght + " = " + dbLength);
        Assert.assertEquals(dbLength, jsonLenght, String.format("The number of cashIncentives for 'vehicleGroupid' = '%s' in db is not " +
                "equal the number in json response. DB: %s, Json: %s", vehicleGroupTerm.getId(), dbLength, jsonLenght));
        numberOfCashIncentives = jsonLenght;
    }


//    @Then("^the values of aisVehicleGroupIds should be the same in the response and db$")
//    public void theValuesOfAisVehicleGroupIdsShouldBeTheSameInTheResponseAndDb() {
//
//        VehicleGroup vg = getVehicleGroup();
//        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveIdAndVin(vg.getAisIncentiveId(), vg.getVin());
//        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
//        String jsonAisVehicleGroupId = null;
//        for (int i = 0; i < numberOfVehicleGroups; i++) {
//            boolean flag = false;
//            for (VehicleGroup dbvg : dbVehicleGroups) {
//                System.out.println("\n" + i + ") db vehicleGroupId:" + dbvg.getAisVehicleGroupId());
//                jsonAisVehicleGroupId = responseJsonPath.get(String.format("response[%d].aisVehicleGroupID", i));
//                System.out.println(i + ") js vehicleGroupId:" + jsonAisVehicleGroupId);
//                if (dbvg.getAisVehicleGroupId().equalsIgnoreCase(jsonAisVehicleGroupId)) {
//                    dbVehicleGroups.remove(dbvg);
//                    flag = true;
//                    break;
//                }
//            }
//            Assert.assertTrue(flag, String.format("The db doesn't contain all the aisVehicleGroupIds (particularly:%s) in the json for aisIncentiveId = ", jsonAisVehicleGroupId, getAisIncentive().getId()));
//            System.out.println();
//        }
//    }
////
//    @Then("^the values of vehicleGroupIds should be the same in the response and db$")
//    public void theValuesOfVehicleGroupIdsShouldBeTheSameInTheResponseAndDb() {
//
//        VehicleGroup vg = getVehicleGroup();
//        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveIdAndVin(vg.getAisIncentiveId(), vg.getVin());
//        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
//        int jsonVehicleGroupId = 0;
//        for (int i = 0; i < numberOfVehicleGroups; i++) {
//            boolean flag = false;
//            for (VehicleGroup dbvg : dbVehicleGroups) {
//                System.out.println("\n" + i + ") db vehicleGroupId:" + dbvg.getVehicleGroupId());
//                jsonVehicleGroupId = responseJsonPath.get(String.format("response[%d].vehicleGroupID", i));
//                System.out.println(i + ") js vehicleGroupId:" + jsonVehicleGroupId);
//                if (dbvg.getVehicleGroupId() == jsonVehicleGroupId) {
//                    dbVehicleGroups.remove(dbvg);
//                    flag = true;
//                    break;
//                }
//            }
//            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleGroupIds (particularly:%d) in the json for aisIncentiveId = ", jsonVehicleGroupId, getAisIncentive().getId()));
//            System.out.println();
//        }
//    }
//
//    @Then("^the values of vehicleGroupNames should be the same in the response and db$")
//    public void theValuesOfvehicleGroupNamesShouldBeTheSameInTheResponseAndDb() {
//
//        VehicleGroup vg = getVehicleGroup();
//        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveIdAndVin(vg.getAisIncentiveId(), vg.getVin());
//        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
//        String jsonVehicleGroupName = null;
//        for (int i = 0; i < numberOfVehicleGroups; i++) {
//            boolean flag = false;
//            for (VehicleGroup dbvg : dbVehicleGroups) {
//                System.out.println("\n" + i + ") db vehicleGroupName:" + dbvg.getVehicleGroupName());
//                jsonVehicleGroupName = responseJsonPath.get(String.format("response[%d].vehicleGroupName", i));
//                System.out.println(i + ") js vehicleGroupName:" + jsonVehicleGroupName);
//                if (dbvg.getVehicleGroupName().equalsIgnoreCase(jsonVehicleGroupName)) {
//                    dbVehicleGroups.remove(dbvg);
//                    flag = true;
//                    break;
//                }
//            }
//            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleGroupNames (particularly:%s) in the json for aisIncentiveId = ", jsonVehicleGroupName, getAisIncentive().getId()));
//            System.out.println();
//
//        }
//    }
//
//    @Then("^the values of modelYear should be the same in the response and db$")
//    public void theValuesOfModelYearsShouldBeTheSameInTheResponseAndDb() {
//
//        VehicleGroup vg = getVehicleGroup();
//        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveIdAndVin(vg.getAisIncentiveId(), vg.getVin());
//        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
//        int jsonModelYear = 0;
//        for (int i = 0; i < numberOfVehicleGroups; i++) {
//            boolean flag = false;
//            for (VehicleGroup dbvg : dbVehicleGroups) {
//                System.out.println("\n" + i + ") db modelYear:" + dbvg.getModelYear());
//                jsonModelYear = responseJsonPath.get(String.format("response[%d].modelYear", i));
//                System.out.println(i + ") js modelYear:" + jsonModelYear);
//                if (dbvg.getModelYear() == jsonModelYear) {
//                    dbVehicleGroups.remove(dbvg);
//                    flag = true;
//                    break;
//                }
//            }
//            Assert.assertTrue(flag, String.format("The db doesn't contain all the modelYears (particularly:%d) in the json for aisIncentiveId = ", jsonModelYear, getAisIncentive().getId()));
//            System.out.println();
//        }
//    }
//
//    @Then("^the values of marketingYear should be the same in the response and db$")
//    public void theValuesOfMarketingYearsShouldBeTheSameInTheResponseAndDb() {
//
//        VehicleGroup vg = getVehicleGroup();
//        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveIdAndVin(vg.getAisIncentiveId(), vg.getVin());
//        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
//        int jsonMarketingYear = 0;
//        for (int i = 0; i < numberOfVehicleGroups; i++) {
//            boolean flag = false;
//            for (VehicleGroup dbvg : dbVehicleGroups) {
//                System.out.println("\n" + i + ") db marketingYear:" + dbvg.getMarketingYear());
//                jsonMarketingYear = Integer.parseInt(responseJsonPath.get(String.format("response[%d].marketingYear", i)).toString());
//                System.out.println(i + ") js marketingYear:" + jsonMarketingYear);
//                if (dbvg.getMarketingYear() == jsonMarketingYear) {
//                    dbVehicleGroups.remove(dbvg);
//                    flag = true;
//                    break;
//                }
//            }
//            Assert.assertTrue(flag, String.format("The db doesn't contain all the marketingYears (particularly:%d) in the json for aisIncentiveId = ", jsonMarketingYear, getAisIncentive().getId()));
//            System.out.println();
//        }
//    }
//
//    @Then("^the values of regionID should be the same in the response and db$")
//    public void theValuesOfRegionIDShouldBeTheSameInTheResponseAndDb() {
//
//        VehicleGroup vg = getVehicleGroup();
//        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveIdAndVin(vg.getAisIncentiveId(), vg.getVin());
//        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
//        int jsonRegionID = 0;
//        for (int i = 0; i < numberOfVehicleGroups; i++) {
//            boolean flag = false;
//            for (VehicleGroup dbvg : dbVehicleGroups) {
//                System.out.println("\n" + i + ") db regionID:" + dbvg.getRegionId());
//                jsonRegionID = responseJsonPath.get(String.format("response[%d].regionID", i));
//                System.out.println(i + ") js regionID:" + jsonRegionID);
//                if (dbvg.getRegionId() == jsonRegionID) {
//                    dbVehicleGroups.remove(dbvg);
//                    flag = true;
//                    break;
//                }
//            }
//            Assert.assertTrue(flag, String.format("The db doesn't contain all the regionIDs (particularly:%d) in the json for aisIncentiveId = ", jsonRegionID, getAisIncentive().getId()));
//            System.out.println();
//        }
//    }
//
//    @Then("^the values of hashcodes should be the same in the response and db$")
//    public void theValuesOfHashcodesShouldBeTheSameInTheResponseAndDb() {
//
//        VehicleGroup vg = getVehicleGroup();
//        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveIdAndVin(vg.getAisIncentiveId(), vg.getVin());
//        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
//        String jsonHashcode = null;
//        for (int i = 0; i < numberOfVehicleGroups; i++) {
//            boolean flag = false;
//            for (VehicleGroup dbvg : dbVehicleGroups) {
//                System.out.println("\n" + i + ") db hashcode:" + dbvg.getHash());
//                jsonHashcode = responseJsonPath.get(String.format("response[%d].hashcode", i));
//                System.out.println(i + ") js hashcode:" + jsonHashcode);
//                if (dbvg.getHash().equalsIgnoreCase(jsonHashcode)) {
//                    dbVehicleGroups.remove(dbvg);
//                    flag = true;
//                    break;
//                }
//            }
//            Assert.assertTrue(flag, String.format("The db doesn't contain all the hashcodes (particularly:%s) in the json for aisIncentiveId = %s", jsonHashcode, getAisIncentive().getId()));
//            System.out.println();
//
//        }
//    }

    @Then("^the acodes should be the same in the response and db$")
    public void theAcodesShouldBeTheSameInTheResponseAndDb() {
        List<VehicleCode> dbVehicleCodes = q_a.getVehicleCodesByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the acodes (particularly:%s) in the json " +
                    "for vehicleGroupId = %d ", jsonAcode, vehicleGroupTerm.getId()));
            System.out.println();
        }

    }

    @Then("^the modelCodes should be the same in the response and db$")
    public void theModelCodesShouldBeTheSameInTheResponseAndDb() {

        List<VehicleCode> dbVehicleCodes = q_a.getVehicleCodesByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the modelCodes (particularly:%s) in the json" +
                    " for vehicleGroupId = %d ", jsonModelCode, vehicleGroupTerm.getId()));
            System.out.println();
        }

    }

    @Then("^the styleIds should be the same in the response and db$")
    public void theStyleIdsShouldBeTheSameInTheResponseAndDb() {
        List<VehicleCode> dbVehicleCodes = q_a.getVehicleCodesByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the styleIds (particularly:%d) in the " +
                    "json for vehicleGroupId = %d ", jsonStyleId, vehicleGroupTerm.getId()));
            System.out.println();
        }

    }

    @Then("^the vehicleElements should be the same in the response and db$")
    public void theVehicleElementsShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleElement (particularly:%s) in the json for vehicleGroupId = %d ", jsonVehicleElement, vehicleGroupTerm.getId()));
            System.out.println();
        }

    }

    @Then("^the vehicleHints should be the same in the response and db$")
    public void theVehicleHintShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleHint (particularly:%s) in the " +
                    "json for vehicleGroupId = %d ", jsonVehicleHint, vehicleGroupTerm.getId()));
            System.out.println();
        }
    }

    @Then("^the valuesInVehicleGroup should be the same in the response and db$")
    public void theValuesInVehicleGroupShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroupTerm.getId());
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        System.out.println("\nvaluesInVehicleGroup Check");
        String jsonValuesInVehicleGroup = null;
        for (int i = 0; i < numberOfVehicleMatchDetails; i++) {
            boolean flag = false;
            for (VehicleGroupMatchDetail dbvgmd : dbVehicleGMD) {
                System.out.println("\n" + i + ") db valuesInVehicleGroup:" + dbvgmd.getValuesVehicleGroup());
                jsonValuesInVehicleGroup = responseJsonPath.get(String.format("response[%d].vehicleGroupMatchDetails[%d]." +
                        "valuesInVehicleGroup", number, i));

                System.out.println(i + ") js valuesInVehicleGroup:" + jsonValuesInVehicleGroup);
                if (dbvgmd.getValuesVehicleGroup().equalsIgnoreCase(jsonValuesInVehicleGroup)) {
                    dbVehicleGMD.remove(dbvgmd);
                    flag = true;
                    break;
                }

            }
            Assert.assertTrue(flag, String.format("The db doesn't contain all the valuesInVehicleGroup (particularly:%s) " +
                    "in the json for vehicleGroupId = %d ", jsonValuesInVehicleGroup, vehicleGroupTerm.getId()));
            System.out.println();
        }
    }

    @Then("^the vehicleHintSources should be the same in the response and db$")
    public void theVehicleHintSourcesShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the vehicleHintSource (particularly:%s) in " +
                    "the json for vehicleGroupId = %d ", jsonVehicleHintSource, vehicleGroupTerm.getId()));
            System.out.println();
        }
    }

    @Then("^the vehicleMatchStatuses should be the same in the response and db$")
    public void theVehicleMatchStatusesShouldBeTheSameInTheResponseAndDb() {
        List<VehicleGroupMatchDetail> dbVehicleGMD = q_a.getVehicleGroupMatchDetailsByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the matchStatuses (particularly:%s) in the " +
                    "json for vehicleGroupId = %d ", jsonMatchStatus, vehicleGroupTerm.getId()));
            System.out.println();
        }
    }

    @Then("^the dealScenarioTypeIds should be the same in the response and db$")
    public void theDealScenarioTypeIdsShouldBeTheSameInTheResponseAndDb() {
        List<CashIncentive> dbCashIncentives = q_a.getCashIncentivesByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the dealScenarioTypeIds (particularly:%d) " +
                    "in the json for vehicleGroupId = %d ", jsonDealScenarioTypeId, vehicleGroupTerm.getId()));
            System.out.println();
        }
    }

    @Then("^the cashNames under consumer cash should be the same in the db and json$")
    public void theCashNamesUnderConsumerCashShouldBeTheSameInTheDbAndJson() {
        List<CashIncentive> dbCashIncentives = q_a.getCashIncentivesByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the cashNames (particularly:%s) in the json " +
                    "for vehicleGroupId = %s ", jsonCashName, vehicleGroupTerm.getId()));
            System.out.println();
        }
    }

//    @Then("^get number (\\d+) vehicleGroup of the response$")
//    public void getTheFirstVehicleGroupIdOfTheResponse(int number) {
//
//        OperationsStepDef.number = number;
//        VehicleGroup vg = getVehicleGroup();
//        List<VehicleGroup> dbVehicleGroups = q_a.getVehicleGroupsByAISIncentiveIdAndVin(vg.getAisIncentiveId(), vg.getVin());
//
//        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();
//
//        String jsonAisVehicleGroupId = responseJsonPath.get(String.format("response[%d].aisVehicleGroupID", number));
//
//        for (VehicleGroup vgdb : dbVehicleGroups) {
//            if (vgdb.getAisVehicleGroupId().equalsIgnoreCase(jsonAisVehicleGroupId)) {
//                vehicleGroupTerm = vgdb;
//            }
//        }
//        if (vehicleGroupTerm == null) {
//            System.out.println(String.format("\n\n!IMPORTANT! No vehicleGroups found in db for 'aisIncentiveId' = %s " +
//                    "and with aisVehicleGroupid = %s", getAisIncentive().getId(), jsonAisVehicleGroupId));
//
//        }
//        if (number == 1)
//            System.out.println(String.format("%nnumber %sst vehicle group = ", number) + vehicleGroupTerm);
//        else
//            System.out.println(String.format("%nnumber %snd vehicle group = ", number) + vehicleGroupTerm);
//
//        System.out.println();
//    }

    @Then("^the cashTotals under consumer cash should be the same in the db and json$")
    public void theCashTotalsUnderConsumerCashShouldBeTheSameInTheDbAndJson() {
        List<CashIncentive> dbCashIncentives = q_a.getCashIncentivesByVehicleGroupId(vehicleGroupTerm.getId());
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
            Assert.assertTrue(flag, String.format("The db doesn't contain all the cashTotals (particularly:%d) in the " +
                    "json for vehicleGroupId = %d ", jsonCashTotal, vehicleGroupTerm.getId()));
            System.out.println();
        }
    }

    @Then("^the vin in the response should be the same as in the requested url$")
    public void theVinInTheResponseShouldBeTheSameAsInTheRequestedUrl() {
        String responseVin = responseHolder.getResponseJsonPath().get("response[0].vin");
//        Assert.assertEquals(responseVin,getAisIncentive().getVin(), "The vin of AIS incentive is not equal to the vin in the response");
    }

}

package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONObject;
import org.omg.PortableInterceptor.INACTIVE;
import org.testng.Assert;
import resources.Utils;
import resources.base;

import java.util.*;

import static io.restassured.path.json.JsonPath.from;

public class DisclaimerStepDef extends base {

    private static ArrayList<Integer> programIDs;
    private static List<Integer> jsonProgramIds;
    private static List<Integer> dbProgramIds;

    private static List<Integer> jsonPrograms;

    @Given("^Disclaimer Initialization$")
    public void operInitialization() {
        System.out.println("INSIDE OPERINIT ---------------");
        initBase();

    }

    @When("^fetch all the programIds from the response$")
    public void fetchAllTheProgramIdsFromTheResponse() {
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        jsonProgramIds = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(), "response.*.programID");
        System.out.println("\njsonProgramIds = " + jsonProgramIds);
        System.out.println("The count of programIds in JSON response = " + jsonProgramIds.size());

    }

    @Then("^There should be no duplicate programIds in the json response$")
    public void thereShouldBeNoDuplicateProgramIdsInTheJsonResponse() {
        Set<Integer> set = new HashSet<Integer>(jsonProgramIds);
        Set<Integer> dublicates = Utils.findDuplicates(jsonProgramIds);

        Assert.assertEquals(jsonProgramIds.size(), set.size(), "There are duplicates in the json response, " +
                "here they are---->>>" + dublicates);
    }

    @Then("^we should have all the programs in our db$")
    public void weShouldHaveAllTheProgramsInOurDb() {
        dbProgramIds = q_d.GetAllProgramLocalProgramIDs();

        Collection<Integer> jsonMinusDB = CollectionUtils.subtract(jsonProgramIds, dbProgramIds);

        Assert.assertTrue(jsonMinusDB.isEmpty(), String.format("'programLocal' table doesn't include %d programIDs from the JSON response " +
                "List of programIDs that was not located in db --->>>", jsonMinusDB.size()) + jsonMinusDB);

        System.out.println("\nChecking that all programs from json response exist in our db: passed!" );
    }

    @Then("^the number of programs in json response should be less or equal to the number of programLocals in db$")
    public void theNumberOfProgramsInJsonResponseShouldBeLessOrEqualToTheNumberOfProgramLocalsInDb() {
        List<Integer> allJsonPrograms = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(), "response.*.programID");
        int jsonProgramCount = allJsonPrograms.size();
        int dbProgramCount = q_d.getAllProgramLocalProgramIDs().size();

        Assert.assertTrue(jsonProgramCount <= dbProgramCount,String.format(" The amount of programs in json response" +
                "is greater than the amount in db. Json:%d, DB:%d", jsonProgramCount,dbProgramCount ));
        System.out.printf("%njsonPrograms count: %d,%ndbProgramsCount: %d%n", jsonProgramCount,dbProgramCount);
    }


    @Then("^we should have a programLocalDescription for every programLocal in db$")
    public void weShouldHaveAProgramLocalDDescriptionForEveryProgramLocalInDb() {
        Set<Integer> programLocalIDs = q_d.GetAllProgramLocalIDs();
        Set<Integer> programLocalDescriptionIDs = q_d.getAllProgramLocalDescriptionProgramLocalIDs();

        System.out.printf("%n Count of nprogramLocalIDs [%d] = programLocalDescriptionIDs [%d]%n", programLocalIDs.size(), programLocalDescriptionIDs.size());

        Collection<Integer> localProgramMinusLocalDescr = CollectionUtils.subtract(programLocalIDs, programLocalDescriptionIDs);

        Assert.assertTrue(localProgramMinusLocalDescr.isEmpty(), String.format("'programLocalDescription' table doesn't include %d programs from the JSON response " +
                "List of programIDs that was not located in db --->>>", localProgramMinusLocalDescr.size()) + localProgramMinusLocalDescr);


    }

    @Then("^we should have a programLocal for every program in db$")
    public void weShouldHaveAProgramLocalForEveryProgramInDb() {
        Set<Integer> programIDs = q_d.getAllProgramProgramIDs();
        Set<Integer> programLocalProgramIDs = q_d.getAllProgramLocalProgramIDs();

        System.out.printf("%n Count of 'program' table programIDs [%d] = 'programLocal' table programIds " +
                "[%d]%n", programIDs.size(), programLocalProgramIDs.size());

        Collection<Integer> programMinusProgramLocal = CollectionUtils.subtract(programIDs, programLocalProgramIDs);

        Assert.assertTrue(programMinusProgramLocal.isEmpty(), String.format("'programLocal' table doesn't include %d programs " +
                "from 'program' table. List of programIDs that was not located in db --->>>", programMinusProgramLocal.size()) + programMinusProgramLocal);

    }

    @When("^extract (\\d+) random programs from json response$")
    public void extractRandomProgramsFromJsonResponse(int size) {

        jsonPrograms = new ArrayList<Integer>(size);
        List<Integer> allJsonProgramIDs = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(), "response.*.programID");
        System.out.println(jsonPrograms);




//
        for (int i = 0; i < size; i++) {
            jsonPrograms.add(allJsonProgramIDs.get(new Random().nextInt(allJsonProgramIDs.size())));
        }
////



//        jsonPrograms.addAll(allJsonProgramIDs);
//
//        jsonPrograms.remove(jsonPrograms.indexOf(34310));


        System.out.println("The size of programs being tested = " + jsonPrograms.size());
        System.out.println("\nJson random programs = " + jsonPrograms);
        System.out.println();
    }

    @Then("^the according programs should exist in ais_incentives db programDescription table$")
    public void theAccordingProgramsShouldExistInAis_incentivesDbProgramDescriptionTable() {
        int count =jsonPrograms.size();
        int totalSize = count;

        for (Integer i : jsonPrograms) {
            int count1 = 0;
            boolean exists = false;
            ProgramLocal pl= null;
            while(!exists && count1 < 3) {
                pl = q_d.getProgramLocalByProgramID(i);
                if(pl!=null)
                    exists = true;
                count1++;
            }
            Assert.assertNotNull(pl, String.format(" There is no record for programID = %d in the 'ProgramLocal' table in our db", i));
            System.out.println(--count + ")" + (totalSize-count) +")"+ pl.getProgramID());
        }
    }

    @Then("^the compatibleProgramsString field should contain all programs that are in json response$")
    public void theCompatibleProgramsStringShouldBeTheSameInTheResponseAndDb() {
        int count =jsonPrograms.size();
        int totalSize = count;
        List<Integer> jsonCompatiblePrograms = new ArrayList<Integer>();

        for (Integer i : jsonPrograms) {
            jsonCompatiblePrograms = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(),
                    String.format("response.%d.compatiblePrograms.*", i));

            ProgramLocal pl = q_d.getProgramLocalByProgramID(i);
            for (Integer in : jsonCompatiblePrograms) {
                Assert.assertTrue(pl.getCompatibleProgramsString().contains(in.toString()), String.format(" Program: %d does" +
                        " not contain compatibilityProgram [%d] in 'programLocal' table", i, in));
            }
            System.out.println(--count + ")" + (totalSize-count) + ") CompatibilityPrograms Checked for " + i);
        }

    }

    @Then("^the consumer field should be the same in the response and db$")
    public void theConsumerFieldShouldBeTheSameInTheResponseAndDb() {
        String jsonConsumer;
        ProgramLocalDescription dbDescription;
        String dbConsumer;

        for (Integer i : jsonPrograms) {
            jsonConsumer = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(),
                    String.format("response.%d.consumer", i));

            long programLocalID = q_d.getProgramLocalByProgramID(i).getId();
            dbDescription = q_d.getProgramLocalDescriptionByProgramLocalID(programLocalID);
            dbConsumer = dbDescription.getConsumer();

            Assert.assertEquals(jsonConsumer, dbConsumer, String.format("%nThe consumer field is not same for programID = %d." +
                    " In the response [%s], In the db [%s]", i, jsonConsumer, dbConsumer));
            System.out.println("\n" + jsonConsumer.substring(0, Math.min(jsonConsumer.length(), 25)) + "... = " +
                    dbConsumer.substring(0, Math.min(dbConsumer.length(), 25)) + "...");
        }

    }

    @Then("^the dealer field should be the same in the response and db$")
    public void theDealerFieldShouldBeTheSameInTheResponseAndDb() {
        String jsonDealer;
        ProgramLocalDescription dbDescription;
        String dbDealer;

        for (Integer i : jsonPrograms) {
            jsonDealer = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(),
                    String.format("response.%d.dealer", i));

            long programLocalID = q_d.getProgramLocalByProgramID(i).getId();
            dbDescription = q_d.getProgramLocalDescriptionByProgramLocalID(programLocalID);
            dbDealer = dbDescription.getDealer();

            String[] jsonPhrases = jsonDealer.split("\n");

            for (String phrase : jsonPhrases) {
                Assert.assertTrue(dbDealer.contains(phrase.trim()), String.format("%nThe dealer field is not same for programID = %d. DB doesn't contain phrase [%s]" +
                        " %nIn the response %n[%s], %nIn the db %n[%s]%n%n ", i,phrase, jsonDealer, dbDealer));

//                System.out.println(String.format("%nThe programID = %d." +
//                        " %nIn the response %n[%s], %nIn the db %n[%s]%n%nDB doesn't contain phrase [%s]",i,jsonDealer,dbDealer,ss));
            }
            System.out.println("\n" + jsonDealer.substring(0, Math.min(jsonDealer.length(), 35)) + "... = " +
                    dbDealer.substring(0, Math.min(dbDealer.length(), 35)) + "...");

        }

    }

    @Then("^the short-title field should be the same in the response and db$")
    public void theShortTitleFieldShouldBeTheSameInTheResponseAndDb() {
        String jsonShortTitle;
        ProgramLocalDescription dbDescription;
        String dbShortTitle;

        for (Integer i : jsonPrograms) {
            jsonShortTitle = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(),
                    String.format("response.%d.shortTitle", i));

            long programLocalID = q_d.getProgramLocalByProgramID(i).getId();
            dbDescription = q_d.getProgramLocalDescriptionByProgramLocalID(programLocalID);
            dbShortTitle = dbDescription.getShortTitle();

            Assert.assertEquals(jsonShortTitle, dbShortTitle, String.format("%nThe shortTitle field is not same for programID = %d." +
                    " In the response [%s], In the db [%s]", i, jsonShortTitle, dbShortTitle));
            System.out.println("\n" + jsonShortTitle + " = " + dbShortTitle);

        }
    }

    @Then("^the title field should be the same in the response and db$")
    public void theTitleFieldShouldBeTheSameInTheResponseAndDb() {
        String jsonTitle;
        ProgramLocalDescription dbDescription;
        String dbTitle;

        for (Integer i : jsonPrograms) {
            jsonTitle = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(),
                    String.format("response.%d.title", i));

            long programLocalID = q_d.getProgramLocalByProgramID(i).getId();
            dbDescription = q_d.getProgramLocalDescriptionByProgramLocalID(programLocalID);
            dbTitle = dbDescription.getTitle();

            Assert.assertEquals(jsonTitle, dbTitle, String.format("%nThe title field is not same for programID = %d." +
                    " In the response [%s], In the db [%s]", i, jsonTitle, dbTitle));
            System.out.println("\n" + jsonTitle + " = " + dbTitle);
        }
    }

    @Then("^the taxStatus field should be the same in the response and db$")
    public void theTaxStatusFieldShouldBeTheSameInTheResponseAndDb() {
        String jsonTaxStatus;
        ProgramLocalDescription dbDescription;
        String dbTaxStatus;

        for (Integer i : jsonPrograms) {
            jsonTaxStatus = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(),
                    String.format("response.%d.taxStatus", i));

            long programLocalID = q_d.getProgramLocalByProgramID(i).getId();
            dbDescription = q_d.getProgramLocalDescriptionByProgramLocalID(programLocalID);
            dbTaxStatus = dbDescription.getTaxStatus();

            Assert.assertEquals(jsonTaxStatus, dbTaxStatus, String.format("%nThe taxStatus field is not same for programID = %d." +
                    " In the response [%s], In the db [%s]", i, jsonTaxStatus, dbTaxStatus));
            System.out.println("\n" + jsonTaxStatus + " = " + dbTaxStatus);
        }
    }


}


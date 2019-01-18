package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONObject;
import org.testng.Assert;
import resources.Utils;
import resources.base;

import java.util.*;

import static io.restassured.path.json.JsonPath.from;

public class DisclaimerStepDef extends base {

    private static ArrayList<Integer> programIDs;
    private static List<Integer> jsonProgramIds;
    private static List<Integer> dbProgramIds;



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
        System.out.println(jsonProgramIds.size());


        Set<Integer> set = new HashSet<Integer>(jsonProgramIds);
        System.out.println(set.size());

    }

    @Then("^There should be no duplicate programIds in the json response$")
    public void thereShouldBeNoDuplicateProgramIdsInTheJsonResponse() {
        Set<Integer> set = new HashSet<Integer>(jsonProgramIds);
        Set<Integer> dublicates = Utils.findDuplicates(jsonProgramIds);

        Assert.assertEquals(jsonProgramIds.size(), set.size(),"There are duplicates in the json response, " +
                "here they are---->>>" + dublicates);
    }

    @Then("^we should have all the programs in our db$")
    public void weShouldHaveAllTheProgramsInOurDb() {
        dbProgramIds = q_d.GetAllProgramLocalIds();

        Collection<Integer> jsonMinusDB = CollectionUtils.subtract(jsonProgramIds,dbProgramIds);

        Assert.assertTrue(jsonMinusDB.isEmpty(), String.format("'programLocal' table doesn't include %d programIDs from the JSON response " +
                "List of programIDs that was not located in db --->>>",jsonMinusDB.size()) + jsonMinusDB);

    }


    @Then("^we should have a programDescription for every program in db$")
    public void weShouldHaveAProgramDescriptionForEveryCashProgram() {
        int programCount = q_a.getTheNumberOfcashIncentivesThatHaveProgram(getFeedRunId());
        int programDescrCount = q_a.getTheNumberOfcashIncentivesThatHaveProgramAndHaveProgramDescription(getFeedRunId());

        System.out.println("\n"+programCount+" = " + programDescrCount);

        Assert.assertTrue(programCount!=0,"There are probably no aisIncentives with feedRunId = " + getFeedRunId() +
                ". Please rerun the feed");

        Assert.assertEquals(programCount,programDescrCount,String.format("The number of cashIncentivea in db that have a program" +
                "is not equal to the number of cashIncentive that have program and programDescription. programCount:%d, " +
                "programDescriptionCount:%d",programCount,programDescrCount));

    }


}


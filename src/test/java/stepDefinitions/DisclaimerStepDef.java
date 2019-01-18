package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import model.*;
import org.json.JSONObject;
import org.testng.Assert;
import resources.base;

import java.util.*;

import static io.restassured.path.json.JsonPath.from;

public class DisclaimerStepDef extends base {

    private static ArrayList<Integer> programIDs;


    @Given("^Disclaimer Initialization$")
    public void operInitialization() {
        System.out.println("INSIDE OPERINIT ---------------");
        initBase();

    }

    @When("^fetch all the programIds from the response$")
    public void fetchAllTheProgramIdsFromTheResponse() {
        JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

        List<Integer> list = com.jayway.jsonpath.JsonPath.read(responseHolder.getResponseBody(), "response.*.programID");

        System.out.println("\ni = " + list);

        for (Integer integer : list) {
            System.out.println("Integer = " + integer);
        }

        System.out.println(list.size());


        Set<Integer> set = new HashSet<Integer>(list);
        System.out.println(set.size());

    }

}

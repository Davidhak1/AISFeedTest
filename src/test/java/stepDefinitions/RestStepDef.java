package stepDefinitions;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import model.AISIncentive;
import model.IMDBVehicle;
import model.VehicleGroup;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import resources.ResponseHolder;
import resources.base;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.lang.String.join;
import static org.hamcrest.CoreMatchers.equalTo;


public class RestStepDef extends base {

    Map<String, Object> responseMap;
    ArrayList<HashMap<String, String>> responseMapArray;
    Map<String, String> body;
    List<String> bodyLikeArray;



    private String url;

    @Given("^Initialization$")
    public void initialization() {
        System.out.println("INSIDE INIT---------------");
        initBase();
        request = RestAssured.with();

    }

    @Then("^testing$")
    public void testing(){
        System.out.println("I still can retriece the last feedRunId: "+ getFeedRunId());
    }

    @Given("^the server endpoint is (.+)$")
    public void the_server_endpoint_is(String host) throws Throwable {
        this.url = host;
    }

    @Given("^the apis are up and running for (.+)$")
    public void the_apis_are_up_and_running_for(String url) throws Throwable {
        this.url = url;
        response = given().when().get(url);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("^adding api path for get request (.+)$")
    public void adding_api_path_for_the_request(String apiUrl) throws Throwable {
        this.url += apiUrl;
    }

    @When("^adding api path and body for post requst (.+) with below details")
    public void addingApiPathForPostRequstApiLocationLocationDetails(String api_url, DataTable dataTable) throws Throwable {

        this.url += api_url;

        this.body = new LinkedHashMap<String, String>();
        for (DataTableRow row : dataTable.getGherkinRows()) {
            this.body.put(row.getCells().get(0), row.getCells().get(1));
        }

    }

    @When("^adding basic authentication (.+) (.+)$")
    public void addingAuthenticationVcdaWhatAPrJCtPasswRd(String username, String password) {
        request.auth().preemptive().basic(username, password);
    }

    @And("^adding following headers$")
    public void iAddFollowingHeaders(DataTable dataTable) throws Throwable {

        for (DataTableRow row : dataTable.getGherkinRows()) {

            request.header(row.getCells().get(0), row.getCells().get(1));

        }

    }

    @When("adding following parameters")
    public void adding_following_parameters(DataTable dataTable) {

        for (DataTableRow row : dataTable.getGherkinRows()) {
            request.param(row.getCells().get(0), row.getCells().get(1));
        }
    }

    @When("^perform the get request$")
    public void perform_the_request() throws Throwable {

        System.out.println("\n"+this.url);
        response = request.when().get(this.url);
        responseHolder.setResponse(response);
    }

    @And("^perform the post request$")
    public void andPerformThePostRequest() throws Throwable {
        response = request.contentType(ContentType.JSON).body(this.body).when().post(this.url);
        responseHolder.setResponse(response);
    }

    @And("^perform the post request sending an array$")
    public void andPerformThePostRequestSendingAnArray() throws Throwable {
        response = request.given().contentType(ContentType.JSON).body(this.bodyLikeArray).when().post(this.url);
        responseHolder.setResponse(response);
    }

    @Then("^the response code should be (\\d+)$")
    public void the_response_code_should_be(int responseCode) throws Throwable {
        Assert.assertEquals(responseHolder.getResponseCode(), responseCode);
    }

    @Then("^I should see json response with pairs on the filtered (.+) node$")
    public void i_should_see_json_response_with_pairs_on_the_filtered_node(String filter, DataTable dataTable) throws IOException {

        Map<String, String> query = new LinkedHashMap<String, String>();

        for (DataTableRow row : dataTable.getGherkinRows()) {
            query.put(row.getCells().get(0), row.getCells().get(1));
        }

        ObjectReader reader = new ObjectMapper().reader(Map.class);
        responseMap = reader.readValue(responseHolder.getResponseBody());
        System.out.println(responseMap);

        //if filter is not equal '$' => we will cut the response at the node we need (filter)
        if (!(filter.equals("$"))) {
            try {
                responseMap = (Map<String, Object>) responseMap.get(filter);
                for (String key : query.keySet()) {
                    Assert.assertTrue(responseMap.containsKey(key));
                    Assert.assertEquals(query.get(key), responseMap.get(key).toString());
                }

            }

            catch (ClassCastException e) {
                responseMapArray = (ArrayList<HashMap<String, String>>) responseMap.get(filter);

                System.out.println("RESPONSE MAP ARRAY --->" + responseMapArray);

                for (String key : query.keySet()) {
                    for (HashMap<String, String> map : responseMapArray) {
                        Assert.assertTrue(map.containsKey(key),
                                String.format("The response doesn't contain key -> %s", key));
                        try {
                            Assert.assertEquals(query.get(key), map.get(key));
                        }catch (ClassCastException exception){
                            String responseValue = String.valueOf(map.get(key));
                            if(responseValue.contains(".")){
                                responseValue = responseValue.substring(0, responseValue.indexOf('.'));
                            }
                            System.out.println("Response Value = " + responseValue);
                            Assert.assertEquals(query.get(key), responseValue);

                        }
                    }   System.out.println(key + "=" + query.get(key));
                }

            }
        }

    }

    @Then("^I should see the specifc json value for the following jsonPath$")
    public void iShouldSeeTheSpecifcJsonValueForTheFollowingJsonPath(DataTable dataTable) {
        Map<String, String> query = new LinkedHashMap<String, String>();

        for (DataTableRow row : dataTable.getGherkinRows()) {
            query.put(row.getCells().get(0), row.getCells().get(1));
        }

        for (Map.Entry<String,String> entry : query.entrySet()) {
            System.out.printf("Key = %s, Value = %s%n", entry.getKey(), entry.getValue());
            JsonPath jsonPath = responseHolder.getResponseJsonPath();

//            System.out.println("DATA TABLE->>> "+ jsonPath.get(entry.getKey()));

            Assert.assertEquals(entry.getValue(), jsonPath.get(entry.getKey()));


        }


    }

    @Then("^I should see json response as an array containing following elements$")
    public void iShouldSeeJsonResponseAsAnArrayContainingFollowingElementsOnTheFiltered$Node(DataTable dataTable) throws IOException {
        List<String> queryList = new ArrayList<String>();

        for (DataTableRow row : dataTable.getGherkinRows()) {
            queryList.add(row.getCells().get(0));
        }

        System.out.println();
        System.out.println(ResponseHolder.getResponseBody());
        String responseArray = ResponseHolder.getResponseBody();

        for(String key : queryList){
            Assert.assertTrue(responseArray.contains(key), String.format
                    ("Response body doesn't contain following key: %s. Response body: %s",key,responseArray));
        }


    }

    @Then("^I should see json response with the array of (.+) than (\\d+) items on the filtered (.+) node$")
    public void iShouldSeeJsonResponseWithTheArrayOfItemsOnTheFilteredImageUrlsNode(String comparison, int length, String filter) throws Throwable {

        int actualLen = responseHolder.lengthOfArray(filter);

        if(comparison.equalsIgnoreCase("equal")){
            Assert.assertEquals(actualLen, length, String.format("The lengths supposed, but are not equal. expected: %d | actual: %d", length, actualLen));
        }
        else if(comparison.equalsIgnoreCase("less")){
            Assert.assertTrue(actualLen < length,String.format("The actual length supposed, but is not less than expected. actual: %d | expected: %d", length, actualLen));
        }
        else{
            Assert.assertTrue(actualLen > length, String.format("The actual length supposed, but is not greater than expected. actual: %d | expected: %d", length, actualLen));
        }
    }

    @When("^adding to the api path the vin and (.+) of the vehicle$")
    public void addingToTheApiPathTheVinAndZipOfTheVehicle(String zip) {

        this.url += getAisIncentive().getVin();
        this.url += "/"+zip+".json";

    }

    @When("^adding to the api path the vin of the vehicle and the latestFeedRunId$")
    public void addingToTheApiPathTheVinOfTheVehicleAndTheLatestFeedRunId() {
        this.url += "/"+getAisIncentive().getVin();
        this.url += "/"+getFeedRunId();
    }

}





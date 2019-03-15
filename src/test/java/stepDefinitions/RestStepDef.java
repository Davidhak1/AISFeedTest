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
import resources.compatibleBase;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.lang.String.join;
import static org.hamcrest.CoreMatchers.equalTo;


public class RestStepDef extends compatibleBase {

    Map<String, Object> responseMap;
    ArrayList<HashMap<String, String>> responseMapArray;
    Map<String, String> body;
    List<String> bodyLikeArray;
//    public static Set<Integer> distinctRegions;
//    public static Map<Integer,Integer> regionZipMap;


    private String url;

    @Given("^Initialization$")
    public void initialization() {
//        System.out.println("\nINSIDE INIT---------------");
        initBase();
        request = RestAssured.with();

    }

    @Then("^testing$")
    public void testing(){
        System.out.println("I still can retriece the last feedRunId: "+ getFeedRunId());
    }

    @Given("^the server endpoint is (.+)$")
    public void the_server_endpoint_is(String host){
        this.url = host;
    }

    @Given("^the apis are up and running for (.+)$")
    public void the_apis_are_up_and_running_for(String url){
        this.url = url;
        response = given().when().get(url);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("^adding to the api path (.+)$")
    public void adding_to_the_api_path(String apiUrl){
        this.url += apiUrl;
    }

    @When("^adding api body for post requst with below details")
    public void addingApiBodyForPostRequstApiLocationLocationDetails( DataTable dataTable) throws Throwable {

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
    public void iAddFollowingHeaders(DataTable dataTable) {

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
    public void perform_the_get_request(){

        System.out.println("\n"+this.url);
        response = request.when().get(this.url);
        responseHolder.setResponse(response);
    }

    @And("^perform the post request$")
    public void andPerformThePostRequest(){
        if(this.body!=null) {
            response = request.contentType(ContentType.JSON).body(this.body).when().post(this.url);
        }
        else
            response = request.contentType(ContentType.JSON).when().post(this.url);
        responseHolder.setResponse(response);

    }

    @And("^perform the post request sending an array$")
    public void andPerformThePostRequestSendingAnArray(){
        response = request.given().contentType(ContentType.JSON).body(this.bodyLikeArray).when().post(this.url);
        responseHolder.setResponse(response);
    }

    @Then("^the response code should be (\\d+)$")
    public void the_response_code_should_be(int responseCode){
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

//    @When("^adding to the api path the vin and (.+) of the vehicle$")
//    public void addingToTheApiPathTheVinAndZipOfTheVehicle(String zip) {
//
//        this.url += getVehicleGroup().getVin();
//        this.url += "/"+zip+".json";
//
//    }


    @Then("^make a call for each make and print the number of postalCodes per regionId for a make$")
    public void printTheNumberOfPostalCodesPerRegionId(DataTable dataTable) {
        List<String> makes = new ArrayList<String>();

        for (DataTableRow row : dataTable.getGherkinRows()) {
            makes.add(row.getCells().get(0));
        }

        distinctRegions = new HashSet<Integer>();
        notDistinctRegions = new ArrayList<>();

        regionZipMap = new HashMap<Integer, Integer>();
        for(String make : makes) {
            the_server_endpoint_is("https://incentives.homenetiol.com/v2.6/CA/GetPostalcodesByMake/");
            adding_to_the_api_path(make);
            perform_the_get_request();
            the_response_code_should_be(200);
            JsonPath responseJsonPath = responseHolder.getResponseJsonPath();

            System.out.println(make);
            int regionIdsSize = responseJsonPath.get(String.format("response.regionPostalcodeGroups.regioID.size()"));
            System.out.println(regionIdsSize);
            for (int i = 0; i < regionIdsSize; i++) {
                int regionId = responseJsonPath.get(String.format("response.regionPostalcodeGroups.regionID[%d]", i));
                int zipCount = responseJsonPath.get(String.format("response.regionPostalcodeGroups.postalcodes[%d].size", i));

                distinctRegions.add(regionId);
                notDistinctRegions.add(regionId);

                regionZipMap.put(regionId, zipCount);
                System.out.println(regionId + " : " + zipCount);
            }
        }
        System.out.println("\nDistinct regionIds = " + distinctRegions.size());
        System.out.println("\nNot distinct regionIds = " + notDistinctRegions.size());

        totalPairs = 0;
        int i=1;
        Iterator entries = regionZipMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            int key = (int) entry.getKey();
            int value = (int) entry.getValue();
            System.out.println(i++ + ")RegionId : " + key + " = " + value + " postal codes");
            totalPairs+=value;
        }
        System.out.println("#TOTAL PAIRS = " + totalPairs);

    }


    @Then("^make a call to IS with each regionId we should get the same number of postalCodes in the response as in the db$")
    public void makeACallToISWithEachRegionIdWeShouldGetTheSameNumberOfPostalCodesInTheResponseAsInTheDb() {
        System.out.println();
        Iterator entries = regionIdPostalCodePairs.entrySet().iterator();
        int i=1;

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key =  (String)entry.getKey();
            int value = (int) entry.getValue();
            System.out.println(i++ + ")RegionId : " + key + " has " + value + " postalCodes");

            the_server_endpoint_is("http://vtqainv-incentivessvc03.int.dealer.com:9620/incentives-services/rest/api/v1/AisRegionDetailsService/getPostalCodes/");
            adding_to_the_api_path(key);
            perform_the_get_request();
            int responseArraySize = ResponseHolder.lengthOfArray("$");
            System.out.println("Number of postalCodes in the IS API response = " + responseArraySize);
            Assert.assertEquals(value,responseArraySize,String.format("The amount of postalCodes for regionId[%s] is not equal in the db/API response --> %d = %d",key,value,responseArraySize));
            System.out.println();
        }
    }
}





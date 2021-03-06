package stepDefinitions;


import com.jayway.jsonpath.JsonPath;
import cucumber.api.DataTable;
import cucumber.api.java.cs.A;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import gherkin.formatter.model.DataTableRow;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.w3c.dom.NodeList;
import resources.*;

import javax.xml.ws.Response;
import javax.xml.xpath.XPath;
import java.io.File;
import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Math.abs;
import static resources.Utils.getJsonContentFromFile;
import static resources.Utils.getXpath;

public class DownloadStepDef extends compatibleBase {
    private List<String> accMakes;
    private List<String> makeFileNames;
    private List<String> allFileNames;
    private List<String> specificVins;
    private int xmlTotalVins;
    private List<String> responses;
    private RestStepDef rsd;
    private List<ArrayList<Integer>> vehicleGroups;
    private int vehicleGroupVinsSize;
    private int vinVehicleGroupsSize;

    @Given("^Download Initialization$")
    public void operInitialization() {
        initBase();
    }


    @When("^load all ais files$")
    public void loadAllAisFiles() {
        SFTPConnection.downloadTheFilesFromAISFeed(getFeedRunId());
    }

    @When("^load all ais processed files$")
    public void loadAllAisProcessedFiles() {
        SFTPConnection.downloadTheProcessedFilesFromAISFeed(getFeedRunId());

    }

    @When("^Get the number of ais CA eligible vehicles for all subscribed accounts and makes from Nexus$")
    public void getTheNumberOfAisCAEligibleVehiclesForAllSubscribedAccountsAndMakes() {
        String account,make;
        int eligibleCount;
        accMakes = new ArrayList<>();
        accMakes = q_c.getAccount_makePairsWithSourceAccountId("aiscaincentivesaccount");
        System.out.println(accMakes);
        for(String s : accMakes)
        {
            account = s.substring(0,s.indexOf('_'));
            make = s.substring(s.indexOf('_')+1);
            System.out.printf("account = %s, make = %s",account,make);
            eligibleCount = q_n.getNumberOfVehiclesWithAccountIdOemAndNewAndNotRemoved(account,make);
            System.out.println("\teligibleCount = " + eligibleCount);
            setEligibleCount(getEligibleCount()+eligibleCount);
        }

        System.out.println("Count of all eligible vehicles = " + getEligibleCount() + "\n");
    }

    @When("^Get the names of all files downloaded from ais$")
    public void getTheNamesOfAllFilesDownloadedFromAis() {
        allFileNames = new ArrayList<>();
        String path;
        if(testEnvironment != null) {
            path = testEnvironment.aisSaveDir();
        }else{
            path = prop.getProperty("aisSaveDir");
        }
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                allFileNames.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        System.out.println("allFileNames = "+ allFileNames + "\n");

    }

    @Then("^files with following names should be there$")
    public void filesWithFollowingNamesShouldBeThere(DataTable dataTable) {
        List<String> fields = new ArrayList<>();
        for (DataTableRow row : dataTable.getGherkinRows()) {
            fields.add(row.getCells().get(0));
        }

        boolean contains;
        for (String required : fields)
        {
            contains = false;
            for(String file: allFileNames){
                if(required.equalsIgnoreCase(file)) {
                    contains = true;
                    System.out.printf("%nrequired file/downloaded file = %s = %s", required, file);
                    break;
                }
            }
            Assert.assertTrue(contains,String.format("We don't have a downloaded file with name %s from AIS",required));
        }
    }

    @When("^Get the names of all make files downloaded from ais$")
    public void getTheNamesOfAllMakeFilesDownloadedFromAis() {
        makeFileNames = new ArrayList<>();
        File folder;
        if(testEnvironment != null) {
            folder = new File(testEnvironment.aisSaveDir());
        }else{
            folder = new File(prop.getProperty("aisSaveDir"));
        }
        System.out.println("\n\n THE PATH OF THE FOLDER ---->" + folder.getPath());
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains("AIS-CA")) {
                System.out.println("File " + listOfFiles[i].getName());
                makeFileNames.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        System.out.println("MakeFileNames = "+ makeFileNames + "\n");
    }

    @Then("^we should have a json file for each AIS subscribed make$")
    public void weShouldHaveAJsonFileForEachAISSubscribedMake() {
        String make;
        for(String acc_make : accMakes)
        {
            make = acc_make.substring(acc_make.indexOf('_')+1);
            boolean contains = false;
            for(String name:makeFileNames)
            {
                if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(name, make))
                {
                    contains = true;
                    break;
                }
            }

            Assert.assertTrue(contains, String.format("there is no json file for make [%s]",make));

        }
    }

    @When("^Get the total number of vehicles from the files$")
    public void getTheTotalNumberOfVehiclesFromTheFiles() {
        String path;
        if(testEnvironment != null) {
            path = testEnvironment.aisSaveDir();
        }else{
            path = prop.getProperty("aisSaveDir");
        }

        int size;

        for (String make : makeFileNames) {
            String jContnent = getJsonContentFromFile(path + make);
            List<String> jsonProgramIds = JsonPath.read(jContnent, "vinVehicleGroups.*.vin");
            size = jsonProgramIds.size();
            System.out.printf("%n%s file contains %d", make, size);

            xmlTotalVins += size;
        }
        System.out.println("\nTotal vins in xml files = " + xmlTotalVins);
    }

    @Then("^The difference between the totals of vehicles should be less than (\\d+)$")
    public void theDifferenceBetweenTheTotalsOfVehiclesShouldBeLessThan(int limit) {

        int difference = abs(xmlTotalVins-getEligibleCount());

        System.out.printf("%n Total vehicle in the xml files vs DB -> %d ~ %d%n",xmlTotalVins,getEligibleCount());
        System.out.println("The difference is = "+difference);
        Assert.assertTrue(difference < limit, String.format("The difference of total vehicles in xml file [%d] " +
                "and DB [%d] is more than %d",xmlTotalVins,getEligibleCount(),limit));

    }

    @When("^Get all vins from (.+)$")
    public void getAllVinsFromVinsWithNoVehicleFile(String file) {
        String path;
        if(testEnvironment != null) {
            path = testEnvironment.aisSaveDir();
        }else{
            path = prop.getProperty("aisSaveDir");
        }

        specificVins = new ArrayList<>();
        int size;
        String jContnent = getJsonContentFromFile(path + file);
            specificVins = JsonPath.read(jContnent, "*");

        System.out.println("specificVins from "+file+" = " + specificVins.size());

            System.out.println(specificVins);

        System.out.println();

    }

    @When("^make a post request ready for AIS$")
    public void makeAPostRequestReadyForAIS() {
        rsd = new RestStepDef();
        rsd.initialization();
        rsd.the_server_endpoint_is("https://incentives.homenetiol.com/CA/v2.6/findvehiclegroupsbylistofvehicleandpostalcode");
        rsd.iAddFollowingHeader("AIS-ApiKey","85C88437-7536-48FE-8914-4383CED65BA2");
        rsd.iAddFollowingHeader("Content-Type","application/json");

    }

    @Then("^there should not be any vehicleGroups in the response$")
    public void thereShouldNotBeAnyVehicleGroupsInTheResponse() {
        for(String response :responses) {

            System.out.println("\nresponse = " + response);
            Assert.assertTrue(!response.contains("aisVehicleGroupID"), "There is a vin in VinsWithoutVehicleGroups " +
                    "that returns vehicleGroup from AIS (look at the response)-->" + response);
        }
    }

    @When("^Save vehicleHints for those vins with (\\d+) pagination$")
    public void saveVehicleHintsForThoseVins(int limit) throws InterruptedException {
        String path;
        if(testEnvironment != null) {
            path = testEnvironment.aisSaveDir();
        }else{
            path = prop.getProperty("aisSaveDir");
        }        String jContnent = getJsonContentFromFile(path + "VehicleHints.json");
        LinkedHashMap<String, String> hint = new LinkedHashMap<>();
        ArrayList<String> array = new ArrayList<>();
        responses = new ArrayList<>();
        String body = "{\"vehicleAndPostalcodeFilters\":[";
        int i = 0;

        for (String vin_acc : specificVins) {
            String vin = vin_acc.substring(0, vin_acc.indexOf('_'));
            array = JsonPath.read(jContnent, "$.vehicleAndPostalcodeFilters[?(@.vin == '" + vin + "')]");
            String hintLine = array.toString();
            hintLine = hintLine.substring(1, hintLine.length() - 1);

            body += hintLine + ",";
            System.out.println("Hint = " + hintLine);
//            System.out.println("i = "+ i + "   specificVins = " + specificVins.size());
            if(++i%limit==0 || i==specificVins.size()) {
                body.substring(0, body.length()-1);
//                body+="]}";
//                body+="{\"postalcode\":\"T3R1R8\",\"vin\":\"5N1DL0MM7KC522787\",\"vehicleHints\":{\"TRIM\":\"AWD Pure\",\"MODEL\":\"QX60\",\"MODEL_CODE\":\"J6XG19\"}}";
                System.out.println("body = " + body);
                rsd.addingStringBodyToPostRequest(body);
//                Thread.sleep(3000);
                rsd.andPerformThePostRequest();
                String response = ResponseHolder.getResponseBody();
                responses.add(response);
                body ="{\"vehicleAndPostalcodeFilters\":[";
            }

        }
    }

    @Then("^each vin should have more than (\\d) vehicleGroups$")
    public void eachVinShouldHaveMoreThanTwoVehicleGroups(int min) {
        String vinPayload;
        String responseTemp;

        int i = 0;

        for(String response : responses){
            responseTemp = new String(response);
            while(responseTemp.contains(",{\"vin\"")) {
                i++;
                vinPayload = responseTemp.substring(responseTemp.indexOf("vin"));
                responseTemp = responseTemp.substring(responseTemp.indexOf(",{\"vin\"")+2);

                vinPayload = vinPayload.substring(0, vinPayload.indexOf(",{\"vin\""));
                String vehicleGroup = new String(vinPayload);

                for(int j = 0 ; j<=min ; j++)
                {
                    Assert.assertTrue(vehicleGroup.contains("aisVehicleGroupID"),String.format("vin contains less than %d vehicleGroups -->%s",min,vinPayload));
//                    System.out.println("InnerPayload = " +vehicleGroup);
                    vehicleGroup=vehicleGroup.substring(vehicleGroup.indexOf("aisVehicleGroupID")+1);
                }
//                System.out.println(i+") vinPayload = " + vinPayload );

            }

        }
    }

    @Then("^we should not have any data with the following fields$")
    public void weShouldNotHaveAnyDataWithTheFollowingFields(DataTable dataTable) {
        List<String> fields = new ArrayList<>();
        for (DataTableRow row : dataTable.getGherkinRows()) {
           fields.add(row.getCells().get(0));
        }

        String path;
        if(testEnvironment != null) {
            path = testEnvironment.aisSaveDir();
        }else{
            path = prop.getProperty("aisSaveDir");
        }        ArrayList<HashMap<String,String>> records = new ArrayList<>();

        for (String make : makeFileNames) {
            for (String field : fields) {
                String jContnent = getJsonContentFromFile(path + make);
                records = JsonPath.read(jContnent, "aisResponses.*.response.*.vehicleGroups.*");
                for(HashMap<String,String> record: records)
                {

                    String stringRecord = record.toString();
                    System.out.println("record = " + record);
                    System.out.println(make + "  " +field + " field = " + stringRecord.contains(field));
                    Assert.assertTrue(!stringRecord.contains(field),String.format("following group contains excess data[%s] --> %s",field,stringRecord));
                }
//                System.out.printf("%n%s file contains %d records with field '%s'", make, size,field);
        }
        }

    }

    @When("^Get the vehicleGroups from the files$")
    public void getTheVehicleGroupsFromTheFiles() {

        String path;
        if(testEnvironment != null) {
            path = testEnvironment.aisSaveDir();
        }else{
            path = prop.getProperty("aisSaveDir");
        }        ArrayList<Integer> array;
        vehicleGroups = new ArrayList<>();

        for (String make : makeFileNames) {
            String jContnent = getJsonContentFromFile(path + make);
            array = JsonPath.read(jContnent, "aisResponses.*.response.*.vehicleGroups.*.vehicleGroupID");
            System.out.printf("%nrecords for %s = %s", make, array);
            vehicleGroups.add(array);
        }

    }

    @Then("^we should not have duplicate vehicleGroupIds in the files$")
    public void weShouldNotHaveDuplicateVehicleGroupIdsInTheFiles() {
        for(List<Integer> array : vehicleGroups)
        {
            System.out.println("array = " + array);
            Integer duplicate = Utils.hasDuplicates(array);
            System.out.println("duplicate = " + duplicate);
            Assert.assertTrue(duplicate==null,String.format("There is a duplicate vehicleGroup[%d] in the files ",duplicate));
        }
    }

    @When("^Get the number of unique mappings from vehicleGroupVins field$")
    public void getTheNumberOfUniqueMappingsFromVehicleGroupVinsField() {

        String path;
        if(testEnvironment != null) {
            path = testEnvironment.aisSaveDir();
        }else{
            path = prop.getProperty("aisSaveDir");
        }        List<String> list;
        vehicleGroupVinsSize = 0;
        for (String make : makeFileNames) {
            String jContnent = getJsonContentFromFile(path + make);
            list = JsonPath.read(jContnent, "vehicleGroupVins.*.*");
            System.out.println(list);
            System.out.println(make + " list size = " + list.size());
            vehicleGroupVinsSize += list.size();

        }
        System.out.println("\nThe ultimate size of vehicleGroupVinsSize = " + vehicleGroupVinsSize);
        System.out.println();


    }

    @When("^Get the number of unique mappings from vinVehicleGroups field$")
    public void getTheNumberOfUniqueMappingsFromVinVehicleGroupsField() {
        String path;
        if(testEnvironment != null) {
            path = testEnvironment.aisSaveDir();
        }else{
            path = prop.getProperty("aisSaveDir");
        }        List<String> list;
        vinVehicleGroupsSize = 0;
        for (String make : makeFileNames) {
            String jContnent = getJsonContentFromFile(path + make);
            list = JsonPath.read(jContnent, "vinVehicleGroups.*.vehicleGroupIds.*");
            System.out.println(list);
            System.out.println(make + " list size = " + list.size());
            vinVehicleGroupsSize += list.size();

        }
        System.out.println("The ultimate size of vinVehicleGroupSize = " + vinVehicleGroupsSize);
        System.out.println();
    }

    @Then("^the total Number of mappings should be equalsIgnoreCase$")
    public void theTotalNumberOfMappingsShouldBeEqualsIgnoreCase() {
        System.out.println("vehicleGroupVinSize = " + vehicleGroupVinsSize + " = vinVehicleGroupsSize = " + vinVehicleGroupsSize );
        Assert.assertEquals(vehicleGroupVinsSize,vinVehicleGroupsSize, String.format(
                "The total #s of vehicleGroupVinsSize[%d] is not equal to vehicleGroupVinsSize[%d]",vehicleGroupVinsSize,vinVehicleGroupsSize));
    }


//    @When("^ForXMLFile$")
//    public void forXMLFiles() {
//        for(String fileName : makeFileNames) {
//            xmlFile = prop.getProperty("aisSaveDir" + fileName);
//            xpath = getXpath(xmlFile);
//
//
//
//        }
//    }


}




package stepDefinitions;


import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import org.w3c.dom.NodeList;
import resources.SFTPConnection;
import resources.base;

import javax.xml.xpath.XPath;
import java.io.File;
import java.util.*;

import static resources.Utils.getJsonContentFromFile;
import static resources.Utils.getXpath;

public class DownloadStepDef extends base {
    List<String> makeFileNames;
    int xmlTotalVins;
    private NodeList vehicleNodes;
    private String xmlFile;
    private XPath xpath;


    @Given("^Download Initialization$")
    public void operInitialization() {
        System.out.println("Inside Download Init ---------------");
        initBase();

    }
    @When("^load all ais files$")
    public void loadAllAisFiles() {
        SFTPConnection con = new SFTPConnection();
        con.downloadTheFilesFromAISFeed(getFeedRunId());
    }

    @When("^Get the number of ais CA eligible vehicles for all subscribed accounts and makes from Nexus$")
    public void getTheNumberOfAisCAEligibleVehiclesForAllSubscribedAccountsAndMakes() {
        String account,make;
        int eligibleCount;
        List<String> accMakes = q_c.getAccount_makePairsWithSourceAccountId("aiscaincentivesaccount");
        System.out.println(accMakes);
        for(String s : accMakes)
        {
            account = s.substring(0,s.indexOf('_'));
            make = s.substring(s.indexOf('_')+1);
            System.out.printf("account = %s, make = %s",account,make);
            eligibleCount = q_n.getNumberOfVehiclesWithAccountIdOemAndNewAndNotRemoved(account,make);
            System.out.println("eligibleCount = " + eligibleCount);
            setEligibleCount(getEligibleCount()+eligibleCount);
        }

        System.out.println("Count of all eligible vehicles = " + getEligibleCount());
    }


    @When("^Get the names of all make files downloaded from ais$")
    public void getTheNamesOfAllMakeFilesDownloadedFromAis() {
        makeFileNames = new ArrayList<>();
        File folder = new File(prop.getProperty("aisSaveDir"));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains("AIS_CA")) {
                System.out.println("File " + listOfFiles[i].getName());
                makeFileNames.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        System.out.println("MakeFileNames = "+ makeFileNames);
    }

    @When("^Get the total number of vehicles from the files$")
    public void getTheTotalNumberOfVehiclesFromTheFiles() {
        String path = prop.getProperty("aisSaveDir");
        int size;

        for (String make : makeFileNames) {
            String jContnent = getJsonContentFromFile(path + make);
            List<String> jsonProgramIds = JsonPath.read(jContnent, "vinVehicleGroups.*.vin");
            size = jsonProgramIds.size();
            System.out.printf("%n%s file contains %d ->", make, size);

            xmlTotalVins += size;
        }
        System.out.println("\nTotal vins in xml files = " + xmlTotalVins);
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




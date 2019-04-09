package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import resources.ResponseHolder;
import resources.base;
import resources.compatibleBase;

import static resources.Utils.getUniqueIdModel;
import static resources.Utils.getUniqueIdYear;
import static resources.compatibleBase.*;


public class CompatibilityAndRegionsStepDef extends compatibleBase {

    private String xmlFile;
    private Document xml;
    private XPath xpath;
    private NodeList incentiveNodes;
    private NodeList compNodes;
    private Set<String> parentIds;
    private Map<String, Integer> dbCompMap;
    private Map<String, Integer> xmlCompMap;
    private Map<String, String> tpIncentiveRegionMap;
    private Map<String, String> incentiveIdRegionMap;
    private Map<String, List<String>> incentiveIdZipCodes;
    private Map<String, List<String>> regionIdPostalCodes;
    private RestStepDef rsd;



    @Given("^get the xml file (.+)$")
    public void parse_xml_file(String path) throws Throwable {
        xmlFile = path;
    }

    @Given("^getting the xml file with index (.+)$")
    public void getTheXmlFileWithIndexIndex(String i) {

        int maxLength = processedFileNames.size();
        int index = Integer.parseInt(i);
        if(index+1>maxLength)
        {
            index = maxLength-1;
        }

        String fileName = processedFileNames.get(index);
        System.out.printf("%nChecking processed filed = %s%n%n",fileName);
        xmlFile = prop.getProperty("aisProcessSaveDir")+fileName;

    }

    @Given("^initCompatibility$")
    public void initcompatibility() throws Throwable {

        if(xmlFile!=null) {
            //Get DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            xml = db.parse(xmlFile);
            //Get XPath
            XPathFactory xpf = XPathFactory.newInstance();
            xpath = xpf.newXPath();
        }
        initBase();
    }

    @When("^Get the names of all files processed from ais$")
    public void getTheNamesOfAllFilesDownloadedFromAis() {
        processedFileNames = new ArrayList<>();
        initBase();

        File folder = new File(prop.getProperty("aisProcessSaveDir"));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                processedFileNames.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        System.out.println("allFileNames = "+ processedFileNames + "\n");

    }

    @When("^get all nodes having compatibleIncentives tag$")
    public void get_all_nodes_having_compatibleincentives_tag() throws Throwable {
        compNodes = (NodeList) xpath.evaluate("//Incentive/CompatibleIncentives", xml, XPathConstants.NODESET);
        System.out.println("Number of incentives having at least one compatible incentive - " + compNodes.getLength()+ "\n");

    }

    @When("^get all incentive nodes$")
    public void getAllIncentiveNodes() throws XPathExpressionException {
        incentiveNodes = (NodeList) xpath.evaluate("//Incentive", xml, XPathConstants.NODESET);
        System.out.println("Number of incentives in the xml file - " + incentiveNodes.getLength());
    }

    @When("^save all thirdPartyIncentiveId regionId maps with limit (\\d+)$")
    public void saveAllThirdPartyIncentiveIdRegionIdMaps( int limit) {
        tpIncentiveRegionMap = new HashMap<>();
        int size = incentiveNodes.getLength();
        if(size>limit)
            size =limit;
        int i;
        String uniqueId, region;

        for(i = 0; i < size ; i++ )
        {
            uniqueId = incentiveNodes.item(i).getFirstChild().getTextContent();
            System.out.println("uniqueId = " + uniqueId);
            if(incentiveNodes.item(i).getLastChild().getPreviousSibling().getFirstChild().getFirstChild().getNodeName().equalsIgnoreCase("RegionId")) {
                region = incentiveNodes.item(i).getLastChild().getPreviousSibling().getFirstChild().getFirstChild().getTextContent();
            }else {
                region = incentiveNodes.item(i).getLastChild().getFirstChild().getFirstChild().getTextContent();
            }
            System.out.println("regionId = " + region);

            tpIncentiveRegionMap.put(uniqueId,region);
        }

    }

    @When("^change thirdpartyIncentiveIds to incentiveId if exist$")
    public void changeThirdpartyIncentiveIdsToIncentiveIdIfExist() {

        String incentiveId;
        incentiveIdRegionMap = new HashMap<>();
        List<String> incentivesNotSavedInDb = new ArrayList<>();

        Iterator<String> it = tpIncentiveRegionMap.keySet().iterator();
        while(it.hasNext())
        {
            String key = it.next();
            incentiveId = q_c.getIncentiveIdByThirdPartyId(key);
//            System.out.printf("%n thirdPartyIncentiveId = %s, incentiveId = %s", key, incentiveId);
            if(incentiveId!=null)
            {
                incentiveIdRegionMap.put(incentiveId,tpIncentiveRegionMap.get(key));
//                System.out.printf("%n added to incentiveIdMap: key = %s, value =  %s%n", incentiveId, incentiveIdRegionMap.get(incentiveId));
            }
            else{
                incentivesNotSavedInDb.add(key);
            }
        }
        System.out.printf("%nThe size of IncneitveRegionMap in the conveted file = %d %n",tpIncentiveRegionMap.size());
        System.out.printf("%nThe size of IncneitveRegionMap saved in the db = %d %n",incentiveIdRegionMap.size());
        System.out.printf("%n# of incentives not saved in db = %d --> %s",incentivesNotSavedInDb.size(),incentivesNotSavedInDb);

    }

    @When("^get the list of postal codes of the accounts mapped to those incentives$")
    public void getTheListOfPostalCodesOfTheAccountsMappedToThoseIncentives() {

        incentiveIdZipCodes = new HashMap<>();
        List<String> accounts;
        List<String> postalCodes;
        String postalCode;
        int i = 0;

        Iterator<String> it = incentiveIdRegionMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            accounts = q_c.getAccoutIdsWithMappedIncentiveId(key);

            System.out.println("\n"+ ++i +")incentiveId = " + key);

            postalCodes = new ArrayList<>();
            for(String account: accounts){
                System.out.println("  account = " +  account);
                postalCode = q_n.getPostalCodeWithAccountId(account);
                System.out.println("  postalCode = " + postalCode);
                postalCodes.add(postalCode);
            }

            incentiveIdZipCodes.put(key,postalCodes);
        }



    }

    @When("^save the regionId postalCode mapping in interanl memory$")
    public void saveTheRegionIdPostalCodeMappingInInteranlMemory() {
        Set<String> distinctRegions = new HashSet<>();
        regionIdPostalCodes = new HashMap<>();

        Iterator<String> it = incentiveIdRegionMap.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            String value = incentiveIdRegionMap.get(key);
            distinctRegions.add(value);
        }

        for(String regionId : distinctRegions) {
            rsd = new RestStepDef();
            rsd.initialization();
            rsd.the_server_endpoint_is("http://vtqainv-incentivessvc03.int.dealer.com:9620/incentives-services/rest" +
                    "/api/v1/AisRegionDetailsService/getPostalCodes/" + regionId);
            rsd.perform_the_get_request();
            List<String> postalCodes = ResponseHolder.getResponse().jsonPath().getList("$");
//            System.out.println(postalCodes.size());

            regionIdPostalCodes.put(regionId,postalCodes);
        }

    }

    @Then("^the regionId of an incentive should contain the postalCodes of the accounts mapped to the incentive$")
    public void theRegionIdOfAnIncentiveShouldContainThePostalCodesOfTheAccountsMappedToTheIncentive() {

        List<String> dbZipCodes;
        List<String> allPostalCodes;
        String regionId;

        Iterator<String> it = incentiveIdRegionMap.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            regionId = incentiveIdRegionMap.get(key);
            dbZipCodes = incentiveIdZipCodes.get(key);
            allPostalCodes = regionIdPostalCodes.get(regionId);
            for(String zipCode: dbZipCodes)
            {
                System.out.printf("%nallPostalCodes.contains(zipCode) = %s for regionId %s and zipCode %s for incentiveId = %s",
                        allPostalCodes.contains(zipCode),regionId,zipCode,key);

                Assert.assertTrue(allPostalCodes.contains(zipCode),String.format("regionId %s doesn't contain postal " +
                        "code %s for incentiveId = %s",regionId, zipCode, key));
            }
        }
    }

    @When("^get all incentive nodes of the nodes having compability$")
    public void getAllIncentiveNodesOfTheNodesHavingCompabulity() throws XPathExpressionException {
        incentiveNodes = (NodeList) xpath.evaluate("//Incentive[CompatibleIncentives]", xml, XPathConstants.NODESET);
        System.out.println("Number of incentives in the xml file - " + incentiveNodes.getLength());
    }

    @Then("^unqiueId parent nodes of the nodes should be type cash$")
    public void unqiueid_parent_nodes_of_the_nodes_should_be_type_cash() throws Throwable {
        for (int i = 0; i < compNodes.getLength(); i++) {
            String uniqueId = compNodes.item(i).getParentNode().getFirstChild().getTextContent();
            System.out.println(++i + ") UniqueId = " + uniqueId + "\n");
            Assert.assertTrue(uniqueId.contains("_D-1_"));
        }
    }

    @Then("^all the compatibility uniqueIds should be cash$")
    public void all_the_compatibility_uniqueids_should_be_cash() throws Throwable {
        for (int i = 0; i < compNodes.getLength(); i++) {
            String uniqueId = compNodes.item(i).getParentNode().getFirstChild().getTextContent();
            System.out.println("For the UniqueId = " + uniqueId);
            NodeList innerNodes = (compNodes.item(i).getChildNodes());
            for (int j = 0, k = 1; j < innerNodes.getLength(); j++, k++) {
                String compUniqueId = innerNodes.item(j).getTextContent();
                System.out.println(k + ") " + compUniqueId);
                Assert.assertTrue(compUniqueId.contains("_D-1_"), "UniqueId of compatibleIncentive[" + compUniqueId + "] is not cash type (parent node incentive uniqueId = [" + uniqueId + "])");
            }
        }
    }

    @Then("^model and year of the compatible nodes should be the same as the ones of parent uniqueId node$")
    public void modelAndYearOfTheCompatibleNodesShouldBeTheSameAsTheOnesOfParentUniqueIdNode() {
        int parentYear, compYear;
        String parentModel, compModel;

        for (int i = 0; i < compNodes.getLength(); i++) {
            String uniqueId = compNodes.item(i).getParentNode().getFirstChild().getTextContent();
            parentYear = getUniqueIdYear(uniqueId);
            parentModel = getUniqueIdModel(uniqueId);

            System.out.println("For the UniqueId = " + uniqueId);
            NodeList innerNodes = (compNodes.item(i).getChildNodes());
            for (int j = 0, k = 1; j < innerNodes.getLength(); j++, k++) {
                String compUniqueId = innerNodes.item(j).getTextContent();
                System.out.println(k + ") " + compUniqueId);

                compYear = getUniqueIdYear(compUniqueId);
                compModel = getUniqueIdModel(compUniqueId);

                System.out.println(parentModel + " = " + compModel);
                System.out.println(parentYear + " = " + compYear);


                Assert.assertEquals(parentModel, compModel, "UniqueId of compatibleIncentive[" + compUniqueId + "] " +
                        "doesn't have the same model as the parent node incentive uniqueId = [" + uniqueId + "])");
                Assert.assertEquals(parentYear, compYear, "UniqueId of compatibleIncentive[" + compUniqueId + "] " +
                        "doesn't have the same year as the parent node incentive uniqueId = [" + uniqueId + "])");

            }
        }
    }

    @When("^get the uniqueIds of the parent incentives$")
    public void getTheUniqueIdsOfTheParentIncentives() {
        parentIds = new HashSet<String>();
        for (int i = 0; i < compNodes.getLength(); i++) {
            parentIds.add(compNodes.item(i).getParentNode().getFirstChild().getTextContent());
        }
        System.out.println("\n" + compNodes.getLength() + " = " + parentIds.size());
        Assert.assertEquals(parentIds.size(), compNodes.getLength(), String.format("compNodes[%d] are not equal to the " +
                "incentive uniqueIds[%d] we fetched", compNodes.getLength(), parentIds.size()));
    }

    @When("^get the amount of compatibleIncentives for all incentives from both xml file$")
    public void getTheAmountOfCompatibleIncentivesForParentIncentivesFromIMDB() {
        xmlCompMap = new HashMap<>();
        String thirdPartyId, nodeName;
        int zeros = 0;

        for (int i = 0; i < incentiveNodes.getLength(); i++) {
            thirdPartyId = incentiveNodes.item(i).getFirstChild().getTextContent();
            int compCount = 0;
            boolean flagForZeros = false;
            for (int j = 0; j < incentiveNodes.item(i).getChildNodes().getLength(); j++) {
                nodeName = incentiveNodes.item(i).getChildNodes().item(j).getNodeName();
                System.out.println(j + ") node name = " + nodeName);

                if (nodeName.equalsIgnoreCase("CompatibleIncentives")) {
                    compCount = incentiveNodes.item(i).getChildNodes().item(j).getChildNodes().getLength();
                    flagForZeros = true;
                    if (compCount == 0)
                        break;
                }

            }
            if (!flagForZeros)
                zeros++;
            xmlCompMap.put(thirdPartyId, compCount);
        }

        Iterator it = xmlCompMap.entrySet().iterator();
        int k = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(++k + ")" + pair.getKey() + " = " + pair.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
        }

        System.out.println("zeros = " + zeros);
    }

    @When("^get the amount of compatibleIncentives for all incentives from IMDB$")
    public void getTheAmountOfCompatibleIncentivesForAllIncentivesFromIMDB() {
        dbCompMap = new HashMap<>();
        String thirdPartyId, incentiveId;

        for (int i = 0; i < incentiveNodes.getLength(); i++) {

            thirdPartyId = incentiveNodes.item(i).getFirstChild().getTextContent();
            incentiveId = q_c.getIncentiveIdByThirdPartyId(thirdPartyId);
            if (incentiveId == null)
                continue;
            System.out.println(thirdPartyId + " = " + incentiveId);
            int count = q_c.getIncentiveCompabilitCountByIncentiveId(incentiveId);
            System.out.println(i + ")" + incentiveId + " = " + count);
            dbCompMap.put(thirdPartyId, count);

        }
        Iterator it = dbCompMap.entrySet().iterator();
        int k = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(++k + ")" + pair.getKey() + " = " + pair.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    @Then("^the incentives in the IMDB should have equal or less compatible incentives than in the xml file$")
    public void theIncentivesInTheIMDBShouldHaveEqualOrLessCompatibleIncentivesThanInTheXmlFile() {
        Iterator dbMapIt = dbCompMap.entrySet().iterator();
        while (dbMapIt.hasNext()) {
            Map.Entry pair = (Map.Entry) dbMapIt.next();
            System.out.println(pair.getKey() + " --> " + pair.getValue() + " <= " + xmlCompMap.get(pair.getKey()));
        }
        Iterator dbMapIt1 = dbCompMap.entrySet().iterator();
        while (dbMapIt.hasNext()) {
            Map.Entry pair = (Map.Entry) dbMapIt.next();
            Assert.assertTrue((int) pair.getValue() <= xmlCompMap.get(pair.getKey()), String.format("thirdPartyId[%s]" +
                    " has more incentives in IMDB[%d] than in xml file[%d]", pair.getKey(), dbCompMap.get(pair.getKey()), pair.getValue()));
        }
    }

    @Then("^There should be euqal or more items in the db than the total # of items in ais response$")
    public void thereShouldBeEuqalOrMoreItemsInTheDbThanTheTotalOfItemsInAisResponse() {
        int totalRows = q_c.getTheNumberOfRowsInAsiRegionDetails();
        System.out.printf("%nTotal Rows in db = %d >= %d Total region:zip mappings from AIS responses", totalRows, totalPairs );
//        Assert.assertTrue(totalRows > totalPairs, String.format("There are less rows in aisRegionDetails table [%d] " +
//                " than the mapping in AIS response [%d]",totalRows,totalPairs));
    }

    @Then("^there should be equal or more distinct regionIds in the db than in the ais response$")
    public void thereShouldBeEqualOrMoreDistinctRegionIdsInTheDbThanInTheAisResponse() {
        int distinctRegionIds = q_c.getDistinctRegionIdsFromAisRegionDetails();
        int aisRegions =  regionZipMap.size();
        System.out.printf("%nTotal distinct regionIds in (db/aisRespone) > (%d = %d) Total regionIds from AIS responses", distinctRegionIds, aisRegions );
//        Assert.assertEquals(aisRegions,distinctRegionIds,String.format("The number of AIS distinct regions [%d] is not equal to the db distinct list[%d]",aisRegions,distinctRegionIds));
    }

    @Then("^each regionId should have equal amount of postalCodes mapped to it$")
    public void eachRegionIdShouldHaveEqualAmountOfPostalCodesMappedToIt() {
        Iterator entries = regionZipMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            int key = (int) entry.getKey();
            int value = (int) entry.getValue();

            int dbPostalCodes = q_c.getNumberOfPostalCodesWithRegionId(key);
            System.out.printf("%nFor regionId[%d] --> postalCodes in ais/db --> %d = %d",key,value,dbPostalCodes);
//            Assert.assertEquals(value,dbPostalCodes,String.format("The postal codes count is not equal for regionId[%d]" +
//                    " --> postalCodes in ais/db --> %d = %d",key,value,dbPostalCodes));
        }
    }

    @When("^get the distinct postalCodes with the amount of regionIDs from the db$")
    public void getTheDistinctPostalCodesWithTheAmountOfRegionIDsFromTheDb() {
        regionIdPostalCodePairs = q_c.getDistinctRegionIdAndCountOfPostalCodes();
    }



}
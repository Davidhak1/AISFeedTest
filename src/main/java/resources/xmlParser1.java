package resources;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class xmlParser1
{
    public static void main(String[] args) throws Exception
    {

        String xmlFile = "src/main/java/resources/AIS_CA-FORD2.xml";

        //Get DOM
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xml = db.parse(xmlFile);

        //Get XPath
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();

        //Get first match

        String name = (String) xpath.evaluate("//Incentive[UniqueId='FORD_2018_F-150_P-18528_D-1_1633763509_No-Tier']/Make", xml, XPathConstants.STRING);

        String name1 = (String) xpath.evaluate("count(//Incentive/CompatibleIncentives)", xml, XPathConstants.STRING);


        System.out.println(name1);   //Lokesh

        //Get all matches
        NodeList nodes = (NodeList) xpath.evaluate("//Incentives/Incentive[UniqueId='FORD_2019_Edge_P-19535_D-1_1672674275_No-Tier']/CompatibleIncentives/UniqueId", xml, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getTextContent());   //1 2
        }
        NodeList nodes2 = (NodeList) xpath.evaluate("//Incentive/CompatibleIncentives", xml, XPathConstants.NODESET);

        for (int i = 0; i < nodes2.getLength(); i++) {
            System.out.println("\nFor UniqueId = "+nodes2.item(i).getParentNode().getFirstChild().getTextContent() + "\n");
            NodeList innerNodes = (nodes2.item(i).getChildNodes());
            for (int j = 0, k = 1; j < innerNodes.getLength(); j++,k++)
                System.out.println(k+") " + innerNodes.item(j).getTextContent());   //1 2
        }


    }
}
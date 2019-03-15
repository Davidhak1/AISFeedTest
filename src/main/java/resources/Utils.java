package resources;


import io.restassured.path.json.JsonPath;
import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

public class Utils {

    public static Set<Integer> findDuplicates(List<Integer> listContainingDuplicates) {
        final Set<Integer> setToReturn = new HashSet();
        final Set<Integer> set1 = new HashSet();

        for (Integer yourInt : listContainingDuplicates) {
            if (!set1.add(yourInt)) {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn;
    }

    public static String clobToString(Clob data) {
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = null;
            try {
                reader = data.getCharacterStream();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(reader);

            String line;
            while (null != (line = br.readLine())) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            // handle this exception
        }
        return sb.toString();
    }

    public static Properties initProp(String path) {
        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream(path);
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public static void main(String[] args) {

//            List<Integer> a = new ArrayList<>();
//            List<Integer> b = new ArrayList<>();
//
//            a.add(1);
//            a.add(2);
//            a.add(3);
//            a.add(4);
//
//            b.add(5);
//            b.add(6);
//            b.add(3);
//            b.add(4);
//
//            System.out.println(a);
//            System.out.println(b);
//
//        Collection<Integer> aMinusB = CollectionUtils.subtract(a,b);
//            System.out.println(aMinusB);
//
//        System.out.println(aMinusB.isEmpty());

        getUniqueIdYear("FORD_2019_Transit Connect_P-18528_D-1_1633763509_No-Tier");
        getUniqueIdModel("FORD_2019_Transit Connect_P-18528_D-1_1633763509_No-Tier");
    }

    public static int getUniqueIdYear(String id) {
        int year;
        id = id.substring(id.indexOf('_') + 1);
        year = Integer.parseInt(id.substring(0, id.indexOf('_')));
        return year;
    }

    public static String getUniqueIdModel(String id) {
        String model;
        id = id.substring(id.indexOf('_') + 1);
        id = id.substring(id.indexOf('_') + 1);
        model = id.substring(0, id.indexOf('_'));
        return model;
    }

    public static String getTheRightFolderName(List<String> fileNames, String feedRunId) {
        for (String s : fileNames) {
            if (s.contains(feedRunId))
                return s;
        }
        System.out.println("\nERROR --- There is no folder in ais-directory containing following feedRunId in the name ->" + feedRunId);
        return null;
    }

    public static XPath getXpath(String xmlFile) {
        Document xml;
        XPath xpath = null;
        try {
            if (xmlFile != null) {
                //Get DOM
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                xml = db.parse(xmlFile);
                //Get XPath
                XPathFactory xpf = XPathFactory.newInstance();
                xpath = xpf.newXPath();
                return xpath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xpath;
    }


    public static String getJsonContentFromFile(String path) {
        String line = null;
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                System.out.println(line);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
            return line;
        }
    }
}


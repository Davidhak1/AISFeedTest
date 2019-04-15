package resources;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.AISIncentive;
import model.VehicleGroup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class compatibleBase extends base{

    public static Set<Integer> distinctRegions;
    public static List<Integer> notDistinctRegions;
    public static Map<Integer,Integer> regionZipMap;
    public static Map<Integer,List<String>> AISregionPostalCodesMap;
    public static int totalPairs;
    public static List<String> processedFileNames;
    public static Map<String,Integer> regionIdPostalCodePairs;


}

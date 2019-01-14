package resources;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.AISIncentive;
import model.VehicleGroup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class base {

    public Properties prop;
    public QueriesAISIncentive q_a;
    public QueriesNexusVehicle q_n;
    public QueriesCombined q_c;

    public static ResponseHolder responseHolder;
    public static Response response;
    public static RequestSpecification request;

    private static String feedRunId;
    private static int eligibleCount;
    private static int aisIncentivesCount;

    private static AISIncentive aisIncentive;
    private static VehicleGroup vehicleGroup;
    private static List<AISIncentive> aisIncentives;




    public void initBase() {
//    System.out.println("INITIALIZING QueriesAISIncentive\n");
        q_a = new QueriesAISIncentive();
        q_n = new QueriesNexusVehicle();
        q_c = new QueriesCombined();


        initProp();
    }

    public void initProp() {
        prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream("src/main/java/resources/data.properties");
            prop.load(fis);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public static String getFeedRunId() {
        return feedRunId;
    }

    public static void setFeedRunId(String feedRunId) {
        base.feedRunId = feedRunId;
    }

    public static int getEligibleCount() {
        return eligibleCount;
    }

    public static void setEligibleCount(int eligibleCount) {
        base.eligibleCount = eligibleCount;
    }

    public static int getAisIncentivesCount() {
        return aisIncentivesCount;
    }

    public static void setAisIncentivesCount(int aisIncentivesCount) {
        base.aisIncentivesCount = aisIncentivesCount;
    }


    public static AISIncentive getAisIncentive() {
        return aisIncentive;
    }

    public static void setAisIncentive(AISIncentive aisIncentive) {
        base.aisIncentive = aisIncentive;
    }

    public static VehicleGroup getVehicleGroup() {
        return vehicleGroup;
    }

    public static void setVehicleGroup(VehicleGroup vehicleGroup) {
        base.vehicleGroup = vehicleGroup;
    }

    public static List<AISIncentive> getAisIncentives() {
        return aisIncentives;
    }

    public static void setAisIncentives(List<AISIncentive> aisIncentives) {
        base.aisIncentives = aisIncentives;
    }


    public static ResponseHolder getResponseHolder() {
        return responseHolder;
    }

    public static void setResponseHolder(ResponseHolder responseHolder) {
        base.responseHolder = responseHolder;
    }

    public static Response getResponse() {
        return response;
    }

    public static void setResponse(Response response) {
        base.response = response;
    }

    public static RequestSpecification getRequest() {
        return request;
    }

    public static void setRequest(RequestSpecification request) {
        base.request = request;
    }
}

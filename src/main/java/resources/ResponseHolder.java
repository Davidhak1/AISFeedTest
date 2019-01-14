package resources;

import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.concurrent.ExecutionException;

public class ResponseHolder {
    public static Response response;
    public static int responseCode;
    public static String responseBody;
    public static Headers responseHeaders;

    public static void setResponse (Response response) {
        ResponseHolder.response = response;
        System.out.println(getResponseBody()+"\n");
    }

    public static Response getResponse() { return response; }

    public static int getResponseCode(){
        responseCode = response.getStatusCode();
        return  responseCode;
    }

    public static String getResponseBody(){
        responseBody = response.getBody().asString();
        return responseBody;
    }


    public static Headers getResponseHeaders(){
        responseHeaders = response.getHeaders();
        return  responseHeaders;
    }

    public static int lengthOfArray(String filter){
        Response response = ResponseHolder.getResponse();


        if(getResponseBody().isEmpty())
        {
            return 0;
        }
        else
        {
            try {
                return response.jsonPath().getList(filter).size();
            }catch (Exception e){
                System.out.println("\nPROBABLY WRONG NODE GIVEN");
                e.printStackTrace();
                return -1;
            }
        }
        }


    public static JsonPath getResponseJsonPath() {
        return response.jsonPath();
    }
}

package resources;

import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResponseHolder {
    public static Response response;
    public static int responseCode;
    public static String responseBody;
    public static Headers responseHeaders;

    public static void setResponse (Response response) {
        ResponseHolder.response = response;
        System.out.println(getResponseBody());
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
            return response.jsonPath().getList(filter).size();
        }
    }

    public static JsonPath getResponseJsonPath() {
        return response.jsonPath();
    }
}

package resources;


import model.IMDBVehicle;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Queries {

    private MysqlCon mysqlCon= new MysqlCon();

    private int numberOfAisIncentivesWithFeedRunID(String feedRunID)
    {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from aisIncentive where feedRunId = '%s';", feedRunID));

            while (rs.next()) {
                count++;
            }

        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        }

        finally{
            mysqlCon.endCon();
            return count;
        }

    }

    private int numberOfAisIncentivesWithFeedRunIDAndAcccountId(String feedRunID, String accountId)
    {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from aisIncentive where feedRunId = '%s'" +
                    "and accountId = '%s';", feedRunID, accountId));

            while (rs.next()) {
                count++;
            }

        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        }

        finally{
            mysqlCon.endCon();
            return count;
        }

    }

    private int numberOfAisIncentivesWithFeedRunIDAndAcccountIdAndMake(String feedRunID, String accountId, String make)
    {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from aisIncentive where feedRunId = '%s'" +
                    "and accountId = '%s' and make = '%s';", feedRunID, accountId, make));

            while (rs.next()) {
                count++;
            }

        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        }

        finally{
            mysqlCon.endCon();
            return count;
        }

    }




}
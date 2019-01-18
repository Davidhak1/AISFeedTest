package resources;


import model.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueriesDisclaimer {

    private MysqlConAIS mysqlCon= new MysqlConAIS();


    public AISIncentive getAisIncentivesById(long id){
        Statement stmt = mysqlCon.getStatement();

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from aisIncentive where id = '%d';", id));

            while (rs.next()) {
                return new AISIncentive(rs.getLong(1), rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getTimestamp(6));
            }

        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        }

        finally{
            mysqlCon.endCon();
        }
        System.out.println("No aisIncentives found with 'id' = "+id);
        return null;

    }

    public int numberOfAisIncentivesWithFeedRunID(String feedRunID)    {
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

    public List<Integer> GetAllProgramLocalIds() {
        Statement stmt = mysqlCon.getStatement();
        List<Integer> programIDs = new ArrayList<Integer>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select programID from programLocal"));

            while (rs.next()) {
                programIDs.add(rs.getInt(1));
            }

            if(programIDs.size()>0)
                return programIDs;
        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        }
        finally{
            mysqlCon.endCon();
        }
        System.out.println("No programLocals found");
        return null;
    }




}
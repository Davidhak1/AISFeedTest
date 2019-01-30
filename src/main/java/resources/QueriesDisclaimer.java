package resources;


import model.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueriesDisclaimer {

    private MysqlConAIS mysqlCon= new MysqlConAIS();


    public List<Integer> GetAllProgramLocalProgramIDs() {
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

    public Set<Integer> GetAllProgramLocalIDs() {
        Statement stmt = mysqlCon.getStatement();
        Set<Integer> IDs = new HashSet<Integer>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select id from programLocal"));

            while (rs.next()) {
                IDs.add(rs.getInt(1));
            }

            if(IDs.size()>0)
                return IDs;
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

    public Set<Integer> getAllProgramLocalDescriptionProgramLocalIDs() {
        Statement stmt = mysqlCon.getStatement();
        Set<Integer> IDs = new HashSet<Integer>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select ProgramLocalId from programLocalDescription"));

            while (rs.next()) {
                IDs.add(rs.getInt(1));
            }

            if(IDs.size()>0)
                return IDs;
        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        }
        finally{
            mysqlCon.endCon();
        }
        System.out.println("No programLocalDescriptionss found");
        return null;
    }

    public Set<Integer> getAllProgramProgramIDs() {
        Statement stmt = mysqlCon.getStatement();
        Set<Integer> IDs = new HashSet<Integer>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select programID from program"));

            while (rs.next()) {
                IDs.add(rs.getInt(1));
            }

            if(IDs.size()>0)
                return IDs;
        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        }
        finally{
            mysqlCon.endCon();
        }
        System.out.println("No programLocalDescriptionss found");
        return null;
    }

    public Set<Integer> getAllProgramLocalProgramIDs() {
        Statement stmt = mysqlCon.getStatement();
        Set<Integer> IDs = new HashSet<Integer>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select programId from programLocal"));

            while (rs.next()) {
                IDs.add(rs.getInt(1));
            }

            if(IDs.size()>0)
                return IDs;
        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        }
        finally{
            mysqlCon.endCon();
        }
        System.out.println("No programLocalDescriptionss found");
        return null;
    }

    public ProgramLocal getProgramLocalByProgramID(int programID){
        Statement stmt = mysqlCon.getStatement();

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from programLocal where programID = '%d';", programID));

            while (rs.next()) {
                return new ProgramLocal(rs.getLong(1), rs.getLong(2),rs.getLong(3),
                        rs.getString(4));
            }

        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        }

        finally{
            mysqlCon.endCon();
        }
        System.out.println("No ProgramLocals found with 'programID' = "+programID);
        return null;
    }

    public ProgramLocalDescription getProgramLocalDescriptionByProgramLocalID(long programLocalID){
        Statement stmt = mysqlCon.getStatement();

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from programLocalDescription where programLocalId = '%d';", programLocalID));

            while (rs.next()) {
                return new ProgramLocalDescription(rs.getLong(1), rs.getString(2),rs.getLong(3),
                        rs.getString(4),rs.getClob(5),rs.getString(6),rs.getString(7),
                        rs.getString(8), rs.getString(9));
            }

        }catch (Exception e){
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        }

        finally{
            mysqlCon.endCon();
        }
        System.out.println("No ProgramLocalDescriptions found with 'programLocalID' = "+programLocalID);
        return null;
    }


}
package resources;


import model.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueriesAISIncentive {

    private MysqlConAIS mysqlCon = new MysqlConAIS();


    public AISIncentive getAisIncentivesById(long id) {
        Statement stmt = mysqlCon.getStatement();

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from aisIncentive where id = '%d';", id));

            while (rs.next()) {
                return new AISIncentive(rs.getLong(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getTimestamp(6));
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println("No aisIncentives found with 'id' = " + id);
        return null;

    }

    public VehicleGroup getVehicleGroupByID(long id) {
        Statement stmt = mysqlCon.getStatement();

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from vehicleGroup where id = '%d';", id));

            while (rs.next()) {
                return new VehicleGroup(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getLong(6), rs.getInt(7), rs.getInt(8),
                        rs.getLong(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getTimestamp(13));
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println("No vehicleGroup found with 'id' = " + id);
        return null;

    }

    public int numberOfAisIncentivesWithFeedRunID(String feedRunID) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from aisIncentive where feedRunId = '%s';", feedRunID));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public int numberOfAisIncentivesWithFeedRunIDAndAcccountId(String feedRunID, String accountId) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from aisIncentive where feedRunId = '%s'" +
                    "and accountId = '%s';", feedRunID, accountId));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public int numberOfVehicleGroupsWithDistinctVinByFeedRunIdAccountIdAndMake(String feedRunID, String accountId, String make) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select  vg.id, vg.vin, vg.aisIncentiveId, vg.aisVehicleGroupId," +
                    " vg.vehicleGroupName, vg.vehicleGroupId, vg.modelYear, vg.marketingYear, vg.regionId, vg.hash, vg.vehicleHints," +
                    " vg.exclusionHints, vg.created from aisIncentive ai join vehicleGroup vg on ai.id = vg.aisIncentiveId" +
                    " where ai.feedRunId = '%s' and ai.accountId = '%s' and ai.make = '%s' " +
                    "group by vg.vin;", feedRunID, accountId, make));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public AISIncentive getAisIncentiveByFeedRunIdAccountIdAndMake(String feedRunId, String accountId, String make) {
        Statement stmt = mysqlCon.getStatement();
        List<AISIncentive> aisIncentives = new ArrayList<AISIncentive>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select * from aisIncentive where feedRunId = '%s' and" +
                    " accountId = '%s' and make = '%s';", feedRunId, accountId, make));


            while (rs.next()) {
                return new AISIncentive(rs.getLong(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getTimestamp(6));
            }


        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println(String.format("No aisIncentives found with feedRunId='%s', accountId ='%s' and make = '%s'. ", feedRunId, accountId, make));
        return null;
    }

    public int getNumberOfVehicleGroupsByAISInentiveIdAndVin(Long aisIncentiveID, String vin) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from vehicleGroup where " +
                    "aisIncentiveId = '%s' and vin = '%s';", aisIncentiveID, vin));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public List<VehicleGroup> getVehicleGroupsByAISIncentiveId(Long aisIncentiveID) {
        Statement stmt = mysqlCon.getStatement();
        List<VehicleGroup> vehicleGroups = new ArrayList<VehicleGroup>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select * from vehicleGroup where " +
                    "aisIncentiveId = '%s';", aisIncentiveID));

            while (rs.next()) {
                vehicleGroups.add(new VehicleGroup(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getLong(6), rs.getInt(7), rs.getInt(8),
                        rs.getLong(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getTimestamp(13)));
            }

            if (vehicleGroups.size() > 0)
                return vehicleGroups;
        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println(String.format("No vehicle groups with 'aisIncentiveId'= %d", aisIncentiveID));
        return null;
    }

    public List<VehicleGroup> getVehicleGroupsByAISIncentiveIdAndVin(String aisIncentiveID, String vin) {
        Statement stmt = mysqlCon.getStatement();
        List<VehicleGroup> vehicleGroups = new ArrayList<VehicleGroup>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select * from vehicleGroup where " +
                    "aisIncentiveId = '%s' AND vin = '%s';", aisIncentiveID, vin));

            while (rs.next()) {
                vehicleGroups.add(new VehicleGroup(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getLong(6), rs.getInt(7), rs.getInt(8),
                        rs.getLong(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getTimestamp(13)));
            }

            if (vehicleGroups.size() > 0)
                return vehicleGroups;
        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println(String.format("No vehicle groups with 'aisIncentiveId'= %d and 'vin'= %s", aisIncentiveID, vin));
        return null;
    }

    public int getNumberOfVehicleCodesByVehicleGroupId(Long vehicleGroupId) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from vehicleCode where " +
                    "vehicleGroupId = '%s';", vehicleGroupId));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public int getNumberOfVehicleGroupMatchDetailsByVehicleGroupId(Long vehicleGroupId) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from vehicleGroupMatchDetail where " +
                    "vehicleGroupId = '%s';", vehicleGroupId));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public int getNumberOfCashIncentivesByVehicleGroupId(Long vehicleGroupId) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from cashIncentive where " +
                    "vehicleGroupId = '%s' and programId is null ;", vehicleGroupId));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public List<VehicleCode> getVehicleCodesByVehicleGroupId(long vehicleGroupId) {
        Statement stmt = mysqlCon.getStatement();
        List<VehicleCode> vehicleCodes = new ArrayList<VehicleCode>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select * from vehicleCode where " +
                    "vehicleGroupId = '%s';", vehicleGroupId));

            while (rs.next()) {
                vehicleCodes.add(new VehicleCode(rs.getLong(1), rs.getLong(2), rs.getString(3),
                        rs.getString(4), rs.getLong(5)));
            }

            if (vehicleCodes.size() > 0)
                return vehicleCodes;
        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println(String.format("No vehicle codes with 'vehicleGroupId'= %d", vehicleGroupId));
        return null;
    }

    public List<VehicleGroupMatchDetail> getVehicleGroupMatchDetailsByVehicleGroupId(long vehicleGroupId) {
        Statement stmt = mysqlCon.getStatement();
        List<VehicleGroupMatchDetail> vgmds = new ArrayList<VehicleGroupMatchDetail>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select * from vehicleGroupMatchDetail where " +
                    "vehicleGroupId = '%s';", vehicleGroupId));

            while (rs.next()) {
                vgmds.add(new VehicleGroupMatchDetail(rs.getLong(1), rs.getLong(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }

            if (vgmds.size() > 0)
                return vgmds;
        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println(String.format("No vehicleGroupMatchDetail with 'vehicleGroupId'= %d", vehicleGroupId));
        return null;
    }

    public List<CashIncentive> getCashIncentivesByVehicleGroupId(long vehicleGroupId) {
        Statement stmt = mysqlCon.getStatement();
        List<CashIncentive> vgci = new ArrayList<CashIncentive>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("select * from cashIncentive where " +
                    "vehicleGroupId = '%s' and programId is null;", vehicleGroupId));

            while (rs.next()) {
                vgci.add(new CashIncentive(rs.getLong(1), rs.getInt(2), rs.getLong(3),
                        rs.getString(4), rs.getLong(5), rs.getString(6), rs.getLong(7)));
            }

            if (vgci.size() > 0)
                return vgci;
        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println(String.format("No cashIncentives with 'vehicleGroupId'= %d", vehicleGroupId));
        return null;
    }

    public int getTheNumberOfcashIncentivesThatHaveProgram(String feedRunId) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from cashIncentive ci join program p on p.id = ci.programId " +
                    "join vehicleGroup vg on vg.id = ci.vehicleGroupId join aisIncentive ai on ai.id = vg.aisIncentiveId " +
                    "where ci.programId is not null and ai.feedRunId = '%s';", feedRunId));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }


    }

    public int getTheNumberOfcashIncentivesThatHaveProgramAndHaveProgramDescription(String feedRunId) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from cashIncentive ci join program p on p.id = ci.programId " +
                    "join programLocal pl on p.`programID` = pl.`programID` join vehicleGroup vg on vg.id = ci.`vehicleGroupId` " +
                    "join aisIncentive ai on ai.id = vg.aisIncentiveId where ci.programId is not null and ai.feedRunId = '%s';", feedRunId));

            while (rs.next()) {
                count++;
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }


    }


}
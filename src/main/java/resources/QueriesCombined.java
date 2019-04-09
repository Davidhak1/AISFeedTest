package resources;


import java.sql.ResultSet;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.*;

public class QueriesCombined {

    private MysqlConIMDB mysqlCon = new MysqlConIMDB();

    public String getTheLatestAISFeedRunIDWithStatus(String status) {
        Statement stmt = mysqlCon.getStatement();
        String feedRunId = null;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select id from incentiveFeedRun where thirdPartyId = 'AIS-CANADA' " +
                    "and status = '%s' order by endTime DESC limit 1;",status));

            while (rs.next()) {
                feedRunId = rs.getString(1);
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            if (feedRunId == null) {
                System.out.printf("%n-No feeds found with status = %s, feedRunId = %s-%n", status, feedRunId);
            }
            return feedRunId;
        }

    }

    public List<String> getTheTestData(){
        Statement stmt = mysqlCon.getStatement();
        List<String> thirdPartyIds = new ArrayList<String>();
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT i.thirdPartyIncentiveId from incentiveAccountMap iam JOIN incentive i ON iam.incentiveId = i.id WHERE iam.accountId = 'vancouvernissantc' AND i.expirationDate > NOW() AND i.accountId = 'aiscaincentivesaccount';"));

            while (rs.next()) {
                thirdPartyIds.add(rs.getString(1));
            }

            if (thirdPartyIds.size() > 0)
                return thirdPartyIds;
        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
        }
        System.out.println("No thirdPartyIds found");
        return null;
    }

    public String getIncentiveIdByThirdPartyId(String tpi) {
        Statement stmt = mysqlCon.getStatement();
        String id = null;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select id from incentive where thirdPartyIncentiveId = '%s';",tpi));

            while (rs.next()) {
                id = rs.getString(1);
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            if (id == null) {
                System.out.printf("%nNo incentive found with thirdPartyIncentiveId = %s%n", tpi);
            }
            return id;
        }

    }

    public int getIncentiveCompabilitCountByIncentiveId(String id) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from incentiveCompatibility where incentiveId1 = '%s';",id));

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

    public int getTheNumberOfRowsInAsiRegionDetails() {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("SELECT COUNT(*) FROM aisRegionDetails;"));

            rs.next();
            count = rs.getInt(1);

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public int getDistinctRegionIdsFromAisRegionDetails() {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("SELECT DISTINCT(regionId) from aisRegionDetails;"));

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

    public int getNumberOfPostalCodesWithRegionId(int regionId) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("SELECT COUNT(*) FROM aisRegionDetails where regionId = '%d';",regionId));

            rs.next();
            count = rs.getInt(1);

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return count;
        }

    }

    public Map<String,Integer> getDistinctRegionIdAndCountOfPostalCodes() {
        Statement stmt = mysqlCon.getStatement();
        Map<String,Integer> regionIdPostalCode = new HashMap<>();

        try {

            ResultSet rs = stmt.executeQuery(String.format("SELECT regionId,count(*) from aisRegionDetails group by regionId;"));
            while (rs.next()) {
                regionIdPostalCode.put(rs.getString(1),rs.getInt(2));
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            return regionIdPostalCode;
        }

    }

    public List<String> getAccount_makePairsWithSourceAccountId(String sourceAccountId){

        Statement stmt = mysqlCon.getStatement();
        Statement stmt2 = mysqlCon.getStatement();

        String accountId;
        String makeCode;
        String make;
        List<String> account_make = new LinkedList<>();
        try {

            ResultSet rs = stmt.executeQuery(String.format("SELECT accountId, makeCode from feedConfig where sourceAccountId = '%s'",sourceAccountId));
            while (rs.next()) {
               accountId =  rs.getString(1);
               makeCode = rs.getString(2);

               ResultSet rs2 = stmt2.executeQuery(String.format("SELECT make from dealerFeedOffers where makeCode = '%s'",makeCode));
               rs2.next();
               make = rs2.getString(1);

               account_make.add(accountId+"_"+make);
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally{
            mysqlCon.endCon();
            return account_make;
        }

    }

    public List<String> getAccoutIdsWithMappedIncentiveId(String incentiveId){
        List<String> accountIds = new ArrayList<>();

        Statement stmt = mysqlCon.getStatement();
        String accountId;
        String makeCode;
        String make;
        List<String> account_make = new LinkedList<>();
        try {

            ResultSet rs = stmt.executeQuery(String.format("SELECT accountId from incentiveAccountMap where incentiveId = '%s'",incentiveId));
            while (rs.next()) {
                accountId =  rs.getString(1);
                if(!accountId.equalsIgnoreCase("aiscaincentivesaccount"))
                accountIds.add(accountId);
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally{
            mysqlCon.endCon();
            if(accountIds.isEmpty()) {
                System.out.println("\nNo account with incentiveId = " + incentiveId);
                return null;
            }
                return accountIds;
        }
    }
}

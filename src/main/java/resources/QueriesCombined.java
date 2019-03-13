package resources;


import java.sql.ResultSet;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueriesCombined {

    private MysqlConIMDB mysqlCon = new MysqlConIMDB();

    public String getTheLatestSuccessfulAISFeedRunIDThatHasRecordsInAISIncentiveTable() {
        Statement stmt = mysqlCon.getStatement();
        String feedRunId = null;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select id from incentiveFeedRun where thirdPartyId = 'AIS-CANADA' " +
                    "and status = 'success' order by endTime DESC limit 1;"));

            while (rs.next()) {
                feedRunId = rs.getString(1);
            }

        } catch (Exception e) {
            System.out.println("------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        } finally {
            mysqlCon.endCon();
            if (feedRunId == null) {
                System.out.printf("%n-No feeds found with feedRunId = %s-%n", feedRunId);
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

    public String getIncentiveIdByThirdPartyId( String tpi) {
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
                System.out.printf("%n-No incentive found with feedRunId = %s-%n", id);
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

}

package resources;


import java.sql.ResultSet;
import java.sql.SQLOutput;
import java.sql.Statement;

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
}

package resources;


import java.sql.ResultSet;
import java.sql.Statement;

public class QueriesNexusVehicle {

    private MysqlConNexus mysqlCon = new MysqlConNexus();

    public int getNumberOfVehiclesWithAccountIdOemAndNewAndNotRemoved(String parentId, String make) {
        Statement stmt = mysqlCon.getStatement();
        int count = 0;

        try {

            ResultSet rs = stmt.executeQuery(String.format("select * from vehicle where parentid = '%s' and make = '%s' and type=2 and isremoved = false;", parentId, make));

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

    public String getPostalCodeWithAccountId(String accountId) {
        Statement stmt = mysqlCon.getStatement();
        String postalCode = null;
        try {

            ResultSet rs = stmt.executeQuery(String.format("select postal_code from `account_stats` where account_id = '%s'" +
                    " order by year desc;", accountId));
            rs.next();
            postalCode = rs.getString(1);
            postalCode = postalCode.replaceAll("\\s+","");


        }catch (Exception e){
            System.out.println("--------------------------EXCEPTION IN THE QUERIES CLASS-------------------");
            e.printStackTrace();
        }

        finally{
            mysqlCon.endCon();
            return postalCode;
        }

    }


}
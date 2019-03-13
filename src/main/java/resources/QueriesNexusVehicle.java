package resources;


import java.sql.ResultSet;
import java.sql.Statement;

public class QueriesNexusVehicle {

    private MysqlConNexus mysqlCon= new MysqlConNexus();

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


}
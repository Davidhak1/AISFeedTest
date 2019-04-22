package resources;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static resources.base.testEnvironment;

class MysqlConNexus {

    public  Statement stmt;
    public  Properties prop;
    private  String mysqlUrl;
    private  String user;
    private  String password;
    private  Connection con;




    public Statement getStatement(){

        prop = new Properties();

        try{
            FileInputStream fis = new FileInputStream("src/main/java/resources/props/data.properties");
            prop.load(fis);


            if(testEnvironment==null) {
                mysqlUrl = "jdbc:mysql://"+prop.getProperty("nexusURL")+"/" + prop.getProperty("NexusDbName");
                user = prop.getProperty("nexusUser");
                password = prop.getProperty("nexusPass");
            } else {
                mysqlUrl = "jdbc:mysql://" + testEnvironment.nexusURL() + "/" + testEnvironment.nexusDbName();
                user = testEnvironment.nexusUser();
                password = testEnvironment.nexusPass();

            }
//            Class.forName("com.mysql.cj.jdbc.Driver");

            con=DriverManager.getConnection(
                    mysqlUrl,user,password);
            stmt=con.createStatement();

            return stmt;


        }catch(Exception e){
            System.out.println("Something is wrong with initializing MySQL Statement, check the VPN connection");
            System.out.println(e);}

        return null;
    }


    public void endCon() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


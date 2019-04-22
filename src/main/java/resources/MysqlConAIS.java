package resources;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class MysqlConAIS extends base{

    public static Statement stmt;
    public static Properties prop;
    private static String mysqlUrl;
    private static String user;
    private static String password;
    private static Connection con;




    public Statement getStatement(){

        prop = new Properties();

        try{
            FileInputStream fis = new FileInputStream("src/main/java/resources/props/data.properties");
            prop.load(fis);
        if(testEnvironment==null) {
            mysqlUrl = "jdbc:mysql://" + prop.getProperty("aisUrl") + "/" + prop.getProperty("aisDBName");
            user = prop.getProperty("aisUser");
            password = prop.getProperty("aisPass");
        } else
        {
            mysqlUrl = "jdbc:mysql://" + testEnvironment.aisUrl() + "/" + testEnvironment.aisDBName();
            user = testEnvironment.aisUser();
            password = testEnvironment.aisPass();

        }
//            Class.forName("com.mysql.cj.jdbc.Driver");

            con=DriverManager.getConnection(mysqlUrl,user,password);
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


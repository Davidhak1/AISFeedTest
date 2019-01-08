package resources;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

class MysqlCon{

    public static Statement stmt;
    public static Properties prop;
    private static String mysqlUrl;
    private static String user;
    private static String password;
    private static Connection con;




    public Statement getStatement(){

        prop = new Properties();

        try{
            FileInputStream fis = new FileInputStream("src/main/java/resources/data.properties");
            prop.load(fis);

            mysqlUrl = "jdbc:mysql://"+prop.getProperty("mysqlUrl")+"/" + prop.getProperty("dbName");
            user = prop.getProperty("mysqlUser");
            password = prop.getProperty("mysqlPass");

            Class.forName("com.mysql.cj.jdbc.Driver");

            con=DriverManager.getConnection(
                    mysqlUrl,user,password);
            stmt=con.createStatement();

            return stmt;


        }catch(Exception e){ System.out.println(e);}

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


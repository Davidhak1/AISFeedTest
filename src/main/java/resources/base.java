package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;



public class base {

    public Properties prop;
    public Queries q;


    public void initBase() {
    System.out.println("INITIALIZING Queries\n");
        q = new Queries();
        initProp();
    }

    public void initProp() {
        prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream("src/main/java/resources/data.properties");
            prop.load(fis);
        }catch (IOException e){
            e.printStackTrace();
        }

        q = new Queries();

    }




}

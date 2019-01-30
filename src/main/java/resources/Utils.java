package resources;


import org.apache.commons.collections4.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

public class Utils {

    public static Set<Integer> findDuplicates(List<Integer> listContainingDuplicates) {
        final Set<Integer> setToReturn = new HashSet();
        final Set<Integer> set1 = new HashSet();

        for (Integer yourInt : listContainingDuplicates)
        {
            if (!set1.add(yourInt))
            {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn;
    }

    public static String clobToString(Clob data) {
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = null;
            try {
                reader = data.getCharacterStream();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(reader);

            String line;
            while(null != (line = br.readLine())) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            // handle this exception
        }
        return sb.toString();
    }

    public static Properties initProp(String path){
        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream(path);
            prop.load(fis);
        }catch (IOException e){
            e.printStackTrace();
        }
        return prop;
    }

//    public static void main(String[] args) {
//
//            List<Integer> a = new ArrayList<>();
//            List<Integer> b = new ArrayList<>();
//
//            a.add(1);
//            a.add(2);
//            a.add(3);
//            a.add(4);
//
//            b.add(5);
//            b.add(6);
//            b.add(3);
//            b.add(4);
//
//            System.out.println(a);
//            System.out.println(b);
//
//        Collection<Integer> aMinusB = CollectionUtils.subtract(a,b);
//            System.out.println(aMinusB);
//
//        System.out.println(aMinusB.isEmpty());
//    }
}

package resources;


import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class Utils {

    public static Set<Integer> findDuplicates(List<Integer> listContainingDuplicates)
    {
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

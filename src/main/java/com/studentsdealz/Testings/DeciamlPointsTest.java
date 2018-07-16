package com.studentsdealz.Testings;

import java.text.DecimalFormat;

/**
 * Created by saran on 8/10/17.
 */
public class DeciamlPointsTest {
    public static void main(String... args)
    {
        DecimalFormat format = new DecimalFormat("0.####");
        System.out.println(format.format(5.0000d));
    }
}

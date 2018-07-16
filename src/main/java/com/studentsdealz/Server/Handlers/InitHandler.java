package com.studentsdealz.Server.Handlers;

import com.studentsdealz.AI.Searcher.Simple_Query;

/**
 * Created by saran on 8/12/17.
 */
public class InitHandler {
    public static String response()
    {

        try {
            Simple_Query.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "sucess";
    }
}

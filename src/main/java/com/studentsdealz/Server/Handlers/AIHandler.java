package com.studentsdealz.Server.Handlers;

import com.google.gson.Gson;
import com.studentsdealz.AI.Searcher.Simple_Query;
import com.studentsdealz.Searcher.SearchMain;

/**
 * Created by saran on 8/12/17.
 */
public class AIHandler {
    public String response(String query) throws Exception
    {
        Gson gson=new Gson();


        return gson.toJson(Simple_Query.search(query+" end"));
    }
}

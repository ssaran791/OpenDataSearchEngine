package com.studentsdealz.Server.Handlers;

import com.google.gson.Gson;
import com.studentsdealz.Searcher.SearchMain;

/**
 * Created by saran on 8/11/17.
 */
public class SearchHandler {
    public String response(String query) throws Exception
    {
        Gson gson=new Gson();

        SearchMain searchMain=new SearchMain();
        return gson.toJson(searchMain.getResults(query));
    }
}

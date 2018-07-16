package com.studentsdealz.Server.Handlers;

import com.google.gson.Gson;
import com.studentsdealz.AI.Searcher.Simple_Query;
import com.studentsdealz.AI.Suggestions.SuggestionRetrive;
import com.studentsdealz.Searcher.SearchMain;

/**
 * Created by saran on 8/17/17.
 */
public class Search_SuggestHandler {
    public static String response(String query)
    {
        Gson gson=new Gson();
        try {
            return gson.toJson(SuggestionRetrive.search(query));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
}

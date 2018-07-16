package com.studentsdealz.Server;
import com.studentsdealz.Server.Handlers.AIHandler;
import com.studentsdealz.Server.Handlers.InitHandler;
import com.studentsdealz.Server.Handlers.SearchHandler;
import com.studentsdealz.Server.Handlers.Search_SuggestHandler;

import static spark.Spark.*;
/**
 * Created by saran on 8/10/17.
 */
public class Main {
    public static void main(String... args)
    {
        InitHandler.response();
        System.out.println("==============ready now work================");
        get("/", (request, response) ->{
            return "";
        });
        get("/init", (request, response) ->{
            return "Already done";
        });
        get("/search",(request, response) -> {
            return new SearchHandler().response(request.queryParams("q"));
        });
        get("/search_ai",(request, response) -> {
            return new AIHandler().response(request.queryParams("q"));
        });
        get("/search_suggest",(request, response) -> {
            return new Search_SuggestHandler().response(request.queryParams("q"));
        });
    }
}

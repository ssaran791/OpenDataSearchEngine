package com.studentsdealz.AI.Suggestions;

/**
 * Created by saran on 8/12/17.
 */

class AI_Query_Suggest_Item implements java.io.Serializable
{
    String name;

    int num_of_hits;

    public AI_Query_Suggest_Item(String name,
                                 int num_of_hits) {
        this.name = name;
        this.num_of_hits = num_of_hits;
    }
}
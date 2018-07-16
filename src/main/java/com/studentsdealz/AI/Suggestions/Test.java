package com.studentsdealz.AI.Suggestions;

import java.util.ArrayList;

/**
 * Created by saran on 8/17/17.
 */
public class Test {
    public static void main(String... args) throws Exception{
        ArrayList<String> possible_suggestions_to_index=(Suggestion_Indexer_Utils.suggest_possible_index_nw("689581","Get the details of the advocate whose Reg no. is *","COL, Year"));

        System.out.println(possible_suggestions_to_index);
    }
}

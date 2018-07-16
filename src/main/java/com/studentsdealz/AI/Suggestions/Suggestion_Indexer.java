package com.studentsdealz.AI.Suggestions;

import com.studentsdealz.AI.Suggestions.DAL.DB_MYSQL.Suggestion_Init_DAL;
import com.studentsdealz.AI.Suggestions.MODEL.DB_MYSQL.Suggestion_Init_MODEL;
import com.studentsdealz.AppConstants;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by saran on 8/17/17.
 */
public class Suggestion_Indexer {
    public static void main(String... args) throws Exception
    {
        Directory dir = FSDirectory.open(Paths.get(AppConstants.IndexerConstant_AI.STORAGE_OF_SUGGEST_INDEX));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(dir, analyzer);
        List<Suggestion_Init_MODEL> all_suggestion_to_index= Suggestion_Init_DAL.getAllSuggestionQuery();
        ArrayList<AI_Query_Suggest_Item> ai_query_suggest_items = new ArrayList<AI_Query_Suggest_Item>();
        for(Suggestion_Init_MODEL single_suggestion:all_suggestion_to_index)
        {
         //   System.out.println(single_suggestion.getId());
            System.out.println("keyword"+single_suggestion.getKeyword());
            ArrayList<String> possible_suggestions_to_index=(Suggestion_Indexer_Utils.suggest_possible_index_nw(single_suggestion.getDownload_id()+"",single_suggestion.getKeyword(),single_suggestion.getCols()));

            for(String qury:possible_suggestions_to_index)
            {
                int rank=100;

                ai_query_suggest_items.add(new AI_Query_Suggest_Item(qury,rank));
                System.out.println("query="+qury);
            }
            //adding all the suggestion to the index
           }
        suggester.build(new Ai_Query_Suggest_Iterator(ai_query_suggest_items.iterator()));

        //lookup(suggester,"what ");
        System.out.println("Finished suggestion index");
    }

}

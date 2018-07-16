package com.studentsdealz.AI.Suggestions;

import com.studentsdealz.AI.Indexer.Sqlite_Helper;
import com.studentsdealz.AppConstants;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by saran on 8/12/17.
 */
public class PossibleSuggest {
    public static void main(String... args) throws Exception
    {
        //this is only for testing purpose....
     //   Directory dir = FSDirectory.open(Paths.get(AppConstants.IndexerConstant_AI.STORAGE_OF_SUGGEST_INDEX));
        Directory dir=new RAMDirectory();
        StandardAnalyzer analyzer = new StandardAnalyzer();
        AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(dir, analyzer);




        ArrayList<String> possible_suggestions_to_index=(suggest_possible_index("2957461", "What is the length of national highways in *", "States/UTs"));
        ArrayList<AI_Query_Suggest_Item> ai_query_suggest_items = new ArrayList<AI_Query_Suggest_Item>();
        for(String qury:possible_suggestions_to_index)
        {
            ai_query_suggest_items.add(new AI_Query_Suggest_Item(qury,100));
        }



        suggester.build(new Ai_Query_Suggest_Iterator(ai_query_suggest_items.iterator()));
        //lookup(suggester, "");




    }

    private static ArrayList<String> suggest_possible_index(String download_id,String query,String col_name) throws Exception
    {
        ArrayList<String> all_possible_query=new ArrayList<>();
        Sqlite_Helper sqlite_helper=new Sqlite_Helper();
        sqlite_helper.setSqlite_path(AppConstants.IndexerConstant_AI.STORAGE_OF_CONVERTED_FILES+download_id+".db");

        String col_id=sqlite_helper.col_name(col_name);

        ArrayList<String> all_values=sqlite_helper.getGroupByValues(col_id);

        for(String single_col_value:all_values)
        {
            all_possible_query.add(query.replace("*",single_col_value).toLowerCase().trim());
        }


        //System.out.println(all_values);
        return all_possible_query;

    }
    private static void addToIndex()
    {

    }
    private static void lookup(AnalyzingInfixSuggester suggester, String name) {
        try {
            List<Lookup.LookupResult> results;
            HashSet<BytesRef> contexts = new HashSet<BytesRef>();
            //contexts.add(new BytesRef(region.getBytes("UTF8")));
            // Do the actual lookup.  We ask for the top 2 results.
            results = suggester.lookup(name, contexts, 20, true, false);
            System.out.println("-- \"" + name );
            for (Lookup.LookupResult result : results) {
                System.out.println(result.key);
                AI_Query_Suggest_Item p = getProduct(result);
                if (p != null) {
                    // System.out.println("  image: " + p.image);
                    System.out.println("  # sold: " + p.num_of_hits);
                }
            }
        } catch (IOException e) {
            System.err.println("Error");
        }
    }
    private static AI_Query_Suggest_Item getProduct(Lookup.LookupResult result)
    {
        try {
            BytesRef payload = result.payload;
            if (payload != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(payload.bytes);
                ObjectInputStream in = new ObjectInputStream(bis);
                AI_Query_Suggest_Item p = (AI_Query_Suggest_Item) in.readObject();
                return p;
            } else {
                return null;
            }
        } catch (IOException|ClassNotFoundException e) {
            throw new Error("Could not decode payload :(");
        }
    }

}

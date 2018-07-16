package com.studentsdealz.AI.Suggestions;

/**
 * Created by saran on 8/12/17.
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;

public class SuggestProducts
{
    // Get suggestions given a prefix and a region.
    private static void lookup(AnalyzingInfixSuggester suggester, String name,
                               String region) {
        try {
            List<Lookup.LookupResult> results;
            HashSet<BytesRef> contexts = new HashSet<BytesRef>();
            //contexts.add(new BytesRef(region.getBytes("UTF8")));
            // Do the actual lookup.  We ask for the top 2 results.
            results = suggester.lookup(name, contexts, 2, true, false);
            System.out.println("-- \"" + name + "\" (" + region + "):");
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

    // Deserialize a AI_Query_Suggest_Item from a LookupResult payload.
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

    public static void main(String[] args) {
        try {
            RAMDirectory index_dir = new RAMDirectory();
           // Directory dir = FSDirectory.open(Paths.get("/home/saran/rajasthan_hacking_data/SuggestIndex/"));
            StandardAnalyzer analyzer = new StandardAnalyzer();
            AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(index_dir, analyzer);

            // Create our list of AIQuerySuggestItems.
            ArrayList<AI_Query_Suggest_Item> AIQuerySuggestItems = new ArrayList<AI_Query_Suggest_Item>();
//            AIQuerySuggestItems.add(
//                    new AI_Query_Suggest_Item(
//                            "Electric Guitar with new word",
//                            "http://images.example/electric-guitar.jpg",
//                            new String[]{"US", "CA"},
//                            100));
//            AIQuerySuggestItems.add(
//                    new AI_Query_Suggest_Item(
//                            "Electric Train",
//                            "http://images.example/train.jpg",
//                            new String[]{"US", "CA"},
//                            100));
//            AIQuerySuggestItems.add(
//                    new AI_Query_Suggest_Item(
//                            "Acoustic Guitar",
//                            "http://images.example/acoustic-guitar.jpg",
//                            new String[]{"US", "ZA"},
//                            80));
//            AIQuerySuggestItems.add(
//                    new AI_Query_Suggest_Item(
//                            "Guarana Soda",
//                            "http://images.example/soda.jpg",
//                            new String[]{"ZA", "IE"},
//                            130));

            // Index the AIQuerySuggestItems with the suggester.
           suggester.build(new Ai_Query_Suggest_Iterator(AIQuerySuggestItems.iterator()));

            // Do some example lookups.
            lookup(suggester, "g", "US");

        } catch (IOException e) {
            System.err.println("Error!");
        }
    }
}
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
public class SuggestionRetrive {

    public static List<String> search(String query) throws Exception
    {
        Directory dir = FSDirectory.open(Paths.get(AppConstants.IndexerConstant_AI.STORAGE_OF_SUGGEST_INDEX));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(dir, analyzer);
        return (lookup(suggester, query));

    }
    public static void main(String... args) throws Exception
    {
        search("what");
    }
    private static List<String> lookup(AnalyzingInfixSuggester suggester, String name) {
        List<String> suggestion_retrun=new ArrayList<>();
        try {
            List<Lookup.LookupResult> results;
            HashSet<BytesRef> contexts = new HashSet<BytesRef>();

            results = suggester.lookup(name, contexts, AppConstants.IndexerConstant_AI.num_of_suggestion_to_return, true, false);

            for (Lookup.LookupResult result : results) {
                System.out.println(result.key);
                suggestion_retrun.add(result.key+"");


            }
        } catch (IOException e) {
            System.err.println("Error");
        }
        return suggestion_retrun;
    }

}

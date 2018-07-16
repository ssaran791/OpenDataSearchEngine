package com.studentsdealz.Searcher;

import com.studentsdealz.AppConstants;
import com.studentsdealz.Indexer.Indexer;
import com.studentsdealz.Searcher.MODEL.SearchResponseJSON;
import com.studentsdealz.Searcher.MODEL.SingleSearchResponseJSON;
import org.apache.cassandra.index.Index;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saran on 8/11/17.
 */
public class SearchMain {
    String index = AppConstants.IndexerConstants.INDEX_PATH;
    IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
    IndexSearcher searcher = new IndexSearcher(reader);
    Analyzer analyzer = new StandardAnalyzer();
    int hitsPerPage=100; //total 500 display
    public SearchMain() throws Exception
    {

    }
    public SearchResponseJSON getResults(String query_string) throws Exception {

        SearchResponseJSON responseJSON = new SearchResponseJSON();
        List<SingleSearchResponseJSON> web_results=new ArrayList<>();



        QueryParser parser = new QueryParser(Indexer.DOCUMENT_INDEX_FIELD_NAMES.TITLE, analyzer);
//        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
//                new String[] {Indexer.DOCUMENT_INDEX_FIELD_NAMES.TITLE,Indexer.DOCUMENT_INDEX_FIELD_NAMES.LINKING_KEYWORDS,Indexer.DOCUMENT_INDEX_FIELD_NAMES.URL},
//                analyzer);
        Query query = parser.parse(query_string);


        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 5 * hitsPerPage);
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = results.totalHits;
        if(numTotalHits!=0)
        responseJSON.setTotal_results("About "+numTotalHits + " results");
        else
        responseJSON.setTotal_results("Sorry no results");

        int start = 0;
        int end = Math.min(numTotalHits, hitsPerPage);




            end = Math.min(hits.length, start + hitsPerPage);

            for (int i = start; i < end; i++) {


                Document doc = searcher.doc(hits[i].doc);
                String path = doc.get(Indexer.DOCUMENT_INDEX_FIELD_NAMES.URL);
                if (path != null) {

                    String title = doc.get(Indexer.DOCUMENT_INDEX_FIELD_NAMES.TITLE);
                    if (title != null) {
//                        System.out.println("   Title: " + doc.get("TITLE"));
//                        System.out.println("   URL: " + doc.get("URL"));
//                        System.out.println("   URL ORIGINAL: " + doc.get("URL_ORIGINAL"));
//                        System.out.println("   DESCRIPTION: " );
                        SingleSearchResponseJSON sinle_Result=new SingleSearchResponseJSON();
                        sinle_Result.setDescription( doc.get(Indexer.DOCUMENT_INDEX_FIELD_NAMES.DESCRIPTION));
                        sinle_Result.setTitle( doc.get(Indexer.DOCUMENT_INDEX_FIELD_NAMES.TITLE));
                        sinle_Result.setUrl_to_link(doc.get(Indexer.DOCUMENT_INDEX_FIELD_NAMES.URL_ORGINAL));
                        sinle_Result.setUrl_to_display(doc.get(Indexer.DOCUMENT_INDEX_FIELD_NAMES.URL_ORGINAL));
                        web_results.add(sinle_Result);
                    }
                } else {
                    System.out.println((i + 1) + ". " + "No path for this document");
                }

            }

        responseJSON.setSearchResults(web_results);

        return responseJSON;
    }
}

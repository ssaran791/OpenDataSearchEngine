package com.studentsdealz.Searcher;



        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.nio.charset.StandardCharsets;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.util.Date;

        import com.studentsdealz.AppConstants;
        import org.apache.lucene.analysis.Analyzer;
        import org.apache.lucene.analysis.standard.StandardAnalyzer;
        import org.apache.lucene.document.Document;
        import org.apache.lucene.index.DirectoryReader;
        import org.apache.lucene.index.IndexReader;
        import org.apache.lucene.queryparser.classic.QueryParser;
        import org.apache.lucene.search.IndexSearcher;
        import org.apache.lucene.search.Query;
        import org.apache.lucene.search.ScoreDoc;
        import org.apache.lucene.search.TopDocs;
        import org.apache.lucene.store.FSDirectory;

/** Simple command-line based search demo. */
public class WebSearcher {

    private WebSearcher() {}

    /** Simple command-line based search demo. */
    public static void main(String[] args) throws Exception {


        String index = AppConstants.IndexerConstants.INDEX_PATH;
        String field = "TITLE";
        String queries = null;
        int repeat = 0;
        boolean raw = false;
        String queryString = null;
        int hitsPerPage = 10;



        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();

        BufferedReader in = null;
        if (queries != null) {
            in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
        } else {
            in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        }
        QueryParser parser = new QueryParser(field, analyzer);

            if (queries == null && queryString == null) {                        // prompt the user
                System.out.println("Enter query: ");
            }

            String line = queryString != null ? queryString : in.readLine();



            line = line.trim();


            Query query = parser.parse(line);
            System.out.println("Searching for: " + query.toString(field));


            doPagingSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);



        reader.close();
    }

    /**
     * This demonstrates a typical paging search scenario, where the search engine presents
     * pages of size n to the user. The user can then go to the next page if interested in
     * the next hits.
     *
     * When the query is executed for the first time, then only enough results are collected
     * to fill 5 result pages. If the user wants to page beyond this limit, then the query
     * is executed another time and all hits are collected.
     *
     */
    public static void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query,
                                      int hitsPerPage, boolean raw, boolean interactive) throws IOException {

        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 5 * hitsPerPage);
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = results.totalHits;
        System.out.println(numTotalHits + " total matching documents");

        int start = 0;
        int end = Math.min(numTotalHits, hitsPerPage);

        while (true) {
            if (end > hits.length) {
                System.out.println("Only results 1 - " + hits.length +" of " + numTotalHits + " total matching documents collected.");
                System.out.println("Collect more (y/n) ?");
                String line = in.readLine();
                if (line.length() == 0 || line.charAt(0) == 'n') {
                    break;
                }

                hits = searcher.search(query, numTotalHits).scoreDocs;
            }

            end = Math.min(hits.length, start + hitsPerPage);

            for (int i = start; i < end; i++) {
                if (raw) {                              // output raw format
                    System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
                    continue;
                }

                Document doc = searcher.doc(hits[i].doc);
                String path = doc.get("URL");
                if (path != null) {
                    System.out.println((i+1) + ". " + path);
                    String title = doc.get("TITLE");
                    if (title != null) {
                        System.out.println("   Title: " + doc.get("TITLE"));
                        System.out.println("   URL: " + doc.get("URL"));
                        System.out.println("   URL ORIGINAL: " + doc.get("URL_ORIGINAL"));
                        System.out.println("   DESCRIPTION: " + doc.get("DESCRIPTION"));
                    }
                } else {
                    System.out.println((i+1) + ". " + "No path for this document");
                }

            }


        }
    }
}
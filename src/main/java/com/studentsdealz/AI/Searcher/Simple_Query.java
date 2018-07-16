package com.studentsdealz.AI.Searcher;

import com.studentsdealz.AI.Indexer.Sqlite_Helper;
import com.studentsdealz.AI.Searcher.DAL.DB_MYSQL.Ai_Query_Helper_DAL;
import com.studentsdealz.AI.Searcher.MODEL.DB_MYSQL.Ai_Query_Helper_MODEL;
import com.studentsdealz.AI.Searcher.MODEL.JSON.Ai_Response_MODEL;
import com.studentsdealz.AI.keyword.KeyWordHelper;
import com.studentsdealz.AppConstants;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by saran on 8/10/17.
 */
public class Simple_Query {
    public static KeyWordHelper keyWordHelper=new KeyWordHelper();
    private static String db_file_base= AppConstants.IndexerConstant_AI.STORAGE_OF_CONVERTED_FILES;

    static Set<String> stopWordsSet = new HashSet<String>();
    // 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
    static StandardAnalyzer analyzer = new StandardAnalyzer();

    // 1. create the index
    static Directory index = new RAMDirectory();

    static IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

    static IndexWriter indexWriter;





    public static void init() throws Exception
    {
        indexWriter = new IndexWriter(index, indexWriterConfig);


        addStopWord();
        IndexQueryFromDB();

    }
    public static void main(String... args) throws Exception
        {

            init();
            System.out.println(search("what is the length of national highway in tamil nadu during 2014 end"));
            //search_step2("what is the number of Accidents in Tamil Nadu during 2014","number accident * during 2014",varaibels);


        }
///
        private static void IndexQueryFromDB() throws Exception
        {
            List<Ai_Query_Helper_MODEL> all_query= Ai_Query_Helper_DAL.getAllQuery();
            for(Ai_Query_Helper_MODEL single_query:all_query)
            {
              String keyword_to_index=remove_stop_word(single_query.getKeyword()).trim()+" end";

                addDoc(indexWriter,single_query.getDownload_id(),keyword_to_index,single_query.getVariables(),single_query.getTitle());
            }
            indexWriter.close();
        }

        public static Ai_Response_MODEL search(String user_input) throws Exception
        {
            // when no field is explicitly specified in the query.
            user_input=remove_stop_word(user_input);

            Query q = new QueryParser("keyword", analyzer).parse(user_input);

            // 3. search
            int hitsPerPage = 10;
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            // 4. display results
            System.out.println("Found " + hits.length + " hits.");
            for(int i=0;i<hits.length;++i) {
                int docId = hits[i].doc;
                //System.out.println(hits[i].score);
                Document d = searcher.doc(docId);
                //System.out.println((i + 1) + ". " + d.get("keyword") + "\t" + d.get("variables"));

                TreeMap<Integer,String> varaibels=new TreeMap<>();

                String var_arry[]=d.get("variables").split(",");
                int ii=0;
                for (String var_arry_single:var_arry)
                {

                    varaibels.put(ii,var_arry_single);
                    ii++;
                }
               // System.out.println("Title="+ );
                try {
                    Map<String,String> res=search_step2(d.get("doc_id"), user_input, d.get("keyword"), varaibels);
                    if(res!=null){
                        Ai_Response_MODEL ai_response_model=new Ai_Response_MODEL();
                        ai_response_model.setTitle(d.get("title"));
                        ai_response_model.setAi_result(res);
                        ai_response_model.setDownload_id(d.get("doc_id"));
                        return ai_response_model;
                    }else{
                        System.out.println("Null AI VAlues");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }
        private static void addStopWord()
        {
            //adding all the stop words
            stopWordsSet.add("I");
            stopWordsSet.add("THIS");
            stopWordsSet.add("AND");
            stopWordsSet.add("A");
            stopWordsSet.add("WHAT");
            stopWordsSet.add("IS");
            stopWordsSet.add("THE");
            stopWordsSet.add("OF");
            stopWordsSet.add("TO");
            stopWordsSet.add("IN");
            stopWordsSet.add("HOW");
            stopWordsSet.add("MUCH");
            stopWordsSet.add("DOES");
            stopWordsSet.add("RUNS");

        }


        private static  Map<String ,String>  search_step2(String doc_id,String query,String gendral_format,TreeMap<Integer,String> feild_infos)
        {
            ArrayList<String> static_col=new ArrayList<>();
             Sqlite_Helper sqlite_helper=new Sqlite_Helper();
            sqlite_helper.setSqlite_path(db_file_base+doc_id+".db");
            String basic_sql="SELECT * FROM Data where 1 ";
            boolean is_specified_cols=false;

          System.out.println(query);



            gendral_format="(\\b"+gendral_format+"\\b)";
            gendral_format=gendral_format.replace("*","\\b)(.*?)(\\b");


            System.out.println(gendral_format);

            Pattern p = Pattern.compile(gendral_format);
            try {
            Matcher m = p.matcher(query);
            List<String> matches = new ArrayList<String>();
            while (m.find()) {
                for(int k=2;k<m.groupCount();k=k+2)
                matches.add(m.group(k));
            }
                System.out.println("matches=="+matches);
                int total_vars=0;
                for(Map.Entry<Integer,String> col:feild_infos.entrySet())
            {
                total_vars++;

                    if(col.getValue().equals("COL")){
                        TreeMap<Integer,String > cols_with_like=sqlite_helper.col_names(matches.get(col.getKey()));
                        is_specified_cols=true;
                        for(Map.Entry<Integer ,String > single_col_reul:cols_with_like.entrySet()){
                            static_col.add(single_col_reul.getValue());
                        }

                    }else {
                        String col_name=(sqlite_helper.col_name(col.getValue()));
                        static_col.add((col.getValue()));
                        basic_sql += " and " + col_name + " like '%" + matches.get(col.getKey()) + "%'";
                    }
                //System.out.println(col.getValue()+"===="+col_name);
            }
                System.out.println("SQL=="+basic_sql);
                TreeMap<String, String> ai_result_map = sqlite_helper.getAIResults(basic_sql);
                if(is_specified_cols)
                {
                    HashMap<String,String> single_Results=new HashMap<>();

                    for(String single_static_col:static_col)
                    single_Results.put(single_static_col,ai_result_map.get(single_static_col));

                    if(static_col.size()!=0)
                    return single_Results;
                }
                TreeMap<String, String> ai_Res_tmp = ai_result_map;

                if(ai_Res_tmp.size()!=0)
                return ai_Res_tmp;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        private static String remove_stop_word(String query)
        {
            String returning_word="";
            String[] words = query.split(" ");
            ArrayList<String> wordsList = new ArrayList<String>();


            for(String word : words)
            {
                String wordCompare = word.toUpperCase();
                if(!stopWordsSet.contains(wordCompare))
                {
                    returning_word+=keyWordHelper.stem(word)+" ";
                }
            }

        return returning_word;
        }

    private static void addDoc(IndexWriter w,String doc_id, String keyword, String variables,String title) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("keyword", keyword, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("variables", variables, Field.Store.YES));
        doc.add(new StringField("doc_id", doc_id, Field.Store.YES));
        doc.add(new StringField("title",title, Field.Store.YES));
        w.addDocument(doc);
    }

}

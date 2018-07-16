package com.studentsdealz.Indexer;

import ch.sentric.URL;
import com.studentsdealz.AppConstants;
import com.studentsdealz.Crawler.DAL.DB_CASSANDRA.Crawled_Htmls_DAL;
import com.studentsdealz.Crawler.DAL.DB_CASSANDRA.Links_DAL;
import com.studentsdealz.Crawler.MODEL.DB_CASSANDRA.Crawled_html_Model;
import com.studentsdealz.Indexer.DAL.DB_CASSANDRA.Index_Meta_Data_DAL;
import com.studentsdealz.Indexer.DAL.DB_MYSQL.Indexer_Queue_DAL;
import com.studentsdealz.Indexer.MODEL.DB_CASSANDRA.Index_Meta_Data_MODEL;
import com.studentsdealz.Indexer.MODEL.DB_MYSQL.Indexer_Queue_MODEL;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.parser.html.HtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saran on 8/6/17.
 */
public class Indexer {
    private static final String indexPath= AppConstants.IndexerConstants.INDEX_PATH; //base dir to index all the documents


    private static final int limit=2000;
    private static final String META_CONTENT="content";
    private static final String META_KEYWORD="meta";
    private static final String META_NAME="name";
    private static DateFormat META_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");




    public class DOCUMENT_INDEX_FIELD_NAMES{
        public static final String TITLE="TITLE";
        public static final String URL="URL";
        public static final String URL_ORGINAL="URL_ORIGINAL";
        public static final String DESCRIPTION="DESCRIPTION";
        public static final String LINKING_KEYWORDS="LINKING_KEYWORDS"; //this is list of links to the page with the a tags
    }


    private static IndexWriter writer;
    public static void main(String... args) throws Exception
    {
        //main program to index the crawled documents....
        List<Indexer_Queue_MODEL> to_index_datas= Indexer_Queue_DAL.getToIndexDatas(limit);


        Directory dir = FSDirectory.open(Paths.get(indexPath));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        //creating the indexer in mode of CREATE or APPNED
        //iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);


        writer = new IndexWriter(dir, iwc);


        for(Indexer_Queue_MODEL single_page_to_index:to_index_datas)
        {
            IndexDoc(single_page_to_index.getUrl());
        }




        //after indexing all the docuements closing the writter

        writer.close();

        System.out.println("Sucesfully Indexed All the documents=====");
    }
    private static void IndexDoc(String page_url) throws Exception
    {




        Crawled_html_Model crawled_document=Crawled_Htmls_DAL.getCrawledData(page_url);

        //current time only the document is being indexed so inserting the curren time;
        Date document_index_date=new Date();

        org.jsoup.nodes.Document html_parsed= Jsoup.parse(crawled_document.getData());
        //starting to parse all the data from the html
        String title_of_page=html_parsed.title();
        String description_of_page=null; //description will be added wile parsing the meta data
        String sector_of_page=null;
        Date page_published_date=null;



        //getting all the meta datas
        Elements meta_datas = html_parsed.select(META_KEYWORD);


        for(Element meta_data:meta_datas)
        {
            //contains single meta data information
            //with the help of name attribute seperating all the datas
            switch (meta_data.attr(META_NAME))
            {
                case "og:description":
                    description_of_page=meta_data.attr(META_CONTENT);
                    break;
                case "description":
                    if(description_of_page==null)
                    description_of_page=meta_data.attr(META_CONTENT);
                    break;
                case "og:title":
                    title_of_page=meta_data.attr(META_CONTENT);
                    break;
                case "dcterms.date":

                    break;

            }
        }




        //saving all the meta data about the page
        Index_Meta_Data_DAL.insertMetaData(page_url,crawled_document.getUrl_original(),title_of_page,description_of_page,sector_of_page,document_index_date);



        //mow starting to index the document to lucence

        Document doc=new Document();



        //adding all the feilds

        if(title_of_page!=null)
        doc.add(new TextField(DOCUMENT_INDEX_FIELD_NAMES.TITLE,title_of_page, Field.Store.YES));
        //url must be stores in the string feild because we dont need to tokenize those things
        doc.add(new StringField(DOCUMENT_INDEX_FIELD_NAMES.URL,page_url, Field.Store.YES));
        doc.add(new StringField(DOCUMENT_INDEX_FIELD_NAMES.URL_ORGINAL,crawled_document.getUrl_original(), Field.Store.YES));


        if(description_of_page!=null)
        doc.add(new TextField(DOCUMENT_INDEX_FIELD_NAMES.DESCRIPTION,description_of_page, Field.Store.YES));

        //indexing all the ref page a tags and text

        HashMap<String,String> linking_pages=Links_DAL.getLinksRelation(page_url);

        //prasing all the from page url and from page text and indexing the from page text

        for(Map.Entry<String,String> link_pahe:linking_pages.entrySet()){
            if(link_pahe.getValue()!=null)
            doc.add(new TextField(DOCUMENT_INDEX_FIELD_NAMES.LINKING_KEYWORDS,link_pahe.getValue(), Field.Store.NO));
        }



        writer.updateDocument(new Term(DOCUMENT_INDEX_FIELD_NAMES.URL, page_url), doc);
    }
}

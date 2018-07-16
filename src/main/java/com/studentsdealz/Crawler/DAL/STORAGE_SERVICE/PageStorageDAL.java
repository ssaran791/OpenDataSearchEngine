package com.studentsdealz.Crawler.DAL.STORAGE_SERVICE;

import com.studentsdealz.Crawler.DAL.DB_CASSANDRA.Crawled_Htmls_DAL;
import com.studentsdealz.Crawler.DAL.DB_CASSANDRA.Links_DAL;
import com.studentsdealz.Utils.UrlUtils;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by saran on 8/6/17.
 */
public class PageStorageDAL {
    public static void StorePage(Page page)
    {
        if (page.getParseData() instanceof HtmlParseData) {

                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                String original_url=page.getWebURL().getURL();
                String normalised_url= UrlUtils.getNormalisedURL(original_url);
                Date time_of_last_crawled=new Date();
            String domain_name=null;
            try {
                domain_name = UrlUtils.getDomainName(original_url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Crawled_Htmls_DAL.storeHtml(normalised_url,original_url,domain_name,time_of_last_crawled,htmlParseData.getHtml());

            //storing the links in order to find the most relavent information

            Set<WebURL> links = htmlParseData.getOutgoingUrls();


            HashMap<String,String> to_url_normalised=new HashMap<>();


            for(WebURL single_to_url:links)
            {
                String normalised_single_to_url=UrlUtils.getNormalisedURL(single_to_url.getURL());
                to_url_normalised.put(normalised_single_to_url,single_to_url.getAnchor());
            }

            //now storing all the links data to the cassandra database
            Links_DAL.insertLinksRelation(normalised_url,to_url_normalised);
        }
    }
}

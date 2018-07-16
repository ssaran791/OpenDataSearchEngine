package com.studentsdealz.AI.Crawler;

import com.studentsdealz.AI.Crawler.DAL.DB_MYSQL.File_Info_DAL;
import com.studentsdealz.AI.Crawler.MODEL.DB_MYSQL.File_Info_MODEL;
import com.studentsdealz.AI.Crawler.Utils.FileDownloader;
import com.studentsdealz.AppConstants;
import com.studentsdealz.Crawler.Crawlers.Data_Gov_In_Crawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saran on 8/9/17.
 */
public class CrawlerController {
   public static void main(String... args) throws Exception
   {
        List<File_Info_MODEL> to_download_url= File_Info_DAL.getToDownloadFiles(10000);


       for(File_Info_MODEL single_to_download_url:to_download_url)
       {
           FileDownloader.download_file_from_http(single_to_download_url.getFile_url(),AppConstants.CrawlerConstants_AI.STORAGE_FOLDER+single_to_download_url.getFile_name()+".xls");
       }
       System.out.println("Finished Downloading all the files.");
   }
}

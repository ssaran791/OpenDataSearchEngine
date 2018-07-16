package com.studentsdealz.Crawler;

import com.studentsdealz.AppConstants;
import com.studentsdealz.Crawler.Crawlers.Data_Gov_In_Crawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by saran on 8/5/17.
 */
public class CrawlerController {
    private static String crawlStorageFolder = AppConstants.CrawlerConstants.STORAGE_FOLDER;
    private static int numberOfCrawlers = AppConstants.CrawlerConstants.NO_OF_CRAWLING_THREADS;
    private static String userAgentString= AppConstants.CrawlerConstants.USER_AGENT;




    public static void main(String[] args) throws Exception {



        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setUserAgentString(userAgentString);
        config.setIncludeHttpsPages(true);
        config.setResumableCrawling(true);
        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        ///adding the save shutdown while termination in order to resume the crawling
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("=======INFO==========");
                System.out.println("Please wait closing all the Threads");
                controller.shutdown();
                controller.waitUntilFinish();
            }
        }, "Shutdown-thread"));



        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */

        //adding the initial seeds to the crawler
        //currently taking the 5 main data to index for the search engines
        controller.addSeed("https://data.gov.in/catalogs/sector/Agriculture-9212");
        controller.addSeed("https://data.gov.in/catalogs/sector/Information%20and%20Communications-9252");
        controller.addSeed("https://data.gov.in/catalogs/sector/Information%20and%20Broadcasting-9341");
        controller.addSeed("https://data.gov.in/catalogs/sector/Transport-9383");
        controller.addSeed("https://data.gov.in/catalogs/sector/Governance%20and%20Administration-9302");



        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(Data_Gov_In_Crawler.class, numberOfCrawlers);
    }
}

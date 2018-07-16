package com.studentsdealz.Testings;

import ch.sentric.URL;
import org.apache.lucene.document.Document;
import org.apache.tika.parser.html.HtmlParser;

import java.net.MalformedURLException;

/**
 * Created by saran on 8/7/17.
 */
public class URL_Normalisation {
    public static void main(String... args)
    {

        try {
            URL url = new URL("http://www.example.com:80/bar.html?a=m");
            System.out.println(url.getNormalizedUrl());
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("In shutdown hook");
            }
        }, "Shutdown-thread"));
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

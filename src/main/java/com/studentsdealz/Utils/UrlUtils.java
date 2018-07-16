package com.studentsdealz.Utils;

import ch.sentric.URL;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by saran on 8/7/17.
 */
public class UrlUtils {
    public static String getNormalisedURL(String orginal_url)
    {
        String new_url="";
        try {
            URL url = new URL(orginal_url);
           new_url=(url.getNormalizedUrl());
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
        if(new_url==null)
            return orginal_url;
        return new_url;
    }
    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}

package com.lucas;

import java.util.List;

public interface Database {



    List<CrawlURL> searchBy(int status,long before,int count);
    long searchByCount(int status,long before);
    CrawlURL findCurl(String url);
    CrawlURL insertCurl(CrawlURL curl);
    CrawlURL updateCurl(CrawlURL curl);

}

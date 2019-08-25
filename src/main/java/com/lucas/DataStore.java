package com.lucas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DataStore {

    private static Logger logger = LoggerFactory.getLogger(SQLLiteDB.class.getName());

    private Database db;
    public DataStore(Database db){
        this.db =db;
    }



    private String normalizeURL(String url){
        int i=0;
        if(url.endsWith("/#")){
            i=2;
        }else
        if(url.endsWith("/")||url.endsWith("#")){
            i=1;
        }
        return url.substring(0,url.length()-i);

    }

    public void addByCrawler(String url){
        url = normalizeURL(url);

        if(db.findCurl(url)==null){
            CrawlURL crawlURL = new CrawlURL();
            crawlURL.crawlStatus = Constants.CURL_STATUS_NONE;
            crawlURL.lastCrawled =-1;
            crawlURL.changeFrequency = Constants.FREQUENCY_NEVER;
            crawlURL.url =url;
            logger.info(String.format("add %s = ",crawlURL));
            logger.info(String.format("add insert new curl %s = ",crawlURL));
            db.insertCurl(crawlURL);
        }
    }

    public synchronized  void addBySiteMap(CrawlURL curl){
        logger.info(String.format("add %s = ",curl));
        curl.url = normalizeURL(curl.url);
        CrawlURL crawlURL =db.findCurl(curl.url);
        if(crawlURL!=null){
            if(crawlURL.changeFrequency!=curl.changeFrequency){
                logger.info(String.format("add update existing for freq change %s = ",curl));
                db.updateCurl(curl);
            }
        }else{
            logger.info(String.format("add insert new curl %s = ",curl));
            db.insertCurl(curl);
        }
    }

    public synchronized List<CrawlURL> getListOfWaitingCurls(int count,long before){
        logger.info("getListOfWaitingCurls");
        List<CrawlURL> output= db.searchBy(Constants.CURL_STATUS_NONE,before,count);
        logger.info(String.format("getListOfWaitingCurls size = %d",output.size()));
        return output;
    }

    public synchronized long getCountOfWaitingCurls(long before){
        logger.info("getCountOfWaitingCurls");
        Long res= db.searchByCount(Constants.CURL_STATUS_NONE,before);
        logger.info(String.format("getCountOfWaitingCurls size = %d",res));
        return res;
    }

    public synchronized  void updateCurl(CrawlURL curl){
        logger.info(String.format("updateCurl curl =  %s",curl));
        db.updateCurl(curl);
    }

}

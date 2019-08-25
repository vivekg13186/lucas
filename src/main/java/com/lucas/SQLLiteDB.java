package com.lucas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SQLLiteDB implements Database {

    private static Logger logger = LoggerFactory.getLogger(SQLLiteDB.class.getName());

    private com.dieselpoint.norm.Database db ;

    public SQLLiteDB() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        //System.setProperty("norm.dataSourceClassName","org.sqlite.SQLiteDataSource");
         db= new com.dieselpoint.norm.Database();
        try {
            db.createTable(CrawlURL.class);
        } catch (Exception w) {
            logger.warn("ignore if existing table error",w);
        }
    }


    @Override
    public List<CrawlURL> searchBy(int status,long before,int count) {
        logger.info(String.format("searchBy status  = %d",status));
        List<CrawlURL> result=db.sql("select * from queue where crawlStatus = ? and lastCrawled < ? LIMIT ?",status,before,count).results(CrawlURL.class);
        //List<CrawlURL> result=  db.where("crawlStatus = ? and lastCrawled < ? LIMIT ?", status,before,count).results(CrawlURL.class);
        if(result==null){
            return  new ArrayList<CrawlURL>(0);
        }
        return result;
    }

    @Override
    public long searchByCount(int status,long before) {
        logger.info(String.format("searchByCount status  = %d",status));
        Integer c=db.sql("select count(*) as count from queue where crawlStatus = ? and lastCrawled < ? ",status,before).first(Integer.class);
        return c;
    }

    @Override
    public CrawlURL findCurl(String url) {
        logger.info(String.format("findCurl url  = %s",url));
        return  db.where("url = ? ", url).first(CrawlURL.class);
    }

    @Override
    public CrawlURL insertCurl(CrawlURL curl) {
        logger.info(String.format("insertCurl url  = %s",curl));
        db.insert(curl);
        return curl;
    }

    @Override
    public CrawlURL updateCurl(CrawlURL curl) {
        logger.info(String.format("updateCurl url =  %s",curl));
        db.update(curl);
        return curl;
    }
}

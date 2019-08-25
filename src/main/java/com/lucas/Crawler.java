package com.lucas;


import me.tongfei.progressbar.ProgressBar;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Crawler implements Runnable {


    private static Logger logger = LoggerFactory.getLogger(Crawler.class.getName());
    private LazyScheduler scheduler;
    private DataHandler dataHandler;
    private ProgressBar pb;
    private UrlValidator urlValidator =new UrlValidator();

    public Crawler(LazyScheduler scheduler, int dataType, ProgressBar pb) {
        this.scheduler = scheduler;
        this.dataHandler = DataHandlerFactory.getHandler(dataType);
        this.pb = pb;
    }


    private String normalizeURL(String url){
        int i=0;
        if(url.endsWith("/#")){
           i=2;
        }
        if(url.endsWith("/")){
           i=1;
        }
        return url.substring(0,url.length()-i);

    }
    @Override
    public void run() {
        while (true) {

            CrawlURL crawlURL = scheduler.getNextURL();//scheduler.getQueue().poll(3, TimeUnit.SECONDS);
            List<String> urls = null;
            if (crawlURL == null) {
                logger.info("nothing to  crawl closing");
                return;
            } else {
                logger.info(String.format("got new url to crawl = %s", crawlURL));
                try {
                    TimeUnit.MILLISECONDS.sleep(scheduler.getDelay());
                    urls = dataHandler.handle(crawlURL, scheduler.getOutDir());
                    //urls=new ArrayList<>(1);
                    //urls.add("https://www.google.com/"+ RandomStringUtils.random(3,"abcdefghijknmloprqstuvwxyz"));
                    crawlURL.crawlStatus = Constants.CURL_STATUS_NONE;
                    crawlURL.lastCrawled = System.currentTimeMillis();
                } catch (Exception e) {
                    logger.info("error while fetching data", e);
                    crawlURL.crawlStatus = Constants.CURL_STATUS_ERROR;
                    crawlURL.lastCrawled = System.currentTimeMillis();
                }
                scheduler.getDataStore().updateCurl(crawlURL);
                if (pb != null) {
                    pb.step();
                }

                if (urls != null) {
                    logger.info(String.format("new urls found by crawler size = %d", urls.size()));
                    for (String url : urls) {
                        if (url != null && !url.isEmpty()) {

                              //  url =normalizeURL(url);

                            if(urlValidator.isValid(url)){
                                //TODO fix http https
                                logger.info(String.format("crawler adding url = %s", url));
                                scheduler.getDataStore().addByCrawler(url);
                            }
                        }
                    }
                }
            }

        }
    }

}

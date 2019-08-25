package com.lucas;

import me.tongfei.progressbar.ProgressBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

import static com.lucas.Constants.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class LazyScheduler {


    private DataStore dataStore;
    private Queue<CrawlURL> queue;
    private  ScheduledExecutorService crawler_service;
    private String outDir;
    private int delay;
    private int noOfWorkers;
    private long startAt;


    private static Logger logger = LoggerFactory.getLogger(LazyScheduler.class.getCanonicalName());

    private ProgressBar pb;
    private long count=0;

    public LazyScheduler(DataStore dataStore,int noOfWorkers,int delay,String outDir) {
        queue =new LinkedList<CrawlURL>();
        this.pb= new ProgressBar("crawling", 1);
        this.dataStore=dataStore;
        this.delay=delay;
        this.outDir = outDir;
        this.delay=delay;
        this.noOfWorkers = noOfWorkers;
    }


    public synchronized CrawlURL getNextURL(){
        if(queue.isEmpty()){
            List<CrawlURL> newList =dataStore.getListOfWaitingCurls(1000,startAt);
            queue.addAll(newList);
            count +=newList.size();
            pb.maxHint(count);
        }
        while (true){
            long now = System.currentTimeMillis();
            if(queue.isEmpty()){
                return null;
            }
            CrawlURL crawlURL =queue.remove();
            long cf = crawlURL.changeFrequency;
            boolean ok=false;
            if (crawlURL.lastCrawled == -1|| cf ==FREQUENCY_ALWAYS) {//first time
                ok =true;
                logger.info(String.format("match   FREQUENCY_ALWAYS |FIRST curl = %s",crawlURL));
            } else if (cf == FREQUENCY_HOURLY) {
                long delta = MILLISECONDS.toHours(now - crawlURL.lastCrawled);
                if (delta > 1) {
                    ok =true;
                    logger.info(String.format("match FREQUENCY_HOURLY scheduled curl = %s",crawlURL));
                }
            } else if (cf == FREQUENCY_DAILY) {
                long delta = MILLISECONDS.toDays(now - crawlURL.lastCrawled);
                if (delta > 1) {
                    ok =true;
                    logger.info(String.format("match FREQUENCY_DAILY scheduled curl = %s",crawlURL));
                }
            } else if (cf == FREQUENCY_WEEKLY) {
                long delta = MILLISECONDS.toDays(now - crawlURL.lastCrawled);
                if (delta > 7) {
                    ok =true;
                    logger.info(String.format("match FREQUENCY_WEEKLY scheduled curl = %s",crawlURL));
                }
            } else if (cf == FREQUENCY_MONTHLY) {
                long delta = MILLISECONDS.toDays(now - crawlURL.lastCrawled);
                if (delta > 30) {
                    ok =true;
                    logger.info(String.format("match FREQUENCY_MONTHLY scheduled curl = %s",crawlURL));
                }
            } else if (cf == FREQUENCY_YEARLY) {
                long delta = MILLISECONDS.toDays(now - crawlURL.lastCrawled);
                if (delta > 365) {
                    ok =true;
                    logger.info(String.format("match FREQUENCY_YEARLY scheduled curl = %s",crawlURL));
                }
            }
            if(ok){
                crawlURL.crawlStatus = Constants.CURL_STATUS_SCHEDULED;
                dataStore.updateCurl(crawlURL);
                logger.info(String.format("getNextURL curl = %s",crawlURL));
                return crawlURL;
            }else{
                crawlURL.lastCrawled = System.currentTimeMillis();
                dataStore.updateCurl(crawlURL);
            }
        }

    }






    public void start() {
        startAt =System.currentTimeMillis();
        pb = new ProgressBar("crawling", 1);
        pb.start();
        crawler_service = Executors.newScheduledThreadPool(noOfWorkers);
        for(int i=0;i<noOfWorkers;i++){
            Crawler c = new Crawler(this, CURL_DATA_TYPE_HTML,pb);
            crawler_service.execute(c);
            logger.info(String.format("scheduled created Crawler # %d",i));
        }

        crawler_service.shutdown();
    }




    public void waitForFinish() {
        while (true){
            try {
                    if(crawler_service.awaitTermination(3, MILLISECONDS)){
                        logger.info("all crawlers finished scheduler shutdown");
                        logger.info("crawling finished...");
                        pb.stop();
                        return;
                    }
                //TimeUnit.SECONDS.sleep(3);

            } catch (InterruptedException e) {
                logger.error("unknown exception scheduler",e);
            }
        }

    }


    public int getDelay() {
        return delay;
    }


    public DataStore getDataStore() {
        return dataStore;
    }


    public String getOutDir() {
        return outDir;
    }
}

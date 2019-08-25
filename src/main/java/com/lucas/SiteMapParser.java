package com.lucas;

import com.lucas.html.Helper;
import me.tongfei.progressbar.ProgressBar;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.lucas.Constants.*;

public class SiteMapParser implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ScanSiteMap.class.getName());

    private DataStore ds ;
    private BlockingQueue<String> queue;
    private  ProgressBar pb;

     SiteMapParser(DataStore ds, BlockingQueue<String> queue, ProgressBar pb){
            this.ds =ds;
            this.queue=queue;
            this.pb=pb;
    }
    private void processSiteMapIndex(Document doc) {
        Elements sitemap = doc.select("sitemap");
        if (!sitemap.isEmpty()) {
            for (Element e : sitemap) {
                Elements locs = e.getElementsByTag("loc");
                if (!locs.isEmpty()) {
                    try {
                        logger.info(String.format("processSiteMap adding site map = %s", locs.text()));
                        queue.put(locs.text());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }
    }

    private static  long freqStrToCode(String txt) {
        if (txt == null) return FREQUENCY_NEVER;
        txt = txt.toLowerCase();
        switch (txt) {

            case "hourly":
                return FREQUENCY_HOURLY;
            case "daily":
                return FREQUENCY_DAILY;
            case "weekly":
                return FREQUENCY_WEEKLY;
            case "monthly":
                return FREQUENCY_MONTHLY;
            case "yearly":
                return FREQUENCY_YEARLY;
            case "never":
                return FREQUENCY_NEVER;
            default:
                return FREQUENCY_NEVER;
        }
    }

    private void processSiteMap(Document doc) {
        Elements urls = doc.select("url");
        if (!urls.isEmpty()) {
            for (Element e : urls) {
                CrawlURL crawlURL = new CrawlURL();
                crawlURL.url = Helper.getFirstElementTextByTag("loc", e, "");
                String frqs = Helper.getFirstElementTextByTag("changefreq", e, "always");
                crawlURL.changeFrequency = freqStrToCode(frqs);
                crawlURL.lastCrawled = -1;
                crawlURL.crawlStatus = CURL_STATUS_NONE;
                logger.info(String.format("processSiteMap adding new  curl = %s", crawlURL));
                ds.addBySiteMap(crawlURL);
            }
        }
    }

    public void run() {
        while (true){
            String url ;
            try {
                url = queue.poll(6, TimeUnit.SECONDS);

                if(url==null){
                    logger.info("nothing to process out of loop");
                    break;
                }
                logger.info(String.format("processing url = %s",url));
                Document doc = Helper.getAsDocument(url);
                if (doc != null) {
                    processSiteMapIndex(doc);
                    processSiteMap(doc);
                    pb.maxHint(queue.size());
                    pb.step();
                }
            } catch (InterruptedException e) {
                logger.error("unknown error",e);
            }

        }
    }
}

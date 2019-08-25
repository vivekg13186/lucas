package com.lucas;


import me.tongfei.progressbar.ProgressBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

public class ScanSiteMap {

    private DataStore ds ;
    private BlockingQueue<String> queue= new LinkedBlockingDeque<>();

    private static Logger logger = LoggerFactory.getLogger(ScanSiteMap.class.getName());

    private ScanSiteMap(){

    }

    public void start(String url,DataStore ds) {
        try {
            ProgressBar pb = new ProgressBar("processing site map", 100);
            pb.start();
            logger.info("start");
            this.ds =ds;
            URL urli = new URL(url);
            String sitemap =String.format("%s://%s/sitemap.xml", urli.getProtocol(), urli.getHost());
            queue.put(sitemap);
            ExecutorService service = Executors.newFixedThreadPool(3);
            for(int i=0;i<3;i++){
                logger.info("starting SiteMapParser #"+i);
                service.submit(new SiteMapParser(ds,queue,pb));
            }
            service.shutdown();
            while (true){
                if(service.awaitTermination(10,TimeUnit.SECONDS)){
                    logger.info("all SiteMapParser finished");
                    pb.stop();

                    return;
                }
            }
        } catch (MalformedURLException e) {
            logger.error("sitemap xml no available for this site = "+url,e);
            ds.addByCrawler(url);
            return;
        } catch (InterruptedException e) {
            logger.error("unknown error",e);
            e.printStackTrace();
            return;
        }

    }
    public static final ScanSiteMap scanSiteMap =new ScanSiteMap();
}

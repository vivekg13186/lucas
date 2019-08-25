package com.lucas;

import java.util.List;

public interface DataHandler {

    List<String> handle(CrawlURL crawlURL, String outdir) throws Exception;
}

package com.lucas;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Queue")
public class CrawlURL {

    @Id
    @Column(name = "url")
    public String url;
    @Column(name = "lastCrawled")
    public long lastCrawled;
    @Column(name = "changeFrequency")
    public long changeFrequency;
    @Column(name = "crawlStatus")
    public int crawlStatus;

    public String toString(){
        return String.format("{ url = %s , lastCrawled = %d , changeFrequency = %d , crawlStatus =%d } ",url,lastCrawled,changeFrequency,crawlStatus);
    }

}

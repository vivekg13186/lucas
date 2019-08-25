package com.lucas.html;

import com.google.gson.Gson;
import com.justext.JusText;
import com.justext.paragraph.Paragraph;
import com.justext.util.StopWordsUtil;
import com.lucas.CrawlURL;
import com.lucas.Crawler;
import com.lucas.DataHandler;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class HTMLDataHandler implements DataHandler {
    private static Logger logger = LoggerFactory.getLogger(HTMLDataHandler.class.getName());

    static Set<String> stopWords = StopWordsUtil.getStopWords("en");
    @Override
    public List<String> handle(CrawlURL crawlURL, String outdir) throws Exception {
        Article article = new Article();
        Document doc = Helper.getAsDocument(crawlURL.url);
        article.url = crawlURL.url;
        article.publishedDate = ExtractPublishedDate.extractArticlePublishedDate(doc);
        article.content =content(doc);
        article.title = doc.title();
        saveArticle(article,outdir);
        return Arrays.asList(getAllLinks(doc));
    }


    private void saveArticle(Article a,String outdir) throws IOException {
        Gson gson = new Gson();
        String randomName  = UUID.randomUUID().toString();
        Path path = Paths.get(outdir,"output",randomName+".json");
        FileUtils.writeStringToFile(path.toFile(), gson.toJson(a),"utf-8",false);
    }


    private String[] getAllLinks(Document doc) {
        Elements links = doc.select("a[href]");
        String[] result = new String[links.size()];
        int i = 0;
        for (Element link : links) {
            result[i] = link.attr("abs:href");
        }
        return result;
    }


    private String content(Document doc) {
        JusText jusText = new JusText();
        List<Paragraph> paragraphs = jusText.extract(doc.html(), stopWords);
        StringBuilder sb = new StringBuilder();
        for (Paragraph p : paragraphs) {
            sb.append(p.getText());
        }
        return sb.toString();
    }
}

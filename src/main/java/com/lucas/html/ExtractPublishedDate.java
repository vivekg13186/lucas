package com.lucas.html;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pojava.datetime.DateTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractPublishedDate {
    private static final String regex = "([\\.\\/\\-_]{0,1}(19|20)\\d{2})[\\.\\/\\-_]{0,1}(([0-3]{0,1}[0-9][\\.\\/\\-_])|(\\w{3,5}[\\.\\/\\-_]))([0-3]{0,1}[0-9][\\.\\/\\-]{0,1})?";
    private static final Pattern pattern = Pattern.compile(regex);

    static String extractFromURL(String url) {
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            String st = matcher.group(0);
            if (st != null && !st.isEmpty() && st.length() > 1) {
                return st.substring(1);
            }
        }
        return null;
    }

    static String extractFromLDJson(Document doc) {
        Elements elements = doc.select("script");
        Gson gson = new Gson();
        for (Element e : elements) {
            if (e.hasAttr("type") && e.attr("type") == "application/ld+json") {
                JsonElement json = gson.fromJson(e.html(), JsonElement.class);
                JsonObject jobj = json.getAsJsonObject();
                if (jobj.has("datePublished")) {
                    return jobj.get("datePublished").getAsString();
                } else if (jobj.has("dateCreated")) {
                    return jobj.get("dateCreated").getAsString();
                }
            }
        }
        return null;
    }

    static boolean inArray(String element, String[] arr) {
        if (element == null) return false;
        for (String a : arr) {
            if (element.equals(a)) {
                return true;
            }
        }
        return false;
    }

    static String extractFromMeta(Document doc) {
        Elements es = doc.select("meta");
        for (Element e : es) {
            String metaName = e.attr("name").toLowerCase();
            String itemProp = e.attr("itemprop").toLowerCase();
            String httpEquiv = e.attr("http-equiv").toLowerCase();
            String metaProperty = e.attr("property").toLowerCase();

            //<meta name="pubdate" content="2015-11-26T07:11:02Z" >
            //<meta name='publishdate' content='201511261006'/>
            //<meta name="timestamp"  data-type="date" content="2015-11-25 22:40:25" />
            //<meta name="DC.date.issued" content="2015-11-26">
            //<meta name="Date" content="2015-11-26" />
            //<meta name="sailthru.date" content="2015-11-25T19:56:04+0000" />
            //<meta name="article.published" content="2015-11-26T11:53:00.000Z" />
            //<meta name="published-date" content="2015-11-26T11:53:00.000Z" />
            //<meta name="article.created" content="2015-11-26T11:53:00.000Z" />
            //<meta name="article_date_original" content="Thursday, November 26, 2015,  6:42 AM" />
            //<meta name="cXenseParse:recs:publishtime" content="2015-11-26T14:42Z"/>
            //<meta name="DATE_PUBLISHED" content="11/24/2015 01:05AM" />
            String[] a1 = {"pubdate", "publishdate", "timestamp", "DC.date.issued", "Date", "sailthru.date", "article.published", "published-date", "article.created", "article_date_original", "cXenseParse:recs:publishtime", "DATE_PUBLISHED"};

            //<meta property="article:published_time"  content="2015-11-25" />
            //<meta property="bt:pubDate" content="2015-11-26T00:10:33+00:00">
            String[] a2 = {"article:published_time", "bt:pubDate"};

            //<meta itemprop="datePublished" content="2015-11-26T11:53:00.000Z" />

            String[] a3 = {"datePublished"};

            if (inArray(metaName, a1) || inArray(metaProperty, a2) || inArray(itemProp, a3)) {
                return e.attr("content").trim();
            }

            //<meta property="og:image" content="http://www.dailytimes.com.pk/digital_images/400/2015-11-26/norway-return-number-of-asylum-seekers-to-pakistan-1448538771-7363.jpg"/>
            if (metaProperty == "og:image" || metaProperty == "image") {
                String url = e.attr("content").trim();
                return extractFromURL(url);
            }

            //<meta http-equiv="data" content="10:27:15 AM Thursday, November 26, 2015">
            if (httpEquiv == "date") {
                return e.attr("content").trim();

            }


        }
        return null;
    }

    static String extractFromHTMLTag(Document doc) {
        Elements es = doc.getElementsByTag("time");
        for (Element e : es) {
            if (e.hasAttr("datetime")) {
                String att = e.attr("datetime");
                if (att != null && !att.isEmpty()) {
                    return att;
                }
            }
            if (e.hasClass("timestamp")) {
                String att = e.text();
                if (att != null && !att.isEmpty()) {
                    return att;
                }
            }
        }
        es = doc.getElementsByTag("span");
        for (Element e : es) {
            if (e.hasAttr("itemprop") && e.attr("itemprop").toLowerCase() == "datepublished") {
                String att = e.text();
                if (att != null && !att.isEmpty()) {
                    return att;
                }
            }
        }
        es = doc.getElementsByTag("span,div,p");
        for (Element e : es) {
            if (e.hasClass("pubdate")
                    || e.hasClass("timestamp")
                    || e.hasClass("article_date")
                    || e.hasClass("articledate")
                    || e.hasClass("date")
            ) {
                String att = e.text();
                if (att != null && !att.isEmpty()) {
                    return att;
                }
            }

        }
        return null;
    }

    static long extractArticlePublishedDate(Document doc) {
        try {
            String possibleDate = extractFromLDJson(doc);

            if (possibleDate != null)
                return DateTime.parse(possibleDate).toMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String possibleDate = extractFromMeta(doc);

            if (possibleDate != null)
                return DateTime.parse(possibleDate).toMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String possibleDate = extractFromHTMLTag(doc);
            if (possibleDate != null)
                return DateTime.parse(possibleDate).toMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String possibleDate = extractFromURL(doc.location());
            if (possibleDate != null)
                return DateTime.parse(possibleDate).toMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;

    }

}



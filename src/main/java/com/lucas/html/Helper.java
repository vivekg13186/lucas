package com.lucas.html;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class Helper {

    public static final DateTimeFormatter W3C_FULLDATE_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    protected static final ZoneId TIME_ZONE_UTC = ZoneId.of(ZoneOffset.UTC.toString());
    public static final DateTimeFormatter W3C_SHORTDATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy[-MM[-dd]]", Locale.ROOT).withZone(TIME_ZONE_UTC);

    public static String getFirstElementTextByTag(String tagName, Element doc, String defaultValue) {
        if (doc == null) return defaultValue;
        Element e = doc.getElementsByTag(tagName).first();
        if (e == null) {
            return defaultValue;
        }
        String text = e.text();
        if (text == null) return defaultValue;
        return text;
    }

    public static long convertToDate(String date) {

        ZonedDateTime zdt = convertToZonedDateTime(date);
        if (zdt == null) {
            return System.currentTimeMillis();
        }
        return zdt.toInstant().toEpochMilli();
    }

    public static ZonedDateTime convertToZonedDateTime(String date) {

        if (date == null) {
            return null;
        }

        // full date including daytime and optional time zone
        try {
            return W3C_FULLDATE_FORMATTER.parse(date, ZonedDateTime::from);
        } catch (DateTimeParseException e) {
            // fall-through and try date without daytime
        }

        // dates without daytime
        try {
            TemporalAccessor ta = W3C_SHORTDATE_FORMATTER.parse(date);
            LocalDate ldt = null;
            if (ta.isSupported(ChronoField.DAY_OF_MONTH)) {
                ldt = LocalDate.from(ta);
            } else if (ta.isSupported(ChronoField.MONTH_OF_YEAR)) {
                ldt = YearMonth.from(ta).atDay(1);
            } else if (ta.isSupported(ChronoField.YEAR)) {
                ldt = Year.from(ta).atDay(1);
            }
            if (ldt != null) {
                return ldt.atStartOfDay(TIME_ZONE_UTC);
            }
        } catch (DateTimeParseException e) {
        }

        return null;
    }

    public static String get(String url) {
        try {
            return Jsoup.connect(url).ignoreHttpErrors(true).execute().body();
        } catch (Exception e) {
            return null;
        }

    }

    public static Document getAsDocument(String url) {
        try {
            return Jsoup.connect(url)

                    .followRedirects(true)
                    .ignoreHttpErrors(true).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}

package com.lucas;

public class Constants {

    public static final int CURL_STATUS_NONE=-1;
    public static final int CURL_STATUS_SCHEDULED=1;
    public static final int CURL_STATUS_ERROR=2;

    public static final int CURL_DATA_TYPE_HTML=1;
    public static final int CURL_DATA_TYPE_IMAGE=2;

    public static final long FREQUENCY_ALWAYS = 0;
    public static final long FREQUENCY_HOURLY = 1;
    public static final long FREQUENCY_DAILY = 2;
    public static final long FREQUENCY_WEEKLY = 3;
    public static final long FREQUENCY_MONTHLY = 4;
    public static final long FREQUENCY_YEARLY = 5;
    public static final long FREQUENCY_NEVER = 6;

    public static final int DEFAULT_MIN_DELAY_IS_SECOND=5;
    public static final int DEFAULT_MIN_WORKERS=2;

}

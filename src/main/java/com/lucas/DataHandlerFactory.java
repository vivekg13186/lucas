package com.lucas;

import com.lucas.html.HTMLDataHandler;

public class DataHandlerFactory {

    public static DataHandler getHandler(int type){
        switch (type){
            case Constants.CURL_DATA_TYPE_HTML:
                return  new HTMLDataHandler();
        }

        return  null;
    }
}

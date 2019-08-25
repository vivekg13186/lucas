package com.lucas;

import org.apache.commons.io.FilenameUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrawlHelper {


    private static Logger logger = Logger.getLogger(CrawlHelper.class.getName());

    public static  String randomName(){
       return java.util.UUID.randomUUID().toString();
    }

    public static void downloadImage(String url,String outdir){
        URL website = null;
        try {
            website = new URL(url);
            String filename = FilenameUtils.getName(website.getPath());
            filename =filename == null || filename.isEmpty()?randomName():filename;
            String ext = FilenameUtils.getExtension(filename);
            ext = ext==null || ext.isEmpty()?".png" : ext;
            String path = Paths.get(outdir,filename+"."+ext).toString();
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(path);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            logger.log(Level.ALL,"image downloaded from "+url+" to "+path);
        } catch (MalformedURLException e) {
            logger.log(Level.ALL,"error while downloading image from "+url);
            logger.log(Level.ALL,e.getMessage());
        } catch (FileNotFoundException e) {
            logger.log(Level.ALL,"error while downloading image from "+url);
            logger.log(Level.ALL,e.getMessage());
        } catch (IOException e) {
            logger.log(Level.ALL,"error while downloading image from "+url);
            logger.log(Level.ALL,e.getMessage());
        }
    }


}

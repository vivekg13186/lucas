package com.lucas;

import me.tongfei.progressbar.ProgressBar;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import  org.sqlite.JDBC;
import java.nio.file.Paths;

import static com.lucas.Constants.*;


public class CLIMain {


    private static Logger logger = LoggerFactory.getLogger(CLIMain.class.getName());
    private static boolean validateConfig(Config config){
        if(config.getStart()==null && config.getStart().isEmpty()){
            logger.error("start url missing in yaml file");
            return false;
        }
        if(config.getDelay()<DEFAULT_MIN_DELAY_IS_SECOND)config.setDelay(DEFAULT_MIN_DELAY_IS_SECOND);

        if(config.getOutdir()==null && config.getOutdir().isEmpty()){
            logger.error("outdir  missing in yaml file");
            return false;
        }
        File file =new File(config.getOutdir());
        if(!file.exists() || !file.canWrite()){
            logger.error(String.format("outdir %s not exist or no write permission",file.toString()));
            return false;
        }
        File output=Paths.get(config.getOutdir(),"output").toFile();
        if(!output.exists()){
            if(!output.mkdir()){
                logger.error(String.format("cannot create %s dir",output.toString()));
                return false;
            }
        }
        if(config.getWorkers()<DEFAULT_MIN_WORKERS)config.setWorkers(DEFAULT_MIN_WORKERS);
        return true;

    }
    public static void main(String[] arg) throws FileNotFoundException, ClassNotFoundException {
        Yaml yaml = new Yaml(new Constructor(Config.class));
        logger.info(JDBC.class.getName());
        if(arg.length>=1){
            InputStream inputStream =new FileInputStream(arg[0]);
            Config config = yaml.load(inputStream);
            if(!validateConfig(config))return;
            if(config.isDebug()){
                LogManager.getRootLogger().setLevel(Level.ALL);
            }else{
                LogManager.getRootLogger().setLevel(Level.ERROR);
            }
            System.setProperty("norm.jdbcUrl", String.format("jdbc:sqlite:%s.db",Paths.get(config.getOutdir(),"lucas").toString()));
            logger.info(String.format(" jdbc url = %s",System.getProperty("norm.jdbcUrl")));
            SQLLiteDB db =new SQLLiteDB();
            DataStore ds =new DataStore(db);
            ScanSiteMap.scanSiteMap.start(config.getStart(),ds);
           // Scheduler.scheduler.start(config.getWorkers(),config.getDelay(),config.getOutdir(),ds);
            LazyScheduler lz =new LazyScheduler(ds,config.getWorkers(),config.getDelay(),config.getOutdir());
            lz.start();
            lz.waitForFinish();
        }else{
            logger.error("missing config yaml file argument");
        }


    }
}

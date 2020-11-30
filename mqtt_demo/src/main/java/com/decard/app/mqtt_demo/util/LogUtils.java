package com.decard.app.mqtt_demo.util;

import android.content.Context;
import android.os.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * Created by hizha on 2018/3/29.
 */

public class LogUtils {

//    private static final String LOG_DIR = "/mnt/sdcard/dc_Log";
    private static final String LOG_DIR = Environment.getExternalStorageDirectory().getPath()+"/DCard/log";


    private LogUtils(){}

    @Deprecated
    public static Logger logger = LoggerFactory.getLogger("log_trace");

    @Deprecated
    public static void trace(String msg){
        logger.trace(msg);
    }



    @Deprecated
    public static void trace(String msg, Object arg){
        logger.trace(msg,arg);
    }


    @Deprecated
    public static void config(boolean iPrint) {

//        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//        if (!iPrint) {
//
//            loggerContext.reset();
//
//        } else {
//
//            loggerContext.reset();
//
//            JoranConfigurator config = new JoranConfigurator();
//
//            config.setContext(loggerContext);
//            try {
//                config.doConfigure(ContextUtils.getContext().getAssets().open("logback.xml.old", Context.MODE_PRIVATE));
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JoranException e) {
//                e.printStackTrace();
//            }
//
////            //logcat
////            PatternLayoutEncoder encoder = new PatternLayoutEncoder();
////            encoder.setContext(loggerContext);
////            encoder.setPattern("[%file:%M:%line] - %msg %n");
////            encoder.start();
////
////            LogcatAppender logcatAppender = new LogcatAppender();
////            logcatAppender.setContext(loggerContext);
////
////
////            //logfile
////
////            RollingFileAppender rollingFileAppender = new RollingFileAppender();
////            rollingFileAppender.setContext(loggerContext);
////            rollingFileAppender.setFile("${LOG_DIR}/dc.trace.admin.${today}.log");
////            rollingFileAppender.();
////
////
////            ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
////            root.addAppender(logcatAppender);
////            root.addAppender(rollingFileAppender);
//
//        }
    }

        //初始化控制台&本地文件写入的logger对象,级别不同
//    private static Logger logFileTrace = LoggerFactory.getLogger("logFile_trace");
//    private static Logger logFileDebug = LoggerFactory.getLogger("logFile_debug");
//    private static Logger logFileInfo = LoggerFactory.getLogger("logFile_info");
//    private static Logger logFileWarn = LoggerFactory.getLogger("logFile_warn");
//    private static Logger logFileError = LoggerFactory.getLogger("logFile_error");

    //初始化控制台输出的Logger对象
//    private static Logger getFileLogger(Class<?> clazz){
//        return LoggerFactory.getLogger(clazz);
//    }

//    public static void trace(String message){
//        LoggerFactory.getLogger("log_trace").trace(message);
//    }
//
//    public static void trace(String format,Object message){
//        LoggerFactory.getLogger("log_trace").trace(format,message);
//    }

//    /**
//     * 只在控制台输出日志
//     * @param message  输出的信息
//     */
//    public static void trace(Class<?> clazz, String message){
//        getFileLogger(clazz).trace(message);
//    }

//    public static void debug(Class<?> clazz, String message){
//        getFileLogger(clazz).debug(message);
//    }

//    public static void info(Class<?> clazz, String message){
//        getFileLogger(clazz).info(message);
//    }
//
//    public static void warn(Class<?> clazz, String message){
//        getFileLogger(clazz).warn(message);
//    }
//
//    public static void error(Class<?> clazz, String message){
//        getFileLogger(clazz).error(message);
//    }


//    /**
//     * 在控制台输出日志，同时将日志写入到配置文件对应的本地文件中
//     * @param msg 打印信息
//     */
//    public static void traceWriteFile(String msg){
//        logFileTrace.trace(msg);
//    }

//    public static void debugWriteFile(String msg){
//        logFileDebug.debug(msg);
//    }

//    public static void infoWriteFile(String msg){
//        logFileInfo.info(msg);
//    }
//
//    public static void warnWriteFile(String msg){
//        logFileWarn.warn(msg);
//    }
//
//    public static void errorWriteFile(String msg){
//        logFileError.error(msg);
//    }

//    /**
//     * 打印异常信息，并存储在文件中
//     * @param msg  日志信息
//     * @param throwable  异常
//     */
//    public static void errorWriteFile(String msg,Throwable throwable){
//        logFileError.error(msg,throwable);
//    }


    /**
     * 打印日志
     * @param printOnLogCat 是否打印到logcat
     * @param printOnFile 是否打印到日志文件 ---> 目录："/mnt/sdcard/dc_Log"
     */
    public static void config(boolean printOnLogCat,boolean printOnFile) {
        // reset the default context (which may already have been initialized)
        // since we want to reconfigure it
        LoggerContext context  = (LoggerContext)LoggerFactory.getILoggerFactory();
        context.stop();
        context.reset();


        if(!printOnLogCat && !printOnFile){

            return;

        }

        // add the newly created appenders to the root logger;
        // qualify Logger to disambiguate from org.slf4j.Logger
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        if(printOnLogCat){
            LogcatAppender logcatAppender = configLogCat(context);
            root.addAppender(logcatAppender);
        }

        if(printOnFile){

            RollingFileAppender rollingFileAppender = configLogFile(context);
            root.addAppender(rollingFileAppender);

        }
        root.setLevel(Level.ALL);
        // print any status messages (warnings, etc) encountered in logback config
        StatusPrinter.print(context);
    }



    private static LogcatAppender configLogCat(LoggerContext context){

        // setup LogcatAppender
        PatternLayoutEncoder encoder2 = new PatternLayoutEncoder();
        encoder2.setContext(context);
        encoder2.setCharset(Charset.forName("UTF-8"));
//        encoder2.setPattern("[%file:%M:%line] - %msg %n");
        encoder2.setPattern("[%M:%line] - %msg %n");
        encoder2.start();

        LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(context);
        logcatAppender.setEncoder(encoder2);
        logcatAppender.start();

        return logcatAppender;

    }


    private static RollingFileAppender configLogFile(LoggerContext context){

        // setup FileAppender
        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        rollingFileAppender.setAppend(true);//是否以追加方式输出。默认为true。
        rollingFileAppender.setContext(context);//是否以追加方式输出。默认为true。

        String prefix = "dc.trace.";
        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
        rollingPolicy.setFileNamePattern(LOG_DIR + File.separator + prefix+"%d{yyyyMMdd}.log");
        rollingPolicy.setMaxHistory(7);//声明归档日志最大保留时间
        rollingPolicy.setCleanHistoryOnStart(true);//如果设置为true，则当appender启动时，会删除所有归档日志文件。
        rollingPolicy.setTotalSizeCap(FileSize.valueOf("512MB"));//如果设置为true，则当appender启动时，会删除所有归档日志文件。
        rollingPolicy.setParent(rollingFileAppender);  // parent and context required!
        rollingPolicy.setContext(context);
        rollingPolicy.start();

        rollingFileAppender.setRollingPolicy(rollingPolicy);


        PatternLayoutEncoder encoder1 = new PatternLayoutEncoder();
        encoder1.setCharset(Charset.forName("UTF-8"));
        encoder1.setContext(context);
//        encoder1.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
//        encoder1.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%file:%M:%line] - %msg %n");
        encoder1.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%file:%M:%line] - %msg %n");
        encoder1.start();


        rollingFileAppender.setEncoder(encoder1);
        rollingFileAppender.start();

        return rollingFileAppender;


    }

}

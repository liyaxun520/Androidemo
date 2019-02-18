package com.example.socketdemo;

import java.io.Closeable;

public class CloseUtils {

    public static void close(Closeable... closeables){

        if(closeables==null)
            return;

        try{
            for(Closeable mCloseable:closeables)
                mCloseable.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

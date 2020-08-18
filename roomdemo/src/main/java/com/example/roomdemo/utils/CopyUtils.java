package com.example.roomdemo.utils;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyUtils {

    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
//            String cmd="chmod 777 " + "data/data/com.square_enix.ss/files/dt.txt";
            String cmd="chmod 777 " + "data/data/com.example.roomdemo/databases/appDatabase";
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.e("错误信息 ",e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception e) {
                Log.e("TAG",e.getMessage());
                e.printStackTrace();
            }
        }
        return true;
    }

    public static int copy(String fromFile, String toFile)
    {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if(!root.exists())
        {
            return -1;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();

        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if(!targetDir.exists())
        {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for(int i= 0;i<currentFiles.length;i++)
        {
            if(currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");

            }else//如果当前项为文件则进行文件拷贝
            {
                CopyUtils.CopyFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return 0;
    }


    // 文件拷贝
    // 要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int CopyFile(String fromFile, String toFile) {


        Log.e("11", "22222");
        //File in = new File(fromFile);
        //File out = new File(toFile);
        Log.e("11", "555555");
        try {
            Log.e("11", "6666666");
            //FileInputStream fosfrom = new FileInputStream(in);
            //FileOutputStream fosto = new FileOutputStream(out);
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            Log.e("11", "44444");
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }

    }
}

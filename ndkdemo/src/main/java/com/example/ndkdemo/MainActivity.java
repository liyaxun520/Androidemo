package com.example.ndkdemo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.decard.NDKMethod.BasicOper;
import com.decard.entitys.IDCard;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
//    static {
//        System.loadLibrary("lison");
//    }


    private int portSate = -1;
    private String portValue = "/dev/ttyHSL1";


    private int count = 0;
    private static Lock lock = new ReentrantLock();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.addLogAdapter(new AndroidLogAdapter());

//        String strFromC = MyJni.getStrFromC();
//        Logger.d("获取c文本  {}"+strFromC);
//        //计算面积
//        double rectArea = MyJni.getRectArea(10.0, 20.0);
//        Logger.d("矩形面积 {}"+rectArea+"");
//
//        Person person = MyJni.getPerson();
//        Logger.d("获取用户信息  "+person.toString());

        portSate = BasicOper.dc_open("COM", MainActivity.this, portValue, 115200);

        Thread thread = new Thread(task);
        thread.start();
    }


    boolean bFindCard = false;


    private Runnable task = new Runnable() {
        @Override
        public void run() {
            try {
                lock.lock();
                do{
                    bFindCard = MDSEUtils.isSucceed(BasicOper.dc_reset());
                    Log.d("","射频复位");
                    SystemClock.sleep(500);
                }while (!bFindCard);
                bFindCard = false;
                long beginTime = System.currentTimeMillis();

                do {
                    String resultString = MDSEUtils.returnResult(BasicOper.dc_card_exist());
                    if (resultString != null) {

                        Integer resultInterger = Integer.valueOf(resultString, 16);
                        if ((resultInterger & 0x0008) == 0x0008 || (resultInterger & 0x4000) == 0x4000) {
                            Log.d("检测到身份证"  ,"");
                            bFindCard = true;
                            break;
                        }
                    }
//                    bFindCard = MDSEUtils.isSucceed(BasicOper.dc_card_b_hex());
                    if (System.currentTimeMillis() - beginTime > 10000) {
                        break;
                    }

                } while (!bFindCard);

                if (!bFindCard) {
                } else {

                    IDCard idCardData = BasicOper.dc_SamAReadCardInfo(1);
                    if (idCardData != null) {
                        Log.d("身份证信息  ",idCardData.toString());

                        boolean isExist = false;
                        do {

                            String cardExistString = BasicOper.dc_card_exist();
                            isExist = MDSEUtils.returnResult(cardExistString).equals("5000") || MDSEUtils.returnResult(cardExistString).equals("1008");

                        } while (isExist);


                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    };

}

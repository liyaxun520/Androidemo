package com.decard.material_spinner;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class MainActivity extends AppCompatActivity {

//    private var mSpinner: CusSpinner? = null
//    private var mAdapter: ArrayAdapter<String>? = null
//    private var mActivity: Activity? = null
//    private var mSelectedPosition = -1

    private HashMap<String, List<String>> hashMap = new HashMap<>();
    private HashMap<String,List<HashMap<String,String>>> hashMaps = new HashMap<>();

    List<HashMap<String,String>> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                clearFiles(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"dc");
//            }
//        }).start();

        HashMap<String, String> map = new HashMap<>();
        map.put("a","a");
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("b","b");

        System.out.println(hashMap.size());
    }

    public static void clearFiles(String filePath){
        File scFileDir = new File(filePath);
        File TrxFiles[] = scFileDir.listFiles();
        for(File curFile:TrxFiles ){
            curFile.delete();
        }
    }


}

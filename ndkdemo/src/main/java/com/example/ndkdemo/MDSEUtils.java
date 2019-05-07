package com.example.ndkdemo;

import android.text.TextUtils;

/**
 * Created by Dell on 2017/3/1.
 */

public class MDSEUtils {

    public static class ErrorInfo {
        public boolean isSucceed;
        public String resultCode;
    }


    public static boolean isSucceed(String s) {
        if (!TextUtils.isEmpty(s)) {
            String[] result = s.split("\\|");
            if ("0000".equals(result[0])) {
                return true;
            }
        }
        return false;
    }

    public static ErrorInfo getErrorInfo(String s) {
        ErrorInfo errorInfo = new ErrorInfo();
        if (!TextUtils.isEmpty(s)) {
            String[] result = s.split("\\|", -1);
            if ("0000".equals(result[0])) {
                errorInfo.isSucceed = true;
                errorInfo.resultCode = result[0];
                return errorInfo;
            } else {
                errorInfo.isSucceed = false;
                errorInfo.resultCode = result[0] + " " + result[1];
                return errorInfo;
            }
        }
        return errorInfo;
    }


    public static String returnResult(String s) {
        if (!TextUtils.isEmpty(s)) {
            String[] codes = s.split("\\|");
            if ("0000".equals(codes[0]) && codes.length >= 2) {
                return codes[1];
            }
        }
        return s;
    }

    public static String[] returnAllResult(String s) {
        if (!TextUtils.isEmpty(s)) {
            String[] codes = s.split("\\|", -1);
            if ("0000".equals(codes[0])) {
                String[] result = new String[codes.length - 1];
                System.arraycopy(codes, 1, result, 0, result.length);
                return result;
            }
        }
        return null;
    }
}

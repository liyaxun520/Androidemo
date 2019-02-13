package com.example.http;

import java.io.Serializable;

/**********************************************
 * 作者： Tom 
 * 日期： 2017-09-30
 * 描述： 
 ***********************************************/

public class CheckUpdateReponse implements Serializable {


    private String appPath;
    private String appVersion;
    private String appUpdateExplain;

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppUpdateExplain() {
        return appUpdateExplain;
    }

    public void setAppUpdateExplain(String appUpdateExplain) {
        this.appUpdateExplain = appUpdateExplain;
    }

    @Override
    public String toString() {
        return "CheckUpdateReponse{" +
                "appPath='" + appPath + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appUpdateExplain='" + appUpdateExplain + '\'' +
                '}';
    }
}

// NotifyCallBack.aidl
package com.example.aidl;

// Declare any non-default types here with import statements

interface NotifyCallBack {

    void notifyMainUIThread(String cusName,boolean joinOrleave);
}

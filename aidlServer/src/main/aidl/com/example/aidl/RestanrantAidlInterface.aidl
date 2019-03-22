// RestanrantAidlInterface.aidl
package com.example.aidl;

// Declare any non-default types here with import statements

interface RestanrantAidlInterface {


    void join(IBinder token,String name);

    void leave(String name);

    void registCallBack(NotifyCallBack callBack);

    void unRegistCallBack(NotifyCallBack callBack);
}

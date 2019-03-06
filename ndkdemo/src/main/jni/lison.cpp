//
// Created by Administrator on 2019/2/13.
//
#include <stdio.h>
#include "com_example_androidemo_jni_MyJni.h"
#define TAG "Lison_C++"
#include <android/log.h>
#define _A_H_

#include <iostream>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL, TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG,__VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG,__VA_ARGS__)
#define LOGU(...) __android_log_print(ANDROID_LOG_UNKNOWN, TAG,__VA_ARGS__)
#define LOGS(...) __android_log_print(ANDROID_LOG_SILENT, TAG,__VA_ARGS__)
#define LOGDF(...) __android_log_print(ANDROID_LOG_DEFAULT, TAG,__VA_ARGS__)

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_androidemo_jni_MyJni_getStrFromC
  (JNIEnv *env, jclass instance){
    // TODO
    LOGD(TAG,"getFromCMsg");
//    cout << "Hello World";
   return env->NewStringUTF("I am str from C++");
  }

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_androidemo_jni_MyJni_getRectArea(JNIEnv *env, jclass type, jdouble width,
                                                  jdouble height) {

    if(width <= 0 || height <= 0){
        return -1;
    }
    return width*height;

}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_androidemo_jni_MyJni_getPerson(JNIEnv *env, jclass type) {
    //获取到person class对象
    jclass jclass1 = env->FindClass("com/example/androidemo/Person");
    //获取到构造函数的methodId
    jmethodID jmethodID1 = env->GetMethodID(jclass1, "<init>", "(ILjava/lang/String;)V");

    jint age = 28;
    char *name = "lison";
    jstring  strName = env->NewStringUTF(name);
    //NewObject，根据class对象返回一个实例对象
    jobject jobject1 = env->NewObject(jclass1,jmethodID1,age,strName);
    return jobject1;

}
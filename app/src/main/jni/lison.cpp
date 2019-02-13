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
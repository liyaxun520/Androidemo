package com.example.http.http.bean;

/**
 * @description 解析实体基类!
 */

public class BaseEntity<T> {
    private static int SUCCESS_CODE=1;//成功的code
//    private int code;
//    private String msg;
//    private T data;
//
//
    public boolean isSuccess(){
        return getCode()==SUCCESS_CODE;
    }
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//


    private String msg;
    private int code;
    private T obj;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", obj=" + obj +
                '}';
    }


}

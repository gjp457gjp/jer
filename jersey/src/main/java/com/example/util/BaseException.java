package com.example.util;

/**
 * Created by fangxiao on 15/11/27.
 */
public class BaseException extends Exception{
    String message ="";

    Object object = null;


    public BaseException(Exception e) {
        super(e);
    }

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(String message, Object object) {
        this.message = message;
        this.object = object;
    }

    public BaseException(Exception e, String message) {
        super(e);
        this.message = message;
    }

    public BaseException(Exception e, Object object) {
        super(e);
        this.object = object;
    }

    public BaseException(Exception e, String message, Object object) {
        super(e);
        this.message = message;
        this.object = object;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
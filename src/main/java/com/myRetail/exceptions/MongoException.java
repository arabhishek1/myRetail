package com.myRetail.exceptions;

/**
 * Created by abhishek.ar on 16/06/17.
 */
public class MongoException extends Exception {

    public MongoException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MongoException(String message, Throwable cause,
                                   boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public MongoException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public MongoException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public MongoException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}

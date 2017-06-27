package com.myRetail.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public class CartmanException extends Exception {
    protected static final Logger logger = LoggerFactory.getLogger(CartmanException.class);

    private int code;
    private String message;

    public CartmanException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CartmanException(int code, String message, Exception ex) {
        this.code = code;
        this.message = message;
        logger.error("Exception Cause" + ex.getMessage());
        logger.error("Exception Stacktrace" +  Arrays.asList(ex.getStackTrace()).toString());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


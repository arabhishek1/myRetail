package com.myRetail.exceptions;

import java.io.Serializable;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class NonRetryableException extends Exception implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2341689465204360063L;
    private String message = null;

    public NonRetryableException(String message) {
        super(message);
        this.message = message;
    }

    public NonRetryableException() {
        super();
    }

    public NonRetryableException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public NonRetryableException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "RetryableException [message=" + message + "]";
    }

    public String getMessage() {
        return message;
    }

}


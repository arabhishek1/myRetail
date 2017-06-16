package com.myRetail.exceptions;

import java.io.Serializable;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class RetryableException extends Exception implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4422401625305013849L;
    private String message = null;

    public RetryableException(String message) {
        super(message);
        this.message = message;
    }

    public RetryableException() {
        super();
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    public String toString() {
        return "RetryableException [message=" + message + "]";
    }

    public String getMessage() {
        return message;
    }
}

package com.eetc.aestec.aestec.client;

/**
 * Created by twerky on 17/01/16.
 */

public class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }
}
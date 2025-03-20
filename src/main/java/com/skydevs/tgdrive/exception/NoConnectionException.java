package com.skydevs.tgdrive.exception;

public class NoConnectionException extends BaseException {

    public NoConnectionException() {
        super("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
    }

    public NoConnectionException(String msg) {
        super(msg);
    }
}

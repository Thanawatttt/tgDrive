package com.skydevs.tgdrive.exception;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("ไม่พบผู้ใช้");
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}

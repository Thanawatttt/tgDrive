package com.skydevs.tgdrive.exception;

public class PasswordErrorException extends BaseException {

    public PasswordErrorException() {
        super("รหัสผ่านไม่ถูกต้อง");
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }
}

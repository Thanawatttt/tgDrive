package com.skydevs.tgdrive.exception;

/**
 * ข้อผิดพลาดเมื่อไม่มีไฟล์คอนฟิก
 */
public class NoConfigException extends BaseException {

    /**
     * คอนสตรักเตอร์เริ่มต้น ใช้ข้อความผิดพลาดเริ่มต้น
     */
    public NoConfigException() {
        super("ตอนนี้ไม่มีไฟล์คอนฟิก");
    }

    /**
     * คอนสตรักเตอร์ที่สามารถกำหนดข้อความผิดพลาดเองได้
     * @param msg ข้อความผิดพลาดที่กำหนดเอง
     */
    public NoConfigException(String msg) {
        super(msg);
    }
}

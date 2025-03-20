package com.skydevs.tgdrive.exception;

/**
 * ข้อผิดพลาดเมื่อไม่สามารถดึงขนาดไฟล์ได้
 */
public class FailedToGetSizeException extends BaseException {

    /**
     * คอนสตรักเตอร์เริ่มต้น ใช้ข้อความผิดพลาดเริ่มต้น
     */
    public FailedToGetSizeException() {
        super("ไม่สามารถดึงขนาดไฟล์ได้");
    }

    /**
     * คอนสตรักเตอร์ที่สามารถกำหนดข้อความผิดพลาดเองได้
     * @param msg ข้อความผิดพลาดที่กำหนดเอง
     */
    public FailedToGetSizeException(String msg) {
        super(msg);
    }
}

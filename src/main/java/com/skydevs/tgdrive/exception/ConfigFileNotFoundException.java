package com.skydevs.tgdrive.exception;

/**
 * ข้อผิดพลาดเมื่อไม่พบไฟล์คอนฟิก
 */
public class ConfigFileNotFoundException extends BaseException {

    /**
     * คอนสตรักเตอร์เริ่มต้น ใช้ข้อความผิดพลาดเริ่มต้น
     */
    public ConfigFileNotFoundException() {
        super("ไม่พบไฟล์คอนฟิก");
    }

    /**
     * คอนสตรักเตอร์ที่สามารถกำหนดข้อความผิดพลาดเองได้
     * @param msg ข้อความผิดพลาดที่กำหนดเอง
     */
    public ConfigFileNotFoundException(String msg) {
        super(msg);
    }
}

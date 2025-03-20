package com.skydevs.tgdrive.exception;

/**
 * ข้อผิดพลาดเมื่อไม่ได้ตั้งค่าโทเคนของบอท
 */
public class BotNotSetException extends BaseException {

    /**
     * คอนสตรักเตอร์เริ่มต้น ใช้ข้อความผิดพลาดเริ่มต้น
     */
    public BotNotSetException() {
        super("ยังไม่ได้ตั้งค่าโทเคนของบอท");
    }

    /**
     * คอนสตรักเตอร์ที่สามารถกำหนดข้อความผิดพลาดเองได้
     * @param msg ข้อความผิดพลาดที่กำหนดเอง
     */
    public BotNotSetException(String msg) {
        super(msg);
    }
}

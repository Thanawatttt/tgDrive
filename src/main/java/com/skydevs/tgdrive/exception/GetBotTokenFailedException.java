package com.skydevs.tgdrive.exception;

/**
 * ข้อผิดพลาดเมื่อไม่สามารถดึงโทเคนของบอทได้
 */
public class GetBotTokenFailedException extends BaseException {

    /**
     * คอนสตรักเตอร์เริ่มต้น ใช้ข้อความผิดพลาดเริ่มต้น
     */
    public GetBotTokenFailedException() {
        super("ไม่สามารถดึงโทเคนของบอทได้");
    }

    /**
     * คอนสตรักเตอร์ที่สามารถกำหนดข้อความผิดพลาดเองได้
     * @param msg ข้อความผิดพลาดที่กำหนดเอง
     */
    public GetBotTokenFailedException(String msg) {
        super(msg);
    }
}

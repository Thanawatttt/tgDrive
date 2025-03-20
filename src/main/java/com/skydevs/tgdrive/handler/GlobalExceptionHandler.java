// แพ็คเกจสำหรับคลาสนี้
package com.skydevs.tgdrive.handler;

// การนำเข้าคลาสและอินเทอร์เฟซที่จำเป็น
import com.skydevs.tgdrive.exception.BaseException;
import com.skydevs.tgdrive.result.Result;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * คลาสสำหรับจัดการข้อผิดพลาดระดับโลก
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * จัดการข้อผิดพลาดทางธุรกิจ
     * @param ex ข้อผิดพลาดทางธุรกิจ
     * @return ส่งกลับข้อความข้อผิดพลาด
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("ข้อผิดพลาด: {}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * จัดการการเชื่อมต่อที่ถูกตัดขาดโดยฝั่งไคลเอ็นต์
     * @param e ข้อผิดพลาดการเชื่อมต่อที่ถูกตัดขาดโดยฝั่งไคลเอ็นต์
     */
    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException(ClientAbortException e) {
        // ไคลเอ็นต์หยุดการเชื่อมต่อ ให้บันทึกเป็นข้อมูลระดับ Info หรือเพิกเฉย
        log.info("ไคลเอ็นต์หยุดการเชื่อมต่อ: {}", e.getMessage());
    }

    /**
     * จัดการการเชื่อมต่อที่ถูกตัดขาดโดยฝั่งไคลเอ็นต์
     * @param e ข้อผิดพลาดการเชื่อมต่อที่ถูกตัดขาดโดยฝั่งไคลเอ็นต์
     */
    @ExceptionHandler(IOException.class)
    public void handleIOException(IOException e) {
        String message = e.getMessage();
        if (
            message != null &&
            (message.contains("An established connection was aborted") ||
                message.contains("你的主机中的软件中止了一个已建立的连接"))
        ) {
            log.info("ไคลเอ็นต์หยุดการเชื่อมต่อ: {}", message);
        } else {
            // จัดการ IOException อื่นๆ
            log.error("เกิดข้อผิดพลาด IOException", e);
        }
    }
}

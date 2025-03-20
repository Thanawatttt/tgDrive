package com.skydevs.tgdrive.controller;

import com.skydevs.tgdrive.result.Result;
import com.skydevs.tgdrive.service.FileService;
import com.skydevs.tgdrive.service.WebDavService;
import com.skydevs.tgdrive.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@Slf4j
@RequestMapping("/webdav")
public class WebDavController {

    @Autowired
    private FileService fileService;

    @Autowired
    private WebDavService webDacService;

    /**
     * อัปโหลดไฟล์
     * @param request คำขอ HTTP ที่มีข้อมูลไฟล์
     * @return ผลลัพธ์ของการอัปโหลด
     */
    @PutMapping("/**")
    public Result<Void> handlePut(HttpServletRequest request) {
        try (InputStream inputStream = request.getInputStream()) {
            fileService.uploadByWebDav(inputStream, request);
            return Result.success();
        } catch (Exception e) {
            log.error("อัปโหลดไฟล์ล้มเหลว", e);
            return Result.error("อัปโหลดไฟล์ล้มเหลว");
        }
    }

    /**
     * ดาวน์โหลดไฟล์
     * @param request คำขอ HTTP
     * @return ไฟล์ที่ร้องขอ
     */
    @GetMapping("/**")
    public ResponseEntity<StreamingResponseBody> handleGet(
        HttpServletRequest request
    ) {
        return fileService.downloadByWebDav(
            request.getRequestURI().substring("/webdav".length())
        );
    }

    /**
     * ลบไฟล์
     * @param request คำขอ HTTP
     * @param response การตอบกลับ HTTP
     * @return ผลลัพธ์ของการลบไฟล์
     */
    @DeleteMapping("/**")
    public Result<Void> handleDelete(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        try {
            fileService.deleteByWebDav(
                StringUtil.getPath(request.getRequestURI())
            );
            return Result.success();
        } catch (Exception e) {
            log.error("ลบไฟล์ล้มเหลว", e);
            return Result.error("ลบไฟล์ล้มเหลว");
        }
    }

    /**
     * จัดการคำขอ OPTIONS (ใช้สำหรับตรวจสอบความสามารถของ WebDAV)
     * @param response การตอบกลับ HTTP
     */
    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public void handleOptions(HttpServletResponse response) {
        response.setHeader(
            "Allow",
            "OPTIONS, HEAD, GET, POST, PROPFIND, MKCOL, MOVE, COPY"
        );
        response.setHeader("DAV", "1,2");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * จัดการคำขอพิเศษของ WebDAV
     * @param request คำขอ HTTP
     * @param response การตอบกลับ HTTP
     * @throws IOException หากมีข้อผิดพลาดระหว่างการประมวลผล
     */
    @RequestMapping(value = "/dispatch/**", method = { RequestMethod.POST })
    public void handleWebDav(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        webDacService.switchMethod(request, response);
    }
}

package com.skydevs.tgdrive.controller;

import com.skydevs.tgdrive.result.Result;
import com.skydevs.tgdrive.service.BackupService;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/backup")
@Slf4j
public class BackupController {

    private static final String DATABASE_PATH = "db/tgDrive.db"; // เส้นทางของไฟล์ฐานข้อมูล SQLite

    @javax.annotation.Resource
    private BackupService backupService;

    /**
     * ดาวน์โหลดไฟล์สำรองฐานข้อมูล
     * @return ไฟล์ฐานข้อมูล SQLite
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadBackup() {
        File file = new File(DATABASE_PATH);
        if (!file.exists()) {
            log.error("ไม่พบไฟล์ฐานข้อมูล");
            return ResponseEntity.notFound().build();
        }

        log.info(
            "ชื่อไฟล์: " +
            file.getName() +
            " เส้นทาง: " +
            file.getAbsolutePath()
        );

        if (!file.canRead()) {
            log.error("ไม่มีสิทธิ์อ่านไฟล์ฐานข้อมูล: {}", DATABASE_PATH);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=tgDrive.db"
            )
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }

    /**
     * อัปโหลดไฟล์สำรองฐานข้อมูล
     * @param multipartFile ไฟล์สำรองฐานข้อมูลที่อัปโหลด
     * @return สถานะของการอัปโหลด
     */
    @PostMapping("/upload")
    public Result<String> uploadBackupDb(
        @RequestParam MultipartFile multipartFile
    ) {
        try {
            backupService.loadBackupDb(multipartFile);
            log.info("กู้คืนฐานข้อมูลสำเร็จ");
            return Result.success("กู้คืนฐานข้อมูลสำเร็จ");
        } catch (Exception e) {
            log.error("กู้คืนฐานข้อมูลล้มเหลว");
            return Result.error("กู้คืนฐานข้อมูลล้มเหลว");
        }
    }
}

package com.skydevs.tgdrive.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.skydevs.tgdrive.dto.Message;
import com.skydevs.tgdrive.dto.UploadFile;
import com.skydevs.tgdrive.result.PageResult;
import com.skydevs.tgdrive.result.Result;
import com.skydevs.tgdrive.service.BotService;
import com.skydevs.tgdrive.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api")
public class FileController {

    @Autowired
    private BotService botService;

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    public CompletableFuture<Result<UploadFile>> uploadFile(
        @RequestParam("file") MultipartFile multipartFile,
        HttpServletRequest request
    ) {
        return CompletableFuture.supplyAsync(() -> {
            if (multipartFile == null || multipartFile.isEmpty()) {
                return Result.error("ไฟล์ที่อัปโหลดว่างเปล่า");
            }
            return Result.success(
                botService.getUploadFile(multipartFile, request)
            );
        });
    }

    /**
     * 发送消息
     *
     * @param message
     * @return
     */
    @PostMapping("/send-message")
    public Result<String> sendMessage(@RequestBody Message message) {
        log.info("ประมวลผลการส่งข้อความ");
        if (botService.sendMessage(message.getMessage())) {
            return Result.success("ส่งข้อความสำเร็จ: " + message);
        } else {
            return Result.error("การส่งข้อความล้มเหลว");
        }
    }

    /**
     * 获取文件列表
     * @param page
     * @param size
     * @return
     */
    @SaCheckLogin
    @GetMapping("/fileList")
    public Result<PageResult> getFileList(
        @RequestParam int page,
        @RequestParam int size
    ) {
        PageResult pageResult = fileService.getFileList(page, size);
        return Result.success(pageResult);
    }

    /**
     * 更新文件url
     * @return
     */
    @SaCheckLogin
    @PutMapping("/file-url")
    public Result updateFileUrl(HttpServletRequest request) {
        log.info("อัปเดต URL ของไฟล์");
        fileService.updateUrl(request);
        return Result.success();
    }
}

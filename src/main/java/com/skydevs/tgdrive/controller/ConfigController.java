package com.skydevs.tgdrive.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.skydevs.tgdrive.dto.ConfigForm;
import com.skydevs.tgdrive.exception.ConfigFileNotFoundException;
import com.skydevs.tgdrive.result.Result;
import com.skydevs.tgdrive.service.BotService;
import com.skydevs.tgdrive.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;
    @Autowired
    private BotService botService;

    /**
     * 获取配置文件信息
     * @param name 配置文件名
     * @return ConfigForm
     */
    @SaCheckLogin
    @GetMapping()
    public Result<ConfigForm> getConfig(@RequestParam String name) {
        ConfigForm config = configService.get(name);
        if (config == null) {
            log.error("การรับการกำหนดค่าล้มเหลว โปรดตรวจสอบว่าชื่อไฟล์ผิดหรือไม่");
            throw new ConfigFileNotFoundException();
        }
        log.info("รับข้อมูลสำเร็จ");
        return Result.success(config);
    }

    /**
     * 获取所有配置文件
     * @return
     */
    @SaCheckLogin
    @GetMapping("/configs")
    public Result<List<ConfigForm>> getConfigs() {
        List<ConfigForm> configForms = configService.getForms();
        return Result.success(configForms);
    }

    /**
     * 提交配置文件
     * @param configForm
     * @return
     */
    @SaCheckLogin
    @PostMapping()
    public Result<String> submitConfig(@RequestBody ConfigForm configForm) {
        configService.save(configForm);
        log.info("บันทึกการกำหนดค่าเรียบร้อยแล้ว");
        return Result.success("บันทึกการกำหนดค่าเรียบร้อยแล้ว");
    }

    /**
     * 加载配置
     *
     * @param filename 配置文件名
     * @return
     */
    @SaCheckLogin
    @GetMapping("/{filename}")
    public Result<String> loadConfig(@PathVariable("filename") String filename) {
        botService.setBotToken(filename);
        log.info("กำลังโหลดการกำหนดค่าสำเร็จ");
        return Result.success("โหลดการกำหนดค่าเรียบร้อยแล้ว");
    }
}

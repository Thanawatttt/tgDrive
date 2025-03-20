package com.skydevs.tgdrive.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.skydevs.tgdrive.dto.AuthRequest;
import com.skydevs.tgdrive.dto.ChangePasswordRequest;
import com.skydevs.tgdrive.dto.UserLogin;
import com.skydevs.tgdrive.entity.User;
import com.skydevs.tgdrive.result.Result;
import com.skydevs.tgdrive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * เข้าสู่ระบบผู้ใช้
     * @param authRequest ชื่อผู้ใช้และรหัสผ่าน
     * @return สถานะการเข้าสู่ระบบ
     */
    @PostMapping("/login")
    public Result<UserLogin> login(@RequestBody AuthRequest authRequest) {
        // ตรวจสอบชื่อผู้ใช้และรหัสผ่าน
        User user = userService.login(authRequest);

        // ทำการล็อกอินโดยใช้ Sa-Token
        StpUtil.login(user.getId());
        UserLogin userLogin = UserLogin.builder()
            .UserId(user.getId())
            .token(StpUtil.getTokenValue())
            .role(user.getRole())
            .build();

        log.info(user.getId() + " ได้เข้าสู่ระบบ");
        return Result.success(userLogin);
    }

    /**
     * เปลี่ยนรหัสผ่าน
     * @param changePasswordRequest คำขอเปลี่ยนรหัสผ่าน
     * @return ผลลัพธ์ของการเปลี่ยนรหัสผ่าน
     */
    @PostMapping("change-password")
    public Result<String> changePassword(
        @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        long userId = StpUtil.getLoginIdAsLong();

        userService.changePassword(changePasswordRequest);

        log.info(
            userId +
            " เปลี่ยนรหัสผ่านของ " +
            changePasswordRequest.getUsername() +
            " สำเร็จ"
        );
        return Result.success("เปลี่ยนรหัสผ่านสำเร็จ");
    }
}

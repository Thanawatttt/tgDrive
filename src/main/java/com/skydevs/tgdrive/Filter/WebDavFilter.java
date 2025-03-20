// แพ็คเกจสำหรับคลาสนี้
package com.skydevs.tgdrive.Filter;

// การนำเข้าคลาสและอินเทอร์เฟซที่จำเป็น
import com.skydevs.tgdrive.utils.WebDavHttpServletRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ฟิลเตอร์สำหรับจับและแปลงคำขอ WebDav ให้เป็นคำขอที่ Spring สามารถจัดการได้
 */
@Component
@Slf4j
public class WebDavFilter implements Filter {

    @Override
    public void doFilter(
        ServletRequest servletRequest,
        ServletResponse servletResponse,
        FilterChain filterChain
    ) throws IOException, ServletException {
        // แปลงคำขอเป็น HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // รับเมธอดของคำขอ (เช่น GET, POST, PUT, DELETE)
        String method = request.getMethod();

        // ตรวจสอบว่าเมธอดเป็นคำขอ WebDav หรือไม่
        if (
            "PROPFIND".equalsIgnoreCase(method) ||
            "MKCOL".equalsIgnoreCase(method) ||
            "MOVE".equalsIgnoreCase(method) ||
            "COPY".equalsIgnoreCase(method)
        ) {
            // ล็อกการรับคำขอ WebDav
            log.info("รับคำขอ WebDav: {}", method);

            // เก็บเมธอดเดิมไว้ในแอตทริบิวต์ของคำขอ
            request.setAttribute("X-HTTP-Method-Override", method);

            // สร้าง HttpServletRequestWrapper เพื่อแปลงคำขอให้เป็น POST
            HttpServletRequest wrapper = new WebDavHttpServletRequestWrapper(
                request
            );

            // รับ URI ของคำขอ
            String uri = request.getRequestURI();

            // สร้างเส้นทางสำหรับการต่อแปรเพื่อส่งต่อคำขอ
            String forwardPath =
                "/webdav/dispatch" + uri.substring("/webdav".length());

            // ล็อกเส้นทางสำหรับการต่อแปร
            log.info("ส่งต่อคำขอไปยัง: {}", forwardPath);

            // ส่งต่อคำขอไปยังเส้นทางที่กำหนด
            request
                .getRequestDispatcher(forwardPath)
                .forward(wrapper, servletResponse);
        } else {
            // ถ้าไม่ใช่คำขอ WebDav ให้ส่งต่อคำขอไปยังฟิลเตอร์ถัดไป
            filterChain.doFilter(request, servletResponse);
        }
    }
}

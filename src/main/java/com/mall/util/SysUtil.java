package com.mall.util;

import lombok.Cleanup;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by miller on 2018/10/3
 * TODO
 */
public class SysUtil {

    public static void render(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        @Cleanup
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(mapper.writeValueAsString(object).getBytes("utf-8"));
        outputStream.flush();
    }
}

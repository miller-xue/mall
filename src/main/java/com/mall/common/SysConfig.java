package com.mall.common;

import com.mall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by miller on 2018/10/3
 * 系统配配置文件
 * Ftp
 * passwordSalt
 */
@Slf4j
public class SysConfig {

    private SysConfig(){}


    public static String ftpServerIp;

    public static String ftpUser;

    public static String ftpPass;


    public static int ftpPort;




    public static String ftpServerHttpPrefix;

    public static String passwordSalt;



    private static Properties props;

    static {
        String fileName = "mall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(SysConfig.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
            ftpServerIp = getProperty("ftp.server.ip");
            ftpUser = getProperty("ftp.user");
            ftpPass = getProperty("ftp.pass");
            ftpPort = Integer.valueOf(getProperty("ftp.port"));
            ftpServerHttpPrefix = getProperty("ftp.server.http.prefix");
            passwordSalt = getProperty("password.salt");
            props.clear();
        } catch (IOException e) {
            log.error("配置文件读取异常",e);
        }
    }

    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }
}

package com.mall.util;

import com.mall.common.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by miller on 2018/10/3a
 * TODO 异常的抛出有很大的问题
 */

@Slf4j
public class FtpUtil {

    private static FTPClient ftpClient;

    public static boolean uploadFile(List<File> fileList) throws IOException {
        log.info("开始连接ftp服务器");
        boolean result = uploadFile("img", fileList);
        log.info("结束上传,上传结果:{}", result);
        return result;
    }

    private static boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        FileInputStream fis = null;
        // 连接Ftp服务器
        if (open()) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();

                for (File file : fileList) {
                    fis = new FileInputStream(file);
                    ftpClient.storeFile(file.getName(), fis);
                }
                return true;
            } catch (IOException e) {
                log.error("上传文件异常", e);
            }finally {
                fis.close();
                close();
            }
        }
        return false;
    }

    /**
     * 连接Ftp服务器
     *
     * @return
     */
    private static boolean open() {
        if (ftpClient != null && ftpClient.isConnected()) {
            return true;
        }
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(SysConfig.ftpServerIp, SysConfig.ftpPort);
            ftpClient.login(SysConfig.ftpUser, SysConfig.ftpPass);
            // 判断是否开启成功
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                close();
                log.error("FTP server refused connection.");
            }
            return true;
        } catch (Exception e) {
            close();
            log.error("连接Ftp服务器失败", e);
            return false;
        }

    }

    private static void close() {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            log.error("成功关闭连接，服务器ip:" + SysConfig.ftpServerIp + ", 端口:" + SysConfig.ftpPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

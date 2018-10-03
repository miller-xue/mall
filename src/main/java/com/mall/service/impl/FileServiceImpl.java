package com.mall.service.impl;

import com.google.common.collect.Lists;
import com.mall.service.IFileService;
import com.mall.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by miller on 2018/10/3
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {

    public String upload(MultipartFile file, String path) {
        // 1.拿去原始文件名
        String fileName = file.getOriginalFilename();
        // 2.拿到文件扩展屏
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);

        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

        File targetFile = new File(getFileDir(path), uploadFileName);
        try {
            file.transferTo(targetFile);
            // 文件上传成功

            FtpUtil.uploadFile(Lists.newArrayList(targetFile));
            // 已经上传到ftp服务器上
            // 删除文件
            targetFile.delete();
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }


    private File getFileDir(String path){
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        return fileDir;
    }

}

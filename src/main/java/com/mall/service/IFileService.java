package com.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by miller on 2018/10/3
 */
public interface IFileService {

    /**
     * 上传文件
     * @param file 文件对象
     * @param path 路径
     * @return
     */
    String upload(MultipartFile file, String path);
}

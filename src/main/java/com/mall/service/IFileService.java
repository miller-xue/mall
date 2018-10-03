package com.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by miller on 2018/10/3
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}

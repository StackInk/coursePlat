package com.bywlstudio.oss.service;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: zl
 * @Date: Create in 2021/4/8 14:26
 * @Description:
 */
public interface IOssFileService {
    String uploadFile(MultipartFile file) throws FileUploadException;
}

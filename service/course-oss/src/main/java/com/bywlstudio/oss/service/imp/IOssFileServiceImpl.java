package com.bywlstudio.oss.service.imp;

import com.aliyun.oss.OSS;
import com.bywlstudio.common.constant.CourseCode;
import com.bywlstudio.common.exception.CourseException;
import com.bywlstudio.oss.config.OssInfo;
import com.bywlstudio.oss.service.IOssFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @Author: zl
 * @Date: Create in 2021/4/8 14:38
 * @Description:
 */
@Service("ossFileService")
@Slf4j
public class IOssFileServiceImpl implements IOssFileService {

    @Resource
    private OSS ossClient ;

    @Override
    public String uploadFile(MultipartFile file) throws FileUploadException {
        //作为文件分类
        String currentDate = LocalDate.now().toString();
        //文件名字
        String fileName = currentDate+"/"+ UUID.randomUUID().toString().replace("-","")+"-"+file.getOriginalFilename();
        log.info("上传文件名字为：{},桶的名字为:{}",fileName,OssInfo.OSS_BUCKET_NAME);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            ossClient.putObject(OssInfo.OSS_BUCKET_NAME,fileName,inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadException(CourseCode.FileNotFound.getMessage(),e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getFileUrl(fileName);
    }

    private String getFileUrl(String fileName){
        return "https://"+OssInfo.OSS_BUCKET_NAME+"."+OssInfo.OSS_ENDPOINT+"/"+fileName ;
    }
}

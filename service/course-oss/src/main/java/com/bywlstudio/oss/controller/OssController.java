package com.bywlstudio.oss.controller;

import com.bywlstudio.common.util.R;
import com.bywlstudio.oss.service.IOssFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: zl
 * @Date: Create in 2021/4/8 14:20
 * @Description:
 */
@RestController
@Slf4j
@Api("OSS上传接口")
public class OssController {

    @Resource
    private IOssFileService ossFileService ;

    @PostMapping
    @ApiOperation("上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "文件",required = true),
    })
    public R uploadFile(MultipartFile file) throws FileUploadException {
        String url = ossFileService.uploadFile(file);
        return R.ok().data("url",url);
    }

}

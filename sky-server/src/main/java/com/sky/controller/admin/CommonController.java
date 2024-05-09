package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: CommonController
 * @author: 姬紫衣
 * @description:通用接口
 * @date: 2024/5/9 15:09
 * @version: 1.0
 */

@RestController
@RequestMapping("/admin/common")
@Api("通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file)
    {
        log.info("文件上传 {}" ,file);
        //使用UUID防止重命名
        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名后缀
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名
           String objectName = UUID.randomUUID().toString() + substring;

           //文件的请求路径
            String uploadPath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(uploadPath);
        } catch (IOException e) {
            log.error("文件上传失败 {}" , e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}

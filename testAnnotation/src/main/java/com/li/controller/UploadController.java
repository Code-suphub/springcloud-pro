package com.li.controller;

import com.li.service.UploadService;
import com.li.util.FtpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

@RestController
public class UploadController {
    @Resource
    private UploadService uploadService;

    //    @ApiOperation(value = "上传文件接口")
    @PostMapping(value = "/file/upload")
    public void fileUpload(MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        FtpUtils.sshSftp(bytes, "1111.jpg");
    }

    //    @ApiOperation(value = "上传图片接口")
    @GetMapping(value = "/file/download")
    public void ImageUpload(HttpServletResponse response) throws Exception {
        String fileName = "1111.jpg";
        response.reset();
        // 设置文件的MIME类型
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 将字节数组写入响应的输出流
        OutputStream os = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        byte[] bytes = FtpUtils.sshSftpDownLoad(fileName);
        bos.write(bytes);
        bos.flush();

        // 关闭输出流
        bos.close();

    }
}

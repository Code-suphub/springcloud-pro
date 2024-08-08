package com.li.service.impl;

import com.li.constant.Constant;
import com.li.pojo.vo.BaseVO;
import com.li.pojo.vo.FileVO;
import com.li.service.UploadService;
import com.li.util.QiNiuUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

@Slf4j
@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public BaseVO upload(MultipartFile file, String fileType) throws Exception {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            log.error("传入的文件名不能为空");
            return new BaseVO(false, "10001", "1001");
        }
        if (!this.validateFileName(fileName)) {
            log.error("文件名应仅包含汉字、字母、数字、下划线和点号");
            return new BaseVO(false, "10002", "1002");
        }
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        String url = "";
        if (fileType.equals(Constant.IMAGE)) {
            url = new QiNiuUtil().upload(fileInputStream, Constant.IMAGE);
        } else if (fileType.equals(Constant.FILE)) {
            url = new QiNiuUtil().upload(fileInputStream, Constant.FILE);
        }
        FileVO fileVO = new FileVO();
        fileVO.setDownloadUrl(url);
        return fileVO;
    }

    /**
     * 验证文件名称：仅包含 汉字、字母、数字、下划线和点号
     *
     * @param fileName 文件名称
     * @return 返回true表示符合要求
     */
    private boolean validateFileName(String fileName) {
        String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5_\\.]+$";
        return fileName.matches(regex);
    }
}

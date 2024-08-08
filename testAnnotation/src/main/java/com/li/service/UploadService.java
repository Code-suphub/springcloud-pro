package com.li.service;

import com.li.pojo.vo.BaseVO;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    BaseVO upload(MultipartFile file, String fileType) throws Exception;
}

package com.a88.controller;

import com.a88.Pojo.result;
import com.a88.utils.uploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class uploadController {
    @Autowired
    private uploadFileUtil sta;

    @PostMapping("/upload")
    public result upload(MultipartFile image) throws IOException {
            log.info("fileUpload, fileName: {}" ,image.getOriginalFilename());

        String url = sta.uploadFile(image);

        log.info("file upload completed, access url is: {}", url);

        return result.success(url);
    }


}

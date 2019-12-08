package com.sanriyue.mall.manage.controller;


import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
public class FileUploadController {
    //TODO http://localhost:8082/fileUpload
    @Value("${fileServer.url}")
    private String fileUrl;

    @RequestMapping("fileUpload")
    public String fileUpload(MultipartFile file)throws IOException, MyException {
        String imgUrl = fileUrl;
        if (file!=null){
            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile);

            TrackerClient trackerClient = new TrackerClient();
            TrackerServer connection = trackerClient.getConnection();

            StorageClient storageClient = new StorageClient(connection,null);
            //String orginalFilename="d://E-commerce//shan.jpg";
            //以下得到的只是文件名并不是所需要的路径名！！
            String originalFilename = file.getOriginalFilename();
            //用工具类获取文件名后缀
            String last = StringUtils.substringAfterLast(originalFilename, ".");
            //调用其他方法来上传文件
            //String[] jpgs = (String[]) storageClient.upload_file(orginalFilename, "jpg", null);
            String[] upload_file = storageClient.upload_file(file.getBytes(), last, null);
            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                imgUrl += "/" + path;
            }
        }

        return imgUrl;
    }
}

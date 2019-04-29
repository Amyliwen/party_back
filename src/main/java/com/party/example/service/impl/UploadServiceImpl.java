package com.party.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.party.example.service.UploadService;
import com.party.example.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
@Service
public class UploadServiceImpl implements UploadService {
    @Value("9520")
    private String serverPort;

    @Value("D:/temp/")
    private String fileUploadPath;
    @Override
    public JSONObject uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
//        FileUtils.storeFile(file, serverPort, fileUploadPath);
        JSONObject object = new JSONObject();
        return object;
    }
}

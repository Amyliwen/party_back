package com.party.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.party.example.bean.Result;
import com.party.example.service.UploadService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author liwen
 * 文件上传
 */
@RestController
@RequestMapping("/file")
public class UploadDownloadController {
//    @Autowired
//    UploadService uploadService;
//    /**
//     *
//     * @param picture 要上传的文件
//     * @return
//     */
//    @RequestMapping("/upload")
//    public JSONObject  upload(@RequestParam("photoURL")MultipartFile picture, HttpServletRequest request) throws IOException {
//        JSONObject info = new JSONObject();
////        Map<String, String> map = new HashMap<>();
//        if (StringUtils.isEmpty(picture)) {
//
//            map.put("message", "文件为空请重新上传");
//        }
//        map = uploadService.uploadFile(picture, request);
//        return ;
//    }
}

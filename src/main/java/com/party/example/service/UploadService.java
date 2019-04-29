package com.party.example.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
public interface UploadService {
    Map uploadFile(MultipartFile file1, HttpServletRequest req) throws IOException;
}

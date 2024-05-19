package com.example.crud.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    public byte[] processImage(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}

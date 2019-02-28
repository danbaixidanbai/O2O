package com.util;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MultipartUtil {

    public static MultipartFile getMultipatrFile(File file){
        MultipartFile multipartFile=null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            multipartFile = new MockMultipartFile(file.getName(),inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartFile;
    }
}

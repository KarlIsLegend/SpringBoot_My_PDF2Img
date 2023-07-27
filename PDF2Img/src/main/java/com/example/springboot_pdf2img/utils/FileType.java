package com.example.springboot_pdf2img.utils;

/**
 * @author zhj
 * @date 2023/7/24
 **/
public class FileType {
    public String fileType;
    public static String typeFromFileName(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileType.toLowerCase();
    }
}

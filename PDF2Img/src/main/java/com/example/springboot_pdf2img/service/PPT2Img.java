package com.example.springboot_pdf2img.service;

import java.io.File;

/**
 * @author zhj
 * @date 2023/7/26
 **/
public interface PPT2Img {
    String trans2Img(File pdfPath, File savePath);
}

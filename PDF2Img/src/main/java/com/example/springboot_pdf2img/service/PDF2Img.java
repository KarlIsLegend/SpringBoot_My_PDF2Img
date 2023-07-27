package com.example.springboot_pdf2img.service;

import java.io.File;

/**
 * @author zhj
 * @date 2023/7/26
 **/
public interface PDF2Img {
    void pdfTrans2Img(File pdfFile, File savePath);
}

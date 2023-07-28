package com.example.springboot_pdf2img.controller;

import com.example.springboot_pdf2img.service.PDF2Img;
import com.example.springboot_pdf2img.service.PPT2Img;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * @author zhj
 * @date 2023/7/26
 **/
@RestController
public class TransController {
    @Value("${cacheRoot}")
    public String root;
    @Value("${department.d1}")
    public String d1;
    @Value("${department.d2}")
    public String d2;
    @Value("${department.d3}")
    public String d3;
    @Value("${department.d4}")
    public String d4;
    private final Logger logger = LoggerFactory.getLogger(TransController.class);
    @Autowired
    private PDF2Img pdf2Img;
    @Autowired
    private PPT2Img ppt2Img;

    @RequestMapping("update")
    public String update() {
        LocalDate date = LocalDate.now();//获取本地时间
        //资源文件路径
        File[] sourcePath = new File[4];
        sourcePath[0] = new File(root + File.separator + d1 + File.separator + date);
        sourcePath[1] = new File(root + File.separator + d2 + File.separator + date);
        sourcePath[2] = new File(root + File.separator + d3 + File.separator + date);
        sourcePath[3] = new File(root + File.separator + d4 + File.separator + date);
        for (int i = 0; i < 4; i++) {//检查
            if (!sourcePath[i].exists() && !sourcePath[i].mkdirs()) {
                logger.error("创建转换文件【{}】目录失败，请检查目录权限！", sourcePath[i]);
            }

        }
        //图片保存路径
        File[] imgSavePath = new File[4];
        imgSavePath[0] = new File(root + File.separator + "cache" + d1 + "images");
        imgSavePath[1] = new File(root + File.separator + "cache" + d2 + "images");
        imgSavePath[2] = new File(root + File.separator + "cache" + d3 + "images");
        imgSavePath[3] = new File(root + File.separator + "cache" + d4 + "images");
        existsPath(imgSavePath);//检查路径，没有则创建
        //excel保存路径
        File[] excelSavePath = new File[4];
        excelSavePath[0] = new File(root + File.separator + "cache" + d1 + "excel");
        excelSavePath[1] = new File(root + File.separator + "cache" + d2 + "excel");
        excelSavePath[2] = new File(root + File.separator + "cache" + d3 + "excel");
        excelSavePath[3] = new File(root + File.separator + "cache" + d4 + "excel");
        existsPath(excelSavePath);//检查路径，没有则创建
        try {//清除cache下的缓存文件
            FileUtils.cleanDirectory(new File(root + File.separator + "cache"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //处理资源文件
        for (int i = 0; i < 4; i++) {
            fileHandler(sourcePath[i], imgSavePath[i], excelSavePath[i]);
        }
        return getJson();
    }
    @GetMapping("/")
    public String index(){
        return getJson();
    }

    private String getJson() {

        return null;
    }

    /*
      文件处理函数
     */
    private void fileHandler(File sourcePath, File imgSavePath, File excelSavePath) {
        File[] files = sourcePath.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith(".pdf")) {
                pdf2Img.pdfTrans2Img(file, imgSavePath);
            } else if (fileName.endsWith(".pptx")) {
                ppt2Img.trans2Img(file, imgSavePath);
            } else if (fileName.endsWith(".excel")) {
                changeEndsName(file, excelSavePath);
            }
        }
    }

    /*更改文件后缀名*/
    private void changeEndsName(File file, File savePath) {
        String oldName = file.getName();
        String newName = oldName + ".html";
        File newFile = new File(savePath, newName);
        if (file.renameTo(newFile)) {
            System.out.println("文件名已更改: " + oldName + " -> " + newName);
        } else {
            System.out.println("无法更改文件名: " + oldName);
        }
    }

    private void existsPath(File[] path) {//检查路径，没有则创建
        for (File file : path) {//检查
            if (!file.exists() && !file.mkdirs()) {
                logger.error("创建转换文件【{}】目录失败，请检查目录权限！", file);
            }
        }
    }

}

package com.example.springboot_pdf2img.controller;

import com.example.springboot_pdf2img.service.FilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhj
 * @date 2023/7/23
 **/
@RestController
public class HelloController {
    @Autowired
    public FilePath filePath;

    @RequestMapping("/hello")
    public String hello() {
        return "test";
    }
}

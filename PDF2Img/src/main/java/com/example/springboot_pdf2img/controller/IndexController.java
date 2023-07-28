package com.example.springboot_pdf2img.controller;

import com.example.springboot_pdf2img.service.FilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author zhj
 * @date 2023/7/25
 **/
@Controller
public class IndexController {

    @Autowired
    public FilePath filePath;

    @GetMapping("/1")
    public String index(ModelMap modelMap){
        LocalDate date = LocalDate.now();
        modelMap.addAttribute("msg","Hi,SB是Spring Boot...哦");
        modelMap.addAttribute("d1",filePath.d1);
        modelMap.addAttribute("date",date);

        return "index";
    }
}

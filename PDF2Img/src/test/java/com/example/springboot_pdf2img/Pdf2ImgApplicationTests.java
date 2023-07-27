package com.example.springboot_pdf2img;

import com.example.springboot_pdf2img.service.FilePath;
import com.example.springboot_pdf2img.service.PDF2Img;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest

class Pdf2ImgApplicationTests {
    @Autowired

    private FilePath filePath;
    @Autowired
    private PDF2Img pdf2Img;

    @Test
    void contextLoads() {
        File pdf = new File("D:\\code\\test\\document_name01\\2023-07-21\\JDBC.pdf");
        File save = new File("D:\\code\\test\\source\\image");

        System.out.println(filePath.d1);
        pdf2Img.pdfTrans2Img(pdf,save);
    }

}

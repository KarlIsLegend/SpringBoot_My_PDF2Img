package com.example.springboot_pdf2img.service.impl;

import com.example.springboot_pdf2img.service.OfficeToPdfService;
import com.example.springboot_pdf2img.service.PDF2Img;
import com.example.springboot_pdf2img.service.PPT2Img;
import org.jodconverter.core.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author zhj
 * @date 2023/7/26
 **/
@Service
public class PPT2ImgImpl implements PPT2Img {
    private final PDF2Img pdf2Img;
    private final OfficeToPdfService officeToPdfService;

    public PPT2ImgImpl(PDF2Img pdf2Img, OfficeToPdfService officeToPdfService) {
        this.pdf2Img = pdf2Img;
        this.officeToPdfService = officeToPdfService;
    }

    @Override
    public void trans2Img(File ppt, File savePath) {
        File pdf;
        try {
            pdf = officeToPdfService.office2pdf(ppt.getName(), null);
        } catch (OfficeException e) {
            throw new RuntimeException(e);
        }
        pdf2Img.pdfTrans2Img(pdf, savePath);
    }
}

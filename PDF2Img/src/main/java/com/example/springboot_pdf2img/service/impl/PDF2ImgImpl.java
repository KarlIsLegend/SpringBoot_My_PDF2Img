package com.example.springboot_pdf2img.service.impl;

import com.example.springboot_pdf2img.config.ConfigConstants;
import com.example.springboot_pdf2img.service.PDF2Img;
import com.example.springboot_pdf2img.service.PDFToIMG;
import com.example.springboot_pdf2img.service.cache.NotResourceCache;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author zhj
 * @date 2023/7/26
 **/
@Service
public class PDF2ImgImpl implements PDF2Img {
    private static final String PDF2JPG_IMAGE_FORMAT = ".jpg";
    public String filePassword = null;
    private final Logger logger = LoggerFactory.getLogger(PDFToIMG.class);
    @SneakyThrows
    @Override
    public void pdfTrans2Img(File pdfFile, File savePath) {
        PDDocument doc = null;
        try {
//            File pdfFile = new File(pdfPath);
            if (!pdfFile.exists()) {
                return;
            }
            doc = PDDocument.load(pdfFile, filePassword);
            doc.setResourceCache(new NotResourceCache());
            int pageCount = doc.getNumberOfPages();
            PDFRenderer pdfRenderer = new PDFRenderer(doc);
            if (!savePath.exists() && !savePath.mkdirs()) {
                logger.error("创建转换文件【{}】目录失败，请检查目录权限！", savePath);
            }
            File[] files = savePath.listFiles();
            int fileNum = 0;
            if (files != null) {
                fileNum = files.length;
                System.out.println("文件夹下文件数量：" + fileNum);
            } else {
                System.out.println("该文件夹为空！");
            }
            String imageFilePath;
            for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                imageFilePath = savePath + File.separator + (pageIndex + fileNum) + PDF2JPG_IMAGE_FORMAT;
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, ConfigConstants.getPdf2JpgDpi(), ImageType.RGB);
                ImageIOUtil.writeImage(image, imageFilePath, ConfigConstants.getPdf2JpgDpi());
//                String imageUrl = pdfName + "/" + pageIndex;
//                imageUrls.add(imageUrl);
            }
//            try {
//                pdfReader = new PdfReader(pdfFile.getName());   //读取PDF文件
//            } catch (Exception e) {  //获取异常方法 判断是否有加密字符串
//                Throwable[] throwableArray = ExceptionUtils.getThrowables(e);
//                for (Throwable throwable : throwableArray) {
//                    if (throwable instanceof IOException || throwable instanceof EncryptedDocumentException) {
//                        e.getMessage();
//                    }
//                }
//                logger.error("Convert pdf exception, pdfFilePath：{}", pdfFile, e);
//            } finally {
//                if (pdfReader != null) {   //关闭
//                    pdfReader.close();
//                }
//            }
            //判断是否加密文件 加密文件不缓存
//            if (!PDF_PASSWORD_MSG.equals(pdfPassword)) {
//                this.addPdf2jpgCache(pdfFilePath, pageCount);
//            }
        } catch (IOException e) {
            logger.error("Convert pdf to jpg exception, pdfFilePath：{}", pdfFile, e);
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (doc != null) {   //关闭
                doc.close();
            }
        }
    }


}

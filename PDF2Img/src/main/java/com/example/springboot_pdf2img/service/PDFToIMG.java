package com.example.springboot_pdf2img.service;

import com.example.springboot_pdf2img.config.ConfigConstants;
import com.example.springboot_pdf2img.service.cache.CacheService;
import com.example.springboot_pdf2img.service.cache.NotResourceCache;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.poi.EncryptedDocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhj
 * @date 2023/7/24
 **/
@Component
public class PDFToIMG {
    private static final String PDF2JPG_IMAGE_FORMAT = ".jpg";
    private static final String PDF_PASSWORD_MSG = "password";
    private final Logger logger = LoggerFactory.getLogger(PDFToIMG.class);
    private final String fileDir = ConfigConstants.getFileDir();
//    private final CacheService cacheService;

//    public PDFToIMG(CacheService cacheService) {
//        this.cacheService = cacheService;
//    }

    /**
     * 获取缓存中的 pdf 转换成 jpg 图片集
     *
     * @param pdfFilePath pdf文件路径
     * @param pdfName     pdf文件名称
     * @return 图片访问集合
     */
//    private List<String> loadPdf2jpgCache(String pdfFilePath, String pdfName) {
//        List<String> imageUrls = new ArrayList<>();
//        Integer imageCount = this.getPdf2jpgCache(pdfFilePath);
//        if (Objects.isNull(imageCount)) {
//            return imageUrls;
//        }
//        IntStream.range(0, imageCount).forEach(i -> {
//            String imageUrl = pdfName;
//            imageUrls.add(imageUrl);
//        });
//        return imageUrls;
//    }

    /**
     * @param key pdf本地路径
     * @return 已将pdf转换成图片的图片本地相对路径
     */
//    public Integer getPdf2jpgCache(String key) {
//        return cacheService.getPdfImageCache(key);
//    }

    /**
     * pdf文件转换成jpg图片集
     *
     * @param pdfFilePath pdf文件路径
     * @param pdfName     pdf文件名称
     * @return 图片访问集合
     */
    public List<String> pdf2jpg(String pdfFilePath, String pdfName, String saveFolder) throws Exception {
        String filePassword = null;
        PDDocument doc = null;
        PdfReader pdfReader = null;
//        if (!forceUpdatedCache) {
//            List<String> cacheResult = this.loadPdf2jpgCache(pdfFilePath, pdfName);
//            if (!CollectionUtils.isEmpty(cacheResult)) {
//                return cacheResult;
//            }
//        }
        List<String> imageUrls = new ArrayList<>();
        try {
            File pdfFile = new File(pdfFilePath);
            if (!pdfFile.exists()) {
                return null;
            }
            doc = PDDocument.load(pdfFile, filePassword);
            doc.setResourceCache(new NotResourceCache());
            int pageCount = doc.getNumberOfPages();
            PDFRenderer pdfRenderer = new PDFRenderer(doc);
            int index = pdfFilePath.lastIndexOf(".");
            File path = new File(saveFolder);
            if (!path.exists() && !path.mkdirs()) {
                logger.error("创建转换文件【{}】目录失败，请检查目录权限！", saveFolder);
            }
            File[] files = path.listFiles();
            int fileNum = 0;
            if (files != null) {
                fileNum = files.length;
                System.out.println("文件夹下文件数量：" + fileNum);
            } else {
                System.out.println("该文件夹为空！");
            }
            String imageFilePath;
            for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                imageFilePath = saveFolder + File.separator + (pageIndex + fileNum) + PDF2JPG_IMAGE_FORMAT;
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, ConfigConstants.getPdf2JpgDpi(), ImageType.RGB);
                ImageIOUtil.writeImage(image, imageFilePath, ConfigConstants.getPdf2JpgDpi());
                String imageUrl = pdfName + "/" + pageIndex;
                imageUrls.add(imageUrl);
            }
            try {
                pdfReader = new PdfReader(pdfFilePath);   //读取PDF文件
            } catch (Exception e) {  //获取异常方法 判断是否有加密字符串
                Throwable[] throwableArray = ExceptionUtils.getThrowables(e);
                for (Throwable throwable : throwableArray) {
                    if (throwable instanceof IOException || throwable instanceof EncryptedDocumentException) {
                        if (e.getMessage().toLowerCase().contains(PDF_PASSWORD_MSG)) {
//                            pdfPassword = PDF_PASSWORD_MSG;
                        }
                    }
                }
                logger.error("Convert pdf exception, pdfFilePath：{}", pdfFilePath, e);
            } finally {
                if (pdfReader != null) {   //关闭
                    pdfReader.close();
                }
            }
            //判断是否加密文件 加密文件不缓存
//            if (!PDF_PASSWORD_MSG.equals(pdfPassword)) {
//                this.addPdf2jpgCache(pdfFilePath, pageCount);
//            }
        } catch (IOException e) {
            logger.error("Convert pdf to jpg exception, pdfFilePath：{}", pdfFilePath, e);
            throw new Exception(e);
        } finally {
            if (doc != null) {   //关闭
                doc.close();
            }
        }
        return imageUrls;
    }

}

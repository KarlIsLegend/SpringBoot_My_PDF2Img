package com.example.springboot_pdf2img.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author zhj
 * @date 2023/7/25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@PropertySource(value = "classpath:application.properties")
public class FilePath {
    @Value("${d1}")
    public String d1;
    @Value("${d2}")
    public String d2;


}

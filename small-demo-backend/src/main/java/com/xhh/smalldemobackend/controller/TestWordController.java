package com.xhh.smalldemobackend.controller;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@RestController
public class TestWordController {
    
    @GetMapping("/exportWord")
    public void exportWord(HttpServletResponse response) throws Exception {
        
        response.setContentType("application/msword");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("测试work文本", "UTF-8").replaceAll("\\+", "%20");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".doc");
        
        // 文档对象
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
    }
}

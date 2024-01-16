package com.xhh.smalldemobackend.service.registry;

import org.springframework.stereotype.Service;

@Service("excel")
public class ExcelFileParser implements FileParser{
    @Override
    public void parse() {
        System.out.println("excel file parse");
    }
}

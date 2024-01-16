package com.xhh.smalldemobackend.service.registry;

import org.springframework.stereotype.Service;

@Service("csv")
public class CsvFileParser implements FileParser{
    @Override
    public void parse() {
        System.out.println("csv file parse");
    }
}

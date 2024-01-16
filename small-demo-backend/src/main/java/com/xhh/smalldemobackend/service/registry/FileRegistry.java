package com.xhh.smalldemobackend.service.registry;

import org.springframework.stereotype.Service;

@Service
public class FileRegistry {
    
    public void cover(String type) {
        FileParser bean = FileParserRegistry.getBean(type, FileParser.class);
        bean.parse();
    }
}

package com.xhh.smalldemobackend.service.registry;

import org.springframework.stereotype.Service;

@Service(value = "json")
public class JsonFileParser implements FileParser{
    @Override
    public void parse() {
        System.out.println("json file parse");
    }
}

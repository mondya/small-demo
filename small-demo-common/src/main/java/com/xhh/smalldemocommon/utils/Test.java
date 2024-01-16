package com.xhh.smalldemocommon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File("d:/108键盘.txt");
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bytes = new byte[(int) file.length()];

//        fileOutputStream.write("bytes".getBytes());
//        fileOutputStream.close();
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(bytes);
        System.out.println("bytes:" + Arrays.toString(bytes));
        fileInputStream.close();
    }
}

package org.yeepay.core.common.annotation;

import java.io.*;

public class JsonDemo {

    public static void main(String[] args) {
      readFileByLines("D:\\BaiduNetdiskDownload\\qcc_9300w_1.json");
    }

    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
// 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
// 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}

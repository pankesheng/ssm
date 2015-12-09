package com.ssm.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class WriteFile {
	public static void contentToTxt(String filePath, String content) {  
        String str = new String(); //原有txt内容  
        String s1 = new String();//内容更新  
        try {  
            File f = new File(filePath);
            if(!f.exists()){
            	f.mkdirs();
            }
            if (f.exists()) {  
            	f.delete();
            }  
            f.createNewFile();
            System.out.println("查看文件："+filePath);
            BufferedReader input = new BufferedReader(new FileReader(f));  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }  
            System.out.println(s1);  
            input.close();  
            s1 += content;  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
}

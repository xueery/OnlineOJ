package common;

import java.io.*;

/**
 * @author:wangxue
 * @date:2020/5/16 20:03
 */
public class FileUtil {
    //读文件
    public static String read(String filePath){
        try(FileReader fileReader=new FileReader(filePath);
            BufferedReader bufferedReader=new BufferedReader(fileReader)){
            StringBuilder sb=new StringBuilder();
            String string="";
            while((string=bufferedReader.readLine())!=null){
                sb.append(string);
            }
            return sb.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 写文件
    public static void write(String filePath,String content){
        try(FileWriter fileWriter=new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

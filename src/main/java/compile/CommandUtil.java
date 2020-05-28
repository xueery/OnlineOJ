package compile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author:wangxue
 * @date:2020/5/16 17:54
 */
public class CommandUtil {
    /**
     *
     * @param cmd 表示执行的命令
     * @param stdoutFile 用于将标准输出写入文件中
     * @param stderrFile 用于将标准错误写入文件中
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static int run(String cmd,String stdoutFile,String stderrFile) throws IOException, InterruptedException {
        // 获取一个Runtime对象
        Runtime runtime=Runtime.getRuntime();
        //  exec 方法用于创建一个子进程并且执行命令
        Process process=runtime.exec(cmd);
        if(stdoutFile!=null) {
            // 获取子进程的输出
            InputStream stdoutFrom=process.getInputStream();
            // 获取写入文件的输出流
            OutputStream stdoutTo=new FileOutputStream(stdoutFile);
            int ch=-1;
            while((ch=stdoutFrom.read())!=-1){
                stdoutTo.write(ch);
            }
        }
        if(stderrFile!=null){
            InputStream stderrFrom=process.getErrorStream();
            OutputStream stderrTo=new FileOutputStream(stderrFile);
            int ch=-1;
            while((ch=stderrFrom.read())!=-1){
                stderrTo.write(ch);
            }
        }
        // 实现等待，父进程对子进程进行等待
        int exitCode=process.waitFor();
        // 返回一个状态码
        return exitCode;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        run("javac","d:/stoutFile.txt","d:/sterrFile.txt");
    }
}

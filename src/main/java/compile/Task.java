package compile;

import common.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author:wangxue
 * @date:2020/5/16 19:53
 */
public class Task {
    // 编译运行的过程中依赖了一些临时文件，约定一些关于这些临时文件的名字
    //临时文件都放到tmp目录中
    private static final String WORK_DIR="./tmp/";
    // 要编译代码的类名
    private static final String CLASS ="Solution";
    // 要编译代码对应的文件名，需要和类名一致
    private static final String CODE=WORK_DIR+"Solution.java";
    // 标准输入对应的文件
    private static final String STDIN=WORK_DIR+"stdin.txt";
    // 标准输出对应的文件（编译执行的代码的结果保存到这个文件中）
    private static final String STDOUT=WORK_DIR+"stdout.txt";
    // 标准错误对应的文件（编译执行的代码的结果保存到这个文件中）
    private static final String STDERR=WORK_DIR+"stderr.txt";
    // 编译错误对应的文件（编译出错时的具体原因）
    private static final String COMPILE_ERROR=WORK_DIR+"compile_error.txt";

    public Answer compileAndRun(Question question) throws IOException, InterruptedException {
        Answer answer=new Answer();
        // 先创建好存放临时文件的目录
        File workDir=new File(WORK_DIR);
        if(!workDir.exists()){
            workDir.mkdirs();
        }
        // 根据 Question 对象，构造需要的一些临时文件
        FileUtil.write(CODE,question.getCode());
        FileUtil.write(STDIN,question.getStdin());
        // 构造编译命令，并执行
        // -d 表示将生成的字节码文件放在指定目录下
        // 编译命令形如 javac encoding -utf8 ./tmp/Soluton.java -d ./tmp/
        String cmd=String.format("javac -encoding utf-8 %s -d %s",CODE,WORK_DIR);
        System.out.println("编译命令"+cmd);
        CommandUtil.run(cmd,null,COMPILE_ERROR);
        String compileError=FileUtil.read(COMPILE_ERROR);
        if(!("".equals(compileError))){
            System.out.println("编译出错");
            answer.setError(1);
            answer.setReason(compileError);
            return answer;
        }
        // 构造执行命令，并执行  -classpath 指定类加载路径
        // 命令形式为 java -calsspath ./tmp/  Solution
        cmd=String.format("java -classpath %s %s", WORK_DIR,CLASS);
        System.out.println("运行命令"+cmd);
        CommandUtil.run(cmd,STDOUT,STDERR);
        String stdError=FileUtil.read(STDERR);
        if(!"".equals(stdError)){
            System.out.println("运行出错");
            answer.setError(2);
            answer.setReason(stdError);
            answer.setStderr(stdError);
            return answer;
        }
        // 将结果保存在answer中
        answer.setError(0);
        answer.setStdout(FileUtil.read(STDOUT));
        return answer;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Question question=new Question();
        question.setCode(
                "public class Solution{\n" +
                "    public static void main(String[] args){\n"+
                "           System.out.println(\"hello\");\n"+
                "    }\n"+
                "}\n"
        );
        question.setStdin("");
        Task task=new Task();
        Answer answer=task.compileAndRun(question);
        System.out.println(answer);
    }
}

package compile;

/**
 * @author:wangxue
 * @date:2020/5/16 19:32
 */
// 编译和执行的代码
public class Question {
    // 要编译和执行的代码
    private String code;
    //  执行时标准输入要输入的内容
    private String stdin;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStdin() {
        return stdin;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }
}

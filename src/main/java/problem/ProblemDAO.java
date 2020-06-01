package problem;

import common.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:wangxue
 * @date:2020/5/17 16:14
 */
// 数据访问层
public class ProblemDAO {
    // 获取所有题目信息
    public List<Problem> selectAll(){
        List<Problem> results=new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            connection=DBUtil.getConnection();
            String sql="select * from oj_table";
            statement=connection.prepareStatement(sql);
            resultSet=statement.executeQuery();
            while(resultSet.next()){
                Problem problem=new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
//                problem.setDescription(resultSet.getString("description"));
//                problem.setTemplateCode(resultSet.getString("templateCode"));
//                problem.setTestCode(resultSet.getString("testCode"));
                results.add(problem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return results;
    }

    // 获取 指定id 的题目信息
    public Problem selectOnne(int id){
        // 1. 建立数据库连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL
        String sql = "select * from oj_table where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历结果集
            if (resultSet.next()) {
                Problem problem = new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
                problem.setDescription(resultSet.getString("description"));
                problem.setTemplateCode(resultSet.getString("templateCode"));
                problem.setTestCode(resultSet.getString("testCode"));
                return problem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. 释放关闭资源
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    // 只能增一个题目到数据库
    public void insert(Problem problem){
        // 1.获取数据库连接
        Connection connection= DBUtil.getConnection();
        PreparedStatement statement=null;
        // 2.拼装 SQL 语句
        String sql="insert into oj_table values(null,?,?,?,?,?)";
        try {
            statement=connection.prepareStatement(sql);
            statement.setString(1,problem.getTitle());
            statement.setString(2,problem.getLevel());
            statement.setString(3,problem.getDescription());
            statement.setString(4,problem.getTemplateCode());
            statement.setString(5,problem.getTestCode());
            System.out.println("insert:"+statement);
            // 3.执行 SQL 语句
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //4.关闭释放相关资源
            DBUtil.close(connection,statement,null);
        }
    }


    // 删除指定题目信息
    public void delete(int id){
        // 1. 获取连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL
        String sql = "delete from oj_table where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            // 3. 执行 SQL
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭并释放资源
            DBUtil.close(connection, statement, null);
        }
    }

    public static void main(String[] args) {
        /*
        // 1.验证 insert 操作
        problem problem=new problem();
        problem.setTitle("各位相加");
        problem.setLevel("简单");
        problem.setDescription("描述");
        problem.setTemplateCode("代码模板");
        problem.setTestCode("public class TestCode {\n" +
                "    public static void main(String[] args) {\n" +
                "        Solution s=new Solution();\n" +
                "        if(s.addDigits(38)==2){\n" +
                "            System.out.println(\"test OK\");\n" +
                "        }else{\n" +
                "            System.out.println(\"test failed\");\n" +
                "        }\n" +
                "        \n" +
                "        if(s.addDigits(1)==1){\n" +
                "            System.out.println(\"test OK\");\n" +
                "        }else{\n" +
                "            System.out.println(\"test failed\");\n" +
                "        }\n" +
                "    }\n" +
                "}");
        ProblemDAO problemDAO=new ProblemDAO();
        problemDAO.insert(problem);
        System.out.println("insert ok");
        */
        //2.验证出现所有的题目
        /*
         ProblemDAO problemDAO=new ProblemDAO();
         System.out.println(problemDAO.selectAll());
         */
        //3.验证指定id

        ProblemDAO problemDAO=new ProblemDAO();
        System.out.println("指定id："+problemDAO.selectOnne(1));
    }
}

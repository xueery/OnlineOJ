package common;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author:wangxue
 * @date:2020/5/17 15:17
 */
// 用于与数据库建立连接，进一步操作数据库
public class DBUtil {
    private static final String URL="jdbc:mysql://129.204.228.169:3306/oj?characterEncoding=UTF-8";
    private static final String USERNAME="root";
    private static final String PASSWORD="root";


    private static volatile DataSource dataSource=null;

    private DBUtil(){

    }

    public static DataSource getDataSource(){
        if(dataSource==null) {
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    dataSource = new MysqlDataSource();
                    ((MysqlDataSource) dataSource).setUrl(URL);
                    ((MysqlDataSource) dataSource).setUser(USERNAME);
                    ((MysqlDataSource) dataSource).setPassword(PASSWORD);
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection(){
        try {
            //内置了数据库连接池
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet){
        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package jdbc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

@Slf4j
public class JDBCUtils {
    private static LinkedList<Connection> listConnections = new LinkedList<>();
    private static String url;
    private static String user;
    private static String password;
    private static Integer jdbcInitPoolSize;

    static {
        Properties properties = new Properties();
        try {
            properties.load(JDBCSaveTest.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            jdbcInitPoolSize = Integer.parseInt(properties.getProperty("jdbcInitPoolSize"));
            Class.forName(properties.getProperty("driver"));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1.获取jdbc.properties配置文件中的数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);

    }
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }
}
